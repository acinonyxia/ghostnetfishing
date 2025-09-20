package ipwa02.ghostnetfishing.entity;

import jakarta.persistence.*;

import java.util.UUID;

/**
 * Repräsentiert einen registrierten Benutzer der Anwendung.
 * Benutzer können Geisternetze melden oder zur Bergung übernehmen.
 */
@Entity
@Table(name = "app_user")
public class User {

    /**
     * Eindeutige ID des Benutzers (UUID).
     * Wird automatisch generiert.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstname;
    private String lastname;

    /**
     * Telefonnummer des Benutzers (zur Kontaktaufnahme durch andere Nutzer).
     */
    private String phonenumber;

    /**
     * Eindeutige E-Mail-Adresse des Benutzers.
     * Wird zur Anmeldung verwendet.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Passwort des Benutzers im Klartext.
     * ⚠️ Hinweis: In produktiven Anwendungen sollte das Passwort stets gehasht gespeichert werden.
     */
    @Column(nullable = false)
    private String password;

    // Getter und Setter

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
