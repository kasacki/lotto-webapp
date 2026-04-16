# Lotto Management System — WebApp

A Java web application for managing Lotto draws, built on **Jakarta EE 10**, **JPA (EclipseLink)** and **Apache Derby**. Developed as a university lab project at Silesian University of Technology.

---

## Features

- **View draw history** — lists all draws stored in the database
- **Add a new draw** — form accepting a date and 6 numbers in the format `yyyy-MM-dd;n1,n2,n3,n4,n5,n6`
- **Check number statistics** — shows how many days have passed since a given number was last drawn (result stored in a cookie for 1 hour)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Application server | Payara Server (GlassFish-compatible) |
| Specification | Jakarta EE 10 |
| Persistence | JPA 3.0 — EclipseLink |
| Database | Apache Derby (network mode, port 1527) |
| Web layer | Jakarta Servlets |
| Boilerplate reduction | Lombok |
| Testing | JUnit 5 (Jupiter) |
| Build | Maven, Java 21 |
| IDE | NetBeans |

---

## Project Structure

```
WebApp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── pl/polsl/
│   │   │   │   ├── entities/       # JPA entities: DrawEntity, LottoNumber
│   │   │   │   ├── exception/      # LotteryDataException
│   │   │   │   ├── model/          # DbModel, Model, Draw, DrawFilter, TypedBox
│   │   │   │   └── lab/webapp/     # Jakarta REST configuration (JakartaRestConfiguration)
│   │   │   └── ServletPackage/     # Servlets: BaseServlet, AddServlet, DrawsServlet, NumberServlet
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       └── persistence.xml # JPA configuration (persistence unit: my_persistence_unit)
│   │   └── webapp/
│   │       ├── index.html
│   │       ├── add.html
│   │       ├── draws.html
│   │       ├── number.html
│   │       └── WEB-INF/
│   │           ├── web.xml
│   │           ├── beans.xml
│   │           └── glassfish-resources.xml  # JDBC connection pool configuration for Payara
│   └── test/java/                   # JUnit 5 tests (Boundary, Correct, Exception)
└── pom.xml
```

---

## Requirements

- **Java 21**
- **Maven 3.x**
- **NetBeans IDE** (recommended — project includes `nb-configuration.xml`)
- **Payara Server** (6.x or any Jakarta EE 10 compatible release)
- **Apache Derby** running in network mode on `localhost:1527`

---

## Database Configuration

The connection to Derby is defined in `persistence.xml`:

```
URL:      jdbc:derby://localhost:1527/lab
User:     APP
Password: APP
```

> The database schema (tables `DRAWENTITY`, `LOTTONUMBER`) is created automatically on first startup (`schema-generation.database.action = create`).

The JDBC connection pool for Payara is configured in `WEB-INF/glassfish-resources.xml` and registers the resource under the JNDI name `java:app/JavaDB`.

---

## Running the Project in NetBeans

1. Make sure **Payara Server** is registered in NetBeans (_Services → Servers_).
2. Start **Derby** — in NetBeans: _Services → Databases → Java DB → Start Server_.
3. Clone the repository and open the project: _File → Open Project → WebApp_.
4. Click **Run** (green arrow) — NetBeans will build the WAR via Maven and deploy it to Payara.
5. Open your browser at:
   ```
   http://localhost:8080/WebApp-1.0-SNAPSHOT/
   ```

---

## Building (CLI)

```bash
mvn clean package
```

The file `WebApp-1.0-SNAPSHOT.war` will appear in the `target/` directory and can be deployed manually via the Payara Admin Console at `http://localhost:4848`.

---

## Tests

Unit tests (JUnit 5) are split into three categories per feature:

| Category | Description |
|---|---|
| `CorrectTest` | Valid input data |
| `BoundaryTest` | Edge/boundary values |
| `ExceptionTest` | Invalid input — expected exceptions |

Run all tests:

```bash
mvn test
```

---

## Author

Karol Sawicki — Silesian University of Technology
