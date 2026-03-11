# Architecture

## Design goals

This framework is optimized for maintainability, demo value, and extension. The architecture favors clear boundaries over clever abstractions.

## Key decisions

- Maven was kept as the build backbone because the framework spans Playwright Java, RestAssured, JDBC utilities, and Allure.
- JUnit 5 replaces TestNG because it provides modern tagging, extensions, parallel execution, and clean integration with Maven Surefire.
- Playwright Java is used only for browser automation. JUnit 5 handles orchestration, lifecycle, and tagging.
- The database layer uses plain JDBC for transparency and low framework overhead.
- File verification utilities stay lightweight and readable instead of introducing a large verification DSL.

## Layer breakdown

- `config`: reads base and environment-specific properties and supports runtime overrides with `-D` flags.
- `ui.playwright`: owns browser lifecycle, context creation, and page exposure.
- `ui.pages`: page objects that encapsulate selectors and user actions.
- `api.client`: generic HTTP client setup and shared request configuration.
- `api.service`: domain-oriented API methods used directly by tests.
- `db`: query and update helpers for backend assertions.
- `files`: parsers and assertions for text, CSV, TSV, JSON, and XML.
- `reporting`: Allure attachment utilities used by the JUnit watcher.
- `tests.base`: reusable setup and teardown for UI, API, and DB tests.

## End-state package structure

```text
src/main/java/com/automation/framework
|-- api
|   |-- client
|   `-- service
|-- config
|-- db
|-- files
|-- reporting
|-- ui
|   |-- pages
|   `-- playwright
`-- utils

src/test/java/com/automation/framework/tests
|-- api
|-- base
|-- db
|-- files
|-- integration
`-- ui
```

## Data and environment strategy

- Base config lives in `src/test/resources/config/application.properties`.
- Environment overrides live in `src/test/resources/config/environments/<env>.properties`.
- Runtime overrides are passed with Maven system properties such as `-Denv=qa` or `-Dbrowser=firefox`.
- Request payloads and file fixtures live under `src/test/resources/data`.
- DB schema and seed data live under `src/test/resources/db`.

## Parallel execution strategy

- JUnit 5 parallel execution is enabled through `junit-platform.properties`.
- UI execution is thread-safe via `ThreadLocal` Playwright resources.
- API and file tests are naturally parallel-safe.
- DB tests use isolated in-memory H2 setup per test lifecycle.

## Recommended next extensions

- Add domain-rich API models instead of string payloads for larger services.
- Introduce a component-object layer if the UI suite grows beyond a few flows.
- Add contract verification with JSON schemas for more advanced API assertions.
- Add environment-specific secret injection via GitHub Actions secrets.

