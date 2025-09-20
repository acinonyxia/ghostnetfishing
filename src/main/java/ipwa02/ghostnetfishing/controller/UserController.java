package ipwa02.ghostnetfishing.controller;

import ipwa02.ghostnetfishing.entity.GhostNet;
import ipwa02.ghostnetfishing.entity.User;
import ipwa02.ghostnetfishing.repository.GhostNetRepository;
import ipwa02.ghostnetfishing.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

/**
 * Controller für Benutzeraktionen:
 * Registrierung, Login, Logout und Profilseite.
 * Verwaltet Sitzungen über HttpSession und leitet an Thymeleaf-Templates weiter.
 */
@Controller
public class UserController {

    private final GhostNetRepository ghostNetRepository;
    private final UserRepository userRepository;

    /**
     * Konstruktor zur Initialisierung der benötigten Repositories.
     *
     * @param ghostNetRepository Repository für Geisternetze
     * @param userRepository     Repository für registrierte Benutzer
     */
    public UserController(GhostNetRepository ghostNetRepository, UserRepository userRepository) {
        this.ghostNetRepository = ghostNetRepository;
        this.userRepository = userRepository;
    }

    /**
     * Zeigt das Registrierungsformular.
     * GET /register
     *
     * @return View-Name für das Registrierungsformular
     */
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    /**
     * Verarbeitet die Registrierung eines neuen Benutzers.
     * Bei Erfolg: Weiterleitung mit Erfolgsmeldung.
     * Bei Duplikat: Weiterleitung mit Fehlermeldung.
     * POST /register
     *
     * @param firstname          Vorname des Benutzers
     * @param lastname           Nachname des Benutzers
     * @param phonenumber        Telefonnummer des Benutzers
     * @param email              E-Mail-Adresse des Benutzers
     * @param password           Passwort im Klartext (Outlook: Verschlüsselung!)
     * @param redirectAttributes Flash-Meldung für Erfolg oder Fehler
     * @return Redirect zur Registrierungsseite
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam String firstname,
                               @RequestParam String lastname,
                               @RequestParam String phonenumber,
                               @RequestParam String email,
                               @RequestParam String password,
                               RedirectAttributes redirectAttributes) {

        if (userRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "Diese E-Mail ist bereits registriert.");
            return "redirect:/register";
        }

        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPhonenumber(phonenumber);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("success", "Registrierung erfolgreich!");
        return "redirect:/register";
    }

    /**
     * Zeigt die Profilseite des aktuell eingeloggten Benutzers.
     * Enthält eigene Meldungen und übernommene Geisternetze.
     * GET /profil
     *
     * @param session aktuelle Benutzersitzung
     * @param model   Model zum Befüllen der View mit User- und Netzdaten
     * @return View-Name für die Profilseite oder Redirect zu Login
     */
    @GetMapping("/profil")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        Optional<User> opt = userRepository.findById(user.getId());

        if (opt.isEmpty()) {
            return "redirect:/logout";
        }

        User freshUser = opt.get();

        List<GhostNet> reportedGhostNets = ghostNetRepository.findByReportedBy(freshUser);
        List<GhostNet> acceptedGhostNets = ghostNetRepository.findByAssignedUser(freshUser);

        model.addAttribute("user", freshUser);
        model.addAttribute("reportedGhostNets", reportedGhostNets);
        model.addAttribute("acceptedGhostNets", acceptedGhostNets);

        return "profil";
    }

    /**
     * Zeigt das Login-Formular.
     * GET /login
     *
     * @return View-Name für das Loginformular
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * Führt den Login eines Benutzers durch.
     * Verwendet einfache Session-Verwaltung ohne Spring Security.
     * POST /login
     *
     * @param email              Benutzer-E-Mail
     * @param password           Klartext-Passwort
     * @param session            HttpSession zur Speicherung des Benutzers
     * @param redirectAttributes Flash-Meldung bei Fehler
     * @return Redirect zur Startseite oder zurück zum Login bei Fehler
     */
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        Optional<User> userOpt = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password))
                .findFirst();

        if (userOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "E-Mail oder Passwort ungültig.");
            return "redirect:/login";
        }

        session.setAttribute("user", userOpt.get());
        return "redirect:/";
    }

    /**
     * Loggt den aktuellen Benutzer aus, indem die Session invalidiert wird.
     * GET /logout
     *
     * @param session aktuelle Benutzersitzung
     * @return Redirect zur Startseite
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}