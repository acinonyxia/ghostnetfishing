package ipwa02.ghostnetfishing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ipwa02.ghostnetfishing.model.GhostNetStatus;
import jakarta.persistence.*;

import java.util.UUID;

/**
 * Repräsentiert ein gemeldetes Geisternetz.
 * Enthält geografische Informationen, den aktuellen Status sowie optionale Verknüpfungen
 * zu meldenden und bergenden Benutzern.
 */
@Entity
@Table(name = "ghost_net")
public class GhostNet {

    /**
     * Eindeutige Kennung (UUID) des Geisternetzes.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Geografischer Breitengrad des Fundorts.
     */
    private double latitude;

    /**
     * Geografischer Längengrad des Fundorts.
     */
    private double longitude;

    /**
     * Freitext-Feld zur geschätzten Größe des Netzes, z.B. "10 m²" oder "2x3 Meter".
     */
    private String estimatedSize;

    /**
     * Aktueller Bearbeitungsstatus des Geisternetzes.
     */
    @Enumerated(EnumType.STRING)
    private GhostNetStatus status;

    /**
     * Benutzer, der das Netz gemeldet hat (optional).
     * Kann null sein bei anonymer Meldung.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "reported_by_user_id")
    private User reportedBy;

    /**
     * Benutzer, der für die Bergung des Netzes vorgesehen ist (optional).
     * Kann null sein, wenn noch niemand das Netz übernommen hat.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser = null;

    // Getter und Setter

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getEstimatedSize() {
        return estimatedSize;
    }

    public void setEstimatedSize(String estimatedSize) {
        this.estimatedSize = estimatedSize;
    }

    public GhostNetStatus getStatus() {
        return status;
    }

    public void setStatus(GhostNetStatus status) {
        this.status = status;
    }

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }
}
