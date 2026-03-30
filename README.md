# Banking Sheba

A desktop banking application built with JavaFX and SQLite.

## Features

- User login screen
- Dashboard view
- Add money to wallet
- Withdraw money
- Transaction history
- Local SQLite storage with automatic schema initialization

## Tech Stack

- Java 21
- JavaFX 17
- Maven Wrapper (`mvnw`, `mvnw.cmd`)
- SQLite (`sqlite-jdbc`)

## Prerequisites

- JDK 21 installed and available in PATH
- Internet connection (first build only, to download Maven dependencies)

## Getting Started

1. Clone or download the project.
2. Open the project in VS Code or your preferred IDE.
3. Build and run using the Maven wrapper.

### Run on Windows

```powershell
.\mvnw.cmd clean javafx:run
```

### Run on macOS/Linux

```bash
./mvnw clean javafx:run
```

## Build

```powershell
.\mvnw.cmd clean package
```

## Default Login

On first startup, the app auto-creates a default user:

- Username: `emamhassan`
- Password: `1523`

## Database

The SQLite database file is created automatically at:

- Windows: `%USERPROFILE%\.bankingsheba\database.db`
- Other OS: `$HOME/.bankingsheba/database.db`

## Project Structure

```text
src/main/java/com/emam/bankingsheba/
  BankApp.java
  config/
  controller/
  model/
  service/
  utils/

src/main/resources/
  com/emam/fxml/
  css/
```

## Main Entry Point

- Java class: `com.emam.bankingsheba.BankApp`

## Notes

- The current `pom.xml` sets `javafx.platform` to `win`, so JavaFX native artifacts are configured for Windows.
- For non-Windows environments, update `javafx.platform` in `pom.xml` to match your OS (`linux` or `mac`) before running.
