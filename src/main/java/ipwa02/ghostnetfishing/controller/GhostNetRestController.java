package ipwa02.ghostnetfishing.controller;

import ipwa02.ghostnetfishing.entity.GhostNet;
import ipwa02.ghostnetfishing.model.GhostNetStatus;
import ipwa02.ghostnetfishing.repository.GhostNetRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-Controller für Geisternetze.
 * Stellt JSON-Daten zur Verfügung, z.B. für die Kartenansicht.
 */
@RestController
@RequestMapping("/api/ghostnets")
public class GhostNetRestController {

    private final GhostNetRepository ghostNetRepository;

    /**
     * Konstruktor zur Initialisierung des Repositories.
     *
     * @param ghostNetRepository Repository zum Zugriff auf Geisternetze
     */
    public GhostNetRestController(GhostNetRepository ghostNetRepository) {
        this.ghostNetRepository = ghostNetRepository;
    }

    /**
     * Liefert alle Geisternetze mit dem Status GEMELDET oder BERGUNG_BEVORSTEHEND
     * als JSON-Array zurück. Diese Daten werden z.B. von der Leaflet-Karte verwendet.
     * GET /api/ghostnets/mapdata
     *
     * @return Liste aller noch nicht geborgenen Geisternetze (JSON)
     */
    @GetMapping("/mapdata")
    public ResponseEntity<List<GhostNet>> getUnrecoveredNets() {
        List<GhostNetStatus> statuses = List.of(
                GhostNetStatus.GEMELDET,
                GhostNetStatus.BERGUNG_BEVORSTEHEND
        );
        return ResponseEntity.ok(ghostNetRepository.findByStatusIn(statuses));
    }
}
