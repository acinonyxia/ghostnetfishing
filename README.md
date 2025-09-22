# Ghostnet Fishing – Web-App zur Meldung und Bergung von Geisternetzen

## 🌊 Überblick

Dieses Projekt entstand im Rahmen einer Fallstudie und demonstriert eine Web-Anwendung zur Meldung und Bergung sogenannter Geisternetze – herrenlose Fischernetze, die im Meer treiben und eine Gefahr für Meereslebewesen darstellen.

Features:

- Meldung von Geisternetzen (anonym oder durch registrierte Nutzer)
- Übernahme zur Bergung (Statuswechsel auf *BERGUNG_BEVORSTEHEND*)
- Statusänderungen auf *GEBORGEN* / *VERSCHOLLEN*
- Übersichten: Gemeldete Geisternetze und Geisternetze in Bergung
- Leaflet-Karte mit Markern für alle nicht geborgenen Netze
- Einfache Session-basierte Authentifizierung (Login/Logout)

Umgesetzt wurde das Projekt mit **Spring Boot**, **Thymeleaf**, **Bootstrap**, **PostgreSQL** und **Leaflet.js**.

---

## ⚙️ Tech Stack

- **Java 24**
- **Spring Boot 3** (inkl. Spring MVC, Spring Data JPA, Hibernate)
- **PostgreSQL** als Datenbank
- **Thymeleaf** für serverseitiges Templating
- **Bootstrap 5** für responsives Styling
- **Leaflet.js** für die interaktive Karte
- **Maven** als Build-Tool
- **IntelliJ IDEA** als IDE

---

## 🚀 Installation & Setup

1. Repository klonen

```
git clone https://github.com/acinonyxia/ghostnetfishing.git
cd ghostnetfishing
```

2. Datenbank vorbereiten

- PostgreSQL installieren und starten
- Eine neue Datenbank z.B. ghostnetdb anlegen
- Den bereitgestellten SQL-Dump importieren (Beispieldaten)

```
createdb -U <db_user> ghostnetdb
psql -U <db_user> -d ghostnetdb -f db/seed/ghostnet_dump.sql
```

3. Konfiguration anpassen

- Datei `src/main/resources/application-example.properties` kopieren zu `application.properties`
- Zugangsdaten für die lokale Datenbank eintragen

4. Build & Run

```
mvn spring-boot:run
```

5. Zugriff auf die Anwendung

- Browser öffnen: http://localhost:8080

---

## 🔮 Mögliche Erweiterungen

- Integration von Spring Security für Passwort-Verschlüsselung und Login-Absicherung
- Schutz vor Spam und Missbrauch bei anonymen Meldungen
- Einführung einer Service-Schicht und DTOs für saubere Architektur
- Anpassung der Oberfläche an die Corporate Identity der Organisation

---

## 👤 Autor

Entwickelt von Sabrina Hessenthaler im Rahmen einer Fallstudie.
