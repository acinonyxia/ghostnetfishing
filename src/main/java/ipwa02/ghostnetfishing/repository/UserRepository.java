package ipwa02.ghostnetfishing.repository;

import ipwa02.ghostnetfishing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository für registrierte Benutzer.
 * Stellt Zugriff auf die Tabelle {@code app_user} bereit und ermöglicht
 * grundlegende sowie benutzerdefinierte Abfragen.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Prüft, ob ein Benutzer mit der angegebenen E-Mail existiert.
     *
     * @param email zu prüfende E-Mail-Adresse
     * @return true, wenn die E-Mail bereits vergeben ist, sonst false
     */
    boolean existsByEmail(String email);

}
