package ipwa02.ghostnetfishing.controller;

import ipwa02.ghostnetfishing.entity.GhostNet;
import ipwa02.ghostnetfishing.entity.User;
import ipwa02.ghostnetfishing.model.GhostNetStatus;
import ipwa02.ghostnetfishing.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Web-Controller für alle Ansichten und Interaktionen rund um Geisternetze.
 * Behandelt z.B. das Melden, Zuweisen und Anzeigen von Netzen über Thymeleaf-Seiten.
 * Basis-URL: /ghostnets
 */
@Controller
@RequestMapping("/ghostnets")
public class GhostNetWebController {

    private final GhostNetRepository ghostNetRepository;
    private final UserRepository userRepository;

    /**
     * Konstruktor zur Initialisierung der benötigten Repositories.
     *
     * @param ghostNetRepository Repository für Geisternetze
     * @param userRepository     Repository für registrierte Benutzer
     */
    public GhostNetWebController(GhostNetRepository ghostNetRepository, UserRepository userRepository) {
        this.ghostNetRepository = ghostNetRepository;
        this.userRepository = userRepository;
    }

    /**
     * Zeigt das Formular zur Meldung eines neuen Geisternetzes.
     * GET /ghostnets/new
     *
     * @return View-Name für die Formularseite
     */
    @GetMapping("/new")
    public String showForm() {
        return "ghostnets/new";
    }

    /**
     * Zeigt alle gemeldeten Geisternetze, die noch keiner Person zur Bergung zugewiesen wurden.
     * GET /ghostnets/unassigned
     *
     * @param model Model zum Einfügen der Netzliste für die View
     * @return View-Name für die Übersicht der Netze mit dem Status GEMELDET
     */
    @GetMapping("/unassigned")
    public String showUnassignedNets(Model model) {
        List<GhostNet> nets = ghostNetRepository.findByStatus(GhostNetStatus.GEMELDET);
        model.addAttribute("nets", nets);
        return "ghostnets/unassigned";
    }

    /**
     * Zeigt alle Geisternetze, die aktuell einer Person zur Bergung zugewiesen wurden.
     * GET /ghostnets/assigned
     *
     * @param model Model zum Einfügen der Netzliste für die View
     * @return View-Name für die Übersicht der Netze mit dem Status BERGUNG_BEVORSTEHEND
     */
    @GetMapping("/assigned")
    public String showAssignedNets(Model model) {
        List<GhostNet> nets = ghostNetRepository.findByStatus(GhostNetStatus.BERGUNG_BEVORSTEHEND);
        model.addAttribute("nets", nets);
        return "ghostnets/assigned";
    }

    /**
     * Zeigt die Kartenansicht mit allen Netzen im Status GEMELDET oder BERGUNG_BEVORSTEHEND.
     * Die Marker-Daten werden per REST-API geladen.
     * GET /ghostnets/map
     *
     * @return View-Name für die Kartenansicht
     */
    @GetMapping("/map")
    public String showMapView() {
        return "ghostnets/map";
    }

    /**
     * Meldet ein neues Geisternetz mit übergebenen Koordinaten und geschätzter Größe.
     * Optional kann ein registrierter Benutzer übergeben werden, der das Netz gemeldet hat.
     * POST /ghostnets/report
     *
     * @param latitude           Breitengrad des Netzes
     * @param longitude          Längengrad des Netzes
     * @param estimatedSize      geschätzte Größe als Freitext (z.B. "ca. 10 m²")
     * @param reporterId         UUID des meldenden Users (optional)
     * @param redirectAttributes für Erfolg/Fehlermeldungen im Redirect
     * @return Weiterleitung zur Eingabeseite mit Flash-Meldung
     */
    @PostMapping("/report")
    public String submitReport(@RequestParam double latitude,
                               @RequestParam double longitude,
                               @RequestParam String estimatedSize,
                               @RequestParam(required = false) String reporterId,
                               RedirectAttributes redirectAttributes) {

        GhostNet net = new GhostNet();
        net.setLatitude(latitude);
        net.setLongitude(longitude);
        net.setEstimatedSize(estimatedSize);
        net.setStatus(GhostNetStatus.GEMELDET);

        if (reporterId != null && !reporterId.isBlank()) {
            try {
                UUID uuid = UUID.fromString(reporterId);
                userRepository.findById(uuid).ifPresent(net::setReportedBy);
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("error", "Reporter-ID ungültig");
                return "redirect:/ghostnets/new";
            }
        }

        ghostNetRepository.save(net);
        redirectAttributes.addFlashAttribute("success", "Geisternetz wurde erfolgreich gemeldet.");
        return "redirect:/ghostnets/new";
    }

