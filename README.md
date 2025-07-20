# C-3PO Discord Bot 🤖

Ein Discord-Bot, der als C-3PO aus Star Wars fungiert und Fragen mithilfe eines KI-Modells beantwortet. Er ist darauf programmiert, mit der typischen Manier von C-3PO zu kommunizieren und wechselt regelmäßig seine Aktivitäten, um seine Persönlichkeit widerzuspiegeln.

-----

## 🌟 Funktionen

  * **C-3PO-Persönlichkeit:** Antwortet auf Fragen im Stil von C-3PO, inklusive seiner typischen Besorgnis und Höflichkeit.
  * **KI-gestützte Antworten:** Nutzt ein KI-Modell (derzeit Llama-3.3-70B-Instruct-Turbo-Free über Together.xyz), um auf Benutzeranfragen zu antworten.
  * **Dynamische Statusanzeige:** Der Bot ändert regelmäßig seine Discord-Aktivität, um verschiedene C-3PO-bezogene Phrasen anzuzeigen (z.B. "beobachtet die galaktische Etikette").
  * **Fehlerbehandlung:** Gibt humorvolle, C-3PO-ähnliche Fehlermeldungen aus, falls bei der KI-Kommunikation Probleme auftreten.
  * **Direkte Ansprache:** Der Bot antwortet nur auf Nachrichten, die ihn direkt erwähnen.

-----

## 🚀 Erste Schritte

### Voraussetzungen

  * Java Development Kit (JDK) 21 oder höher
  * Ein Discord Bot Token
  * Ein Together.xyz API Key

### Installation

1.  **Repository klonen:**

    ```bash
    git clone https://github.com/Dominic-Kaemereit/C-3PO.git
    cd C-3PO
    ```

2.  **Umgebungsvariablen setzen:**
    Der Bot benötigt zwei Umgebungsvariablen für die API-Schlüssel. Erstelle eine `.env`-Datei oder setze sie direkt in deiner Umgebung:

      * `DISCORD_API_KEY`: Dein Discord Bot Token.
      * `TOGETHER_API_KEY`: Dein Together.xyz API Key.

    Beispiel für eine `.env`-Datei:

    ```
    DISCORD_API_KEY=DEIN_DISCORD_BOT_TOKEN_HIER
    TOGETHER_API_KEY=DEIN_TOGETHER_API_KEY_HIER
    ```

3.  **Bot bauen und ausführen:**
    Verwende Gradle, um das Projekt zu bauen und auszuführen. Das Projekt ist so konfiguriert, dass es eine ausführbare JAR-Datei erstellt.

    ```bash
    ./gradlew shadowJar
    java -jar build/libs/c3po-1.0-all.jar
    ```

    Alternativ kannst du den Bot direkt über Gradle ausführen:

    ```bash
    ./gradlew run
    ```

-----

## 🛠️ Entwicklung

### Projektstruktur

  * `Bot.kt`: Die Hauptklasse, die den Discord-Bot initialisiert, Event-Listener registriert und die Aktivitäten des Bots verwaltet.
  * `handler/AIHandler.kt`: Verwaltet die Kommunikation mit der Together.xyz API, um KI-Antworten zu generieren. Enthält die Logik für das Prompt-Engineering und die Parsen der Antworten.
  * `listener/MessageReceivedListener.kt`: Ein Event-Listener, der auf eingehende Discord-Nachrichten reagiert, den Bot erwähnt und die Nachricht an den `AIHandler` zur Verarbeitung weiterleitet.

### Verwendete Bibliotheken

  * **JDA (Java Discord API):** Zum Interagieren mit der Discord-API.
  * **OkHttp:** Ein effizienter HTTP-Client für die Kommunikation mit der Together.xyz API.
  * **org.json:** Zum Parsen und Erstellen von JSON-Objekten für die API-Anfragen und -Antworten.
  * **Logback Classic:** Für die Protokollierung von Anwendungsinformationen.

-----
