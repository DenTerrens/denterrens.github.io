# Running Tests

## Full regression

```bash
mvn clean test
```

## By layer

```bash
mvn clean test -Pui
mvn clean test -Papi
mvn clean test -Pdb
mvn clean test -Pfiles
mvn clean test -Pintegration
```

## By tag

```bash
mvn clean test -Dgroups=smoke
mvn clean test -Dgroups=ui
mvn clean test -DexcludedGroups=integration
```

## Browser and environment overrides

```bash
mvn clean test -Pui -Dbrowser=firefox -Dheadless=false
mvn clean test -Papi -Denv=qa
```

## Allure

```bash
mvn clean test
mvn allure:report
mvn allure:serve
```

## JMeter

```bash
jmeter -n -t performance/jmeter/plans/reqres-smoke.jmx -l performance/jmeter/results.jtl -e -o performance/jmeter/report
```

## When to use JMeter vs RestAssured

- Use RestAssured for functional correctness, contract verification, and workflow automation.
- Use JMeter for concurrency, response-time, throughput, and smoke-level performance checks.

## Adding new tests

### New UI test

1. Add or extend a page object under `src/main/java/com/automation/framework/ui/pages`.
2. Create a JUnit 5 test under `src/test/java/com/automation/framework/tests/ui`.
3. Tag it with `@Tag("ui")` and optionally `@Tag("smoke")`.

### New API test

1. Add a service method under `src/main/java/com/automation/framework/api/service`.
2. Add payloads under `src/test/resources/data/api` if needed.
3. Create a JUnit 5 test under `src/test/java/com/automation/framework/tests/api` and tag it with `@Tag("api")`.

### New DB verification

1. Add reusable SQL or helper methods around `DatabaseClient`.
2. Seed any demo data under `src/test/resources/db`.
3. Create a test under `src/test/java/com/automation/framework/tests/db` and tag it with `@Tag("db")`.

### New file validator usage

1. Place fixtures or generated samples under `src/test/resources/data/files`.
2. Add parsing or assertion helpers under `src/main/java/com/automation/framework/files` if the format is new.
3. Create a tagged JUnit 5 test under `src/test/java/com/automation/framework/tests/files`.

## Intentional failure demo mode

These tests are useful for showing how Allure captures failed assertions, screenshots, and DOM evidence without forcing every default run to fail.

```bash
mvn clean test -Pfailure-demo
mvn clean test -Pfailure-demo -Dgroups=ui,demo-failure
mvn clean test -Pfailure-demo -Dgroups=api,demo-failure
```

## Generated artifacts

- `allure-results`: raw Allure result files and attachments
- `allure-report`: persistent HTML Allure report
- `reports/surefire`: Maven Surefire XML and text outputs
- `logs/automation-framework.log`: rolling framework log file

## Publish Allure to GitHub Pages

A dedicated workflow is available at `.github/workflows/pages-allure-report.yml`.

Before using it:

1. Open `Settings -> Pages` in your repository.
2. Select `GitHub Actions` as the publishing source.

Then trigger the workflow manually or push changes to `main` or `master`. The workflow runs the functional suites, generates `allure-report`, and deploys that static site to GitHub Pages.

## Performance workflow output

The `performance` workflow uploads the raw `.jtl` file and HTML report as artifacts. It also prints a concise JMeter summary into the GitHub Actions job log and the run summary, including:

- sample count
- failure count
- average response time
- minimum response time
- maximum response time
- approximate 95th percentile response time