    /**
     * Weist einem Benutzer ein Geisternetz zu, das sich im Status GEMELDET befindet.
     * Dadurch wird der Status des Netzes auf BERGUNG_BEVORSTEHEND gesetzt.
     * POST /ghostnets/{id}/assign
     *
     * @param ghostNetId         ID des zuzuweisenden Geisternetzes
     * @param userId             ID des übernehmenden Benutzers
     * @param redirectAttributes für Erfolg/Fehlermeldungen im Redirect
     * @return Weiterleitung zur Übersicht der offenen Netze
     */
    @PostMapping("/{ghostNetId}/assign")
    public String assignGhostNet(@PathVariable UUID ghostNetId,
                                 @RequestParam UUID userId,
                                 RedirectAttributes redirectAttributes) {

        Optional<GhostNet> optNet = ghostNetRepository.findById(ghostNetId);
        Optional<User> optUser = userRepository.findById(userId);

        if (optNet.isEmpty() || optUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Netz oder Benutzer nicht gefunden.");
            return "redirect:/ghostnets/unassigned";
        }

        GhostNet net = optNet.get();

        if (net.getStatus() != GhostNetStatus.GEMELDET || net.getAssignedUser() != null) {
            redirectAttributes.addFlashAttribute("error", "Netz ist bereits vergeben oder nicht zuweisbar.");
            return "redirect:/ghostnets/unassigned";
        }

        net.setAssignedUser(optUser.get());
        net.setStatus(GhostNetStatus.BERGUNG_BEVORSTEHEND);
        ghostNetRepository.save(net);

        redirectAttributes.addFlashAttribute("success", "Du hast das Netz erfolgreich übernommen.");
        return "redirect:/ghostnets/unassigned";
    }

    /**
     * Meldet ein Geisternetz als verschollen.
     * Diese Aktion ist für alle registrierten Benutzer erlaubt, wenn der Status nicht bereits GEBORGEN oder VERSCHOLLEN ist.
     * POST /ghostnets/{id}/report-lost
     *
     * @param ghostNetId         ID des betroffenen Geisternetzes
     * @param userId             ID des meldenden Benutzers
     * @param redirectTo         Zielseite für die Rückleitung (Profil oder Liste)
     * @param redirectAttributes für Erfolg/Fehlermeldungen im Redirect
     * @return Weiterleitung zur übergebenen Seite mit Flash-Meldung
     */
    @PostMapping("/{ghostNetId}/report-lost")
    public String reportAsLost(@PathVariable UUID ghostNetId,
                               @RequestParam UUID userId,
                               @RequestParam(defaultValue = "/ghostnets/unassigned") String redirectTo,
                               RedirectAttributes redirectAttributes) {

        Optional<GhostNet> optNet = ghostNetRepository.findById(ghostNetId);
        Optional<User> optUser = userRepository.findById(userId);

        if (optNet.isEmpty() || optUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Netz oder Benutzer nicht gefunden.");
            return "redirect:" + redirectTo;
        }

        GhostNet net = optNet.get();

        if (net.getStatus() == GhostNetStatus.GEBORGEN) {
            redirectAttributes.addFlashAttribute("error", "Dieses Netz wurde bereits erfolgreich geborgen.");
            return "redirect:" + redirectTo;
        }
        if (net.getStatus() == GhostNetStatus.VERSCHOLLEN) {
            redirectAttributes.addFlashAttribute("error", "Dieses Netz wurde bereits als verschollen gemeldet.");
            return "redirect:" + redirectTo;
        }

        net.setStatus(GhostNetStatus.VERSCHOLLEN);
        ghostNetRepository.save(net);

        redirectAttributes.addFlashAttribute("success", "Netz wurde als verschollen gemeldet.");
        return "redirect:" + redirectTo;
    }

    /**
     * Markiert ein zugewiesenes Geisternetz als erfolgreich geborgen.
     * Diese Aktion ist nur für den zugewiesenen Benutzer erlaubt, wenn der Status BERGUNG_BEVORSTEHEND ist.
     * POST /ghostnets/{id}/mark-recovered
     *
     * @param ghostNetId         ID des betroffenen Geisternetzes
     * @param session            aktuelle Benutzersitzung (zur Identifikation des ausführenden Users)
     * @param redirectAttributes für Erfolg/Fehlermeldungen im Redirect
     * @return Weiterleitung zum Profil mit Flash-Meldung
     */
    @PostMapping("/{ghostNetId}/mark-recovered")
    public String markAsRecovered(@PathVariable UUID ghostNetId,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Optional<GhostNet> optNet = ghostNetRepository.findById(ghostNetId);
        if (optNet.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Netz nicht gefunden.");
            return "redirect:/profil";
        }

        GhostNet net = optNet.get();

        if (net.getAssignedUser() == null || !net.getAssignedUser().getId().equals(user.getId())) {
            redirectAttributes.addFlashAttribute("error", "Du bist diesem Netz nicht zugewiesen.");
            return "redirect:/profil";
        }

        if (net.getStatus() != GhostNetStatus.BERGUNG_BEVORSTEHEND) {
            redirectAttributes.addFlashAttribute("error", "Dieses Netz kann nicht als geborgen markiert werden.");
            return "redirect:/profil";
        }

        net.setStatus(GhostNetStatus.GEBORGEN);
        ghostNetRepository.save(net);

        redirectAttributes.addFlashAttribute("success", "Netz wurde als geborgen markiert.");
        return "redirect:/profil";
    }
}
