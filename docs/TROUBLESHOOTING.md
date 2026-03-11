# Troubleshooting

## Playwright browser install issues

The Playwright Java dependency downloads browser binaries on first use. If the initial run fails in a restricted environment, rerun once internet access is available.

## Public demo site instability

The sample UI and API flows use public demo systems. If they are temporarily unavailable, switch the environment config to an internal demo target or stub service.

## Allure report is empty

Run tests before generating the report and confirm `allure-results` contains files. The persistent HTML report is generated under `allure-report`.

## H2 DB verification failures

The DB suite expects `schema.sql` and `seed.sql` to be available under `src/test/resources/db`. If you rename them, update `BaseDatabaseTest`.

## Tag filtering did not work

Use Maven Surefire properties:

```bash
mvn clean test -Dgroups=ui
mvn clean test -DexcludedGroups=integration
```

## CI workflow differences

UI and performance workflows are intentionally separate from the main CI job so heavy browser or performance execution does not slow every commit.


