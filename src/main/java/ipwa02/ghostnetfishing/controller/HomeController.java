package ipwa02.ghostnetfishing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller für die Startseite der Anwendung.
 * Leitet Anfragen auf die URL "/" an das Index-Template weiter.
 */
@Controller
public class HomeController {

    /**
     * Zeigt die Startseite der Anwendung.
     * GET /
     *
     * @return View-Name der Startseite ("index")
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
