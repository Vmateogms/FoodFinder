# FoodFinder

FoodFinder is a Spring Boot backend that compares grocery prices across different retailers, for now live data can be pulled from Aldi. 

It's a personal learning project built to practice core Spring Boot / Spring Data JPA concepts: REST API design, entity relationships, DTO/entity separation, and integrating with an external third-party API (Algolia, which powers Aldi's product search).

## How it works

The app is split into two independent flows that share the same database:

**1. Import (batch, triggered manually for now)**

`AldiFetcher` calls Aldi's public Algolia search endpoint, maps the raw JSON response into `RawProduct` objects, and hands them to `ProductImportService`, which finds-or-creates the matching `Product` and `Retailer` rows and stores a new `PriceRecord`.

**2. Search (live, one call per request)**

`/products/search` never talks to Aldi directly — it only queries the local database, so it stays fast and doesn't depend on any external API being available.

```
Import:  AldiFetcher -> Algolia (Aldi) -> ProductImportService -> Database
Search:  User query  -> ProductController -> Database
```

## Tech stack

- Java 17+
- Spring Boot (Spring Framework 6, using `RestClient` for outbound HTTP calls)
- Spring Data JPA
- MySQL
- Lombok
- Jackson (JSON <-> Java mapping)

## Project structure

```
foodfinder
├── controller      # REST endpoints (ProductController, ImportController)
├── serviceimpl     # Business logic (price comparison, product import)
├── repository      # Spring Data JPA repositories
├── entity          # JPA entities (Product, Retailer, PriceRecord)
├── dto             # DTOs, including the Algolia response mapping records
└── fetcher         # Retailer-specific data fetchers (RetailerFetcher, AldiFetcher)
```

## Getting started

### Prerequisites

- Java 17 or later
- MySQL running locally (or update the connection details below)
- Maven (adjust the run command if this project uses Gradle instead)

### Setup

1. Clone the repository.
2. Create a MySQL database:
   ```sql
   CREATE DATABASE foodfinder;
   ```
3. Configure `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/foodfinder
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```
4. Run the app:
   ```bash
   ./mvnw spring-boot:run
   ```
   The API will be available at `http://localhost:8080`.

## API endpoints

### Search & compare prices

```
GET /products/search?name={query}
```

Searches locally stored products by name (case-insensitive, partial match) and returns matching prices sorted from cheapest to most expensive.

**Example**

```
GET /products/search?name=eier
```

```json
[
  { "retailerName": "Aldi", "price": 2.49 },
  { "retailerName": "Aldi", "price": 2.99 }
]
```

### Import products from Aldi

```
POST /import/aldi
```

Fetches current egg products from Aldi's Algolia index and stores them in the local database.

## Known limitations / roadmap

This is a work-in-progress learning project. Some things are intentionally simplified for now:

- [ ] Only Aldi is supported — `RetailerFetcher` is an interface specifically so a `LidlFetcher` (or others) can be added later without changing the rest of the app.
- [ ] The import only fetches products matching a single hardcoded search term (`eier`), not the full Aldi catalog.
- [ ] Re-running the import creates a new `PriceRecord` each time rather than checking for recent duplicates.
- [ ] The Algolia API key is currently hardcoded in `AldiFetcher` — should move to `application.properties` / environment variables.
- [ ] No scheduled/automatic imports yet (triggered manually via the endpoint).

