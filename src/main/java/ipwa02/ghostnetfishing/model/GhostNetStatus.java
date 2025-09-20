package ipwa02.ghostnetfishing.model;

/**
 * Enum zur Beschreibung des aktuellen Status eines Geisternetzes.
 * Dient der Statusverfolgung vom Zeitpunkt der Meldung bis zur Bergung oder zum Verlust.
 */
public enum GhostNetStatus {
    GEMELDET,
    BERGUNG_BEVORSTEHEND,
    GEBORGEN,
    VERSCHOLLEN
}