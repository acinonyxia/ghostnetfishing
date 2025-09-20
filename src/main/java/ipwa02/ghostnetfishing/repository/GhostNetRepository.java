package ipwa02.ghostnetfishing.repository;

import ipwa02.ghostnetfishing.entity.GhostNet;
import ipwa02.ghostnetfishing.entity.User;
import ipwa02.ghostnetfishing.model.GhostNetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository für Geisternetze.
 * Verwaltet Datenbankzugriffe auf {@code ghost_net} und stellt
 * verschiedene Suchmethoden für Status und Benutzerbezug bereit.
 */
public interface GhostNetRepository extends JpaRepository<GhostNet, UUID> {

    /**
     * Gibt alle Geisternetze mit dem angegebenen Status zurück.
     *
     * @param status Status des Geisternetzes
     * @return Liste der passenden Netze
     */
    List<GhostNet> findByStatus(GhostNetStatus status);

    /**
     * Gibt alle Geisternetze zurück, deren Status einem der angegebenen entspricht.
     *
     * @param statuses Liste erlaubter Statuswerte
     * @return Liste der passenden Netze
     */
    List<GhostNet> findByStatusIn(List<GhostNetStatus> statuses);

    /**
     * Gibt alle Geisternetze zurück, die vom übergebenen Benutzer gemeldet wurden.
     *
     * @param user meldender Benutzer
     * @return Liste der von diesem Benutzer gemeldeten Netze
     */
    List<GhostNet> findByReportedBy(User user);

    /**
     * Gibt alle Geisternetze zurück, die dem übergebenen Benutzer zur Bergung zugewiesen sind.
     *
     * @param user übernehmender Benutzer
     * @return Liste der dem Benutzer zugewiesenen Netze
     */
    List<GhostNet> findByAssignedUser(User user);
}
