# API Test Automation — FakeRESTApi Books

REST Assured + TestNG automation for the Books endpoints of
<https://fakerestapi.azurewebsites.net>, built on the Service Object Model.

## Scenarios covered

| Type | Test | Checks |
|---|---|---|
| Happy path | `shouldReturnAllBooks` | `GET /Books` → 200, JSON content type, response matches the books array schema, catalogue is non-empty, every book has an id and title, ids are unique |
| Happy path | `shouldCreateBook` (data-driven, 2 rows) | `POST /Books` → 200, body matches the book schema, the returned book equals the submitted payload, page count is positive |
| Negative / edge | `shouldRejectInvalidBookId` (data-driven, 2 rows) | `GET /Books/99999` → 404 `Not Found`; `GET /Books/invalid-id` → 400 validation error. Both assert the problem-details body and that its `status` agrees with the HTTP status |

Five test executions in total from three test methods.

## How to run

Prerequisites: JDK 17+, Maven 3.8+ and outbound internet access.

```bash
mvn clean test
```

Point the suite at another environment, or print every request and response, without
changing code:

```bash
mvn clean test -Dapi.base.uri=https://fakerestapi.azurewebsites.net -Dapi.base.path=/api/v1
mvn clean test -Dapi.log.all=false
```

Every call is logged by default — method, URI, headers and both bodies, pretty-printed — so a
run shows exactly what the API returned. Set `api.log.all=false` (or `-Dapi.log.all=false`) for
a quiet run; failing calls are logged either way.

Reports are written to `target/surefire-reports/`.

## Project structure

```
src/test/java/com/assessment/api/
├── base/      BaseApiTest     — shared setup: log-on-failure, service wiring
├── config/    Configuration   — config.properties + -D overrides
├── data/      JsonDataReader  — JSON → POJO → @DataProvider
│              InvalidBookRequest — typed model of the negative test data
├── models/    Book            — request/response payload with value equality
├── services/  BaseService     — HTTP verbs built on the shared spec (once)
│              BooksService    — getAllBooks, getBookById, createBook
├── spec/      Specs           — request spec (base URI, headers) + reusable response spec
└── tests/     BooksHappyPathTest, BooksNegativeTest

src/test/resources/
├── config.properties           base URI, base path
├── schemas/*.json              JSON schemas for contract-level validation
├── testdata/*.json             externalised payloads and negative cases
└── suites/testng.xml           suite definition (parallel by class)
```

## Design notes

- **Service Object Model** — `BooksService` is the only place that knows the Books paths.
  Tests call `booksService.getBookById(id)`; they never build a URL or pick an HTTP verb.
- **Reusable components** — `Specs.request()` owns base URI, base path and headers;
  `Specs.okJson()` is the reusable expectation for a successful JSON response; `BaseService`
  implements each HTTP verb once. `books-array-schema.json` `$ref`s `book-schema.json`, so the
  book contract is defined once rather than copied into two files.
- **Fluent design** — assertions chain through REST Assured's own fluent API
  (`response.then().spec(okJson()).body(matchesJsonSchemaInClasspath(...))`), then the payload
  is deserialised into a `Book` for value-level assertions.
- **Meaningful assertions** — beyond status codes: schema conformance, round-trip equality of
  the created book, uniqueness of catalogue ids, and consistency between the HTTP status and
  the `status` field in the error body.
- **Data-driven** — payloads and negative cases live in `testdata/*.json` and are bound to
  POJOs; adding a case is a JSON edit.
- **Diagnosability** — `BaseApiTest` is the single place logging is configured. A failing
  assertion always logs the full request and response, so a red report carries its own
  evidence, and `api.log.all` (default `true`) logs every call so the traffic is visible
  without re-running anything.

## Observed API behaviour

The FakeRESTApi simulates writes rather than storing them. Verified while building this suite:

1. **`POST /Books` returns `200 OK`, not `201 Created`**, and returns no `Location` header.
   For a real API this would be a defect; here it is the documented sandbox behaviour, so the
   test asserts 200 and the response payload.
2. **Created books are not persisted.** After a successful `POST /Books` with `id: 9001`,
   `GET /Books/9001` returns `404 Not Found`. The create test therefore validates the returned
   representation only and deliberately does not assert retrievability — asserting a 404 for a
   just-created resource would bake incorrect behaviour into the suite.
3. **`DELETE /Books/{id}` returns `200 OK` but does not delete.** `GET /Books/1` still returns
   `200` afterwards. Verified with a direct call; no delete test is included, because an
   assertion over a no-op would prove nothing.
4. **`publishDate` on `GET` responses is generated relative to the current date**, so it is
   validated by type in the schema rather than by value.

Because of points 2 and 3, the suite is **stateless and idempotent**: it can be re-run any
number of times, in parallel, without any cleanup step.

## Deliberately not included

Kept out because nothing in the required coverage needs them:

- **No `updateBook` / `deleteBook` on the service.** The API simulates both without changing
  state (see above), so there is nothing meaningful to assert. They were written, found to be
  unreachable from any test, and removed — adding them back is three lines on top of
  `BaseService` the day a test needs them.
- **No POJO builders, no Lombok, no test-data factories.** Two payloads read from JSON do not
  justify any of it.
