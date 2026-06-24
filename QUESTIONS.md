# Questions

Answer each question in your own words. Aim for three to eight sentences per answer —
enough to show that you understand the concept, not so much that you are restating a
textbook entry. We are looking for clarity of thinking, not exhaustive coverage.

---

## Java & Object-Oriented Design

**1.** The `Auditable` abstract class in this codebase uses `@MappedSuperclass`. Explain
what this annotation tells JPA, and describe what would happen to the database schema if
you removed it. Why is it better to put `createdAt` and `updatedAt` in a shared abstract
class rather than adding those fields directly to `Transaction` and `Account` separately?

   @MappedSuperclass tells JPA to include the parent class fields in child entity tables. Without it, createdAt and updatedAt would not be mapped properly. Using a shared abstract class avoids duplicate code and keeps audit fields consistent across entities.

**2.** `TransactionService` is defined as a Java interface, with `TransactionServiceImpl`
as its only implementation. A new engineer on the team asks: "Why bother with the interface
if there's only one implementation? Isn't it just extra boilerplate?" How do you respond?
Give at least one concrete scenario where the interface pays off.

     The interface separates the contract from the implementation. It makes testing easier and allows future implementations without changing dependent code. For example, we could add a cached or external service implementation later.

**3.** `Category` is modelled as an enum rather than a plain `String` field on
`Transaction`. What does storing it as `@Enumerated(EnumType.STRING)` in the database
actually produce in the table? What would go wrong if a future developer added a new
category value to the enum but forgot to handle database migration?

     @Enumerated(EnumType.STRING) stores values like FOOD and TRANSPORT as text in the database. This makes the data readable and safer than numeric indexes. If a new enum value is added but not handled correctly, application logic may fail or produce inconsistent results.

**4.** `BudgetCalculator` is a `final` class with a private constructor and a single
static method. What pattern is this, and why is it appropriate for this specific utility?
In your implementation, what data structure did you use as an intermediate step before
building the final sorted map, and why?

     This is a utility class pattern. The class contains helper methods and does not need object creation. I used a Map<Category, BigDecimal> to group and accumulate spending before sorting the results.

---

## Spring Boot & REST API Design

**5.** The original `POST /api/transactions` endpoint returned a `ResponseEntity<Transaction>`
rather than a `ResponseEntity<TransactionResponse>`. Explain specifically what was wrong
with this. What does a DTO (data transfer object) protect against, and what risks does
returning an entity directly introduce?

     Returning an entity exposes internal database details directly to API consumers. A DTO provides a controlled response structure and prevents accidental exposure of fields. It also allows the API contract to evolve independently from the database model.

**6.** When a `POST` request arrives at `TransactionController`, describe the complete
journey from HTTP request to database insert. Name each layer the request passes through,
what each layer is responsible for, and what would happen if the `@Valid` annotation were
removed from the method parameter.

      The request enters the controller, passes validation, then moves to the service layer for business logic. The service calls the repository, which persists data through JPA. Without @Valid, invalid requests could reach the service and database.

**7.** Spring Boot uses `@RestController`, `@Service`, and `@Repository` as stereotype
annotations. They all ultimately do the same thing (register a bean). Why does Spring
provide three different annotations instead of one? What does the distinction communicate
to a developer reading the code?

      All three annotations create Spring beans, but they describe different responsibilities. @RestController handles HTTP requests, @Service contains business logic, and @Repository manages data access. This improves readability and maintainability.

**8.** The `GET /api/transactions/monthly-spend` endpoint accepts `year` and `month` as
query parameters. What HTTP status code should this endpoint return if `month=13` is
passed? Who is responsible for validating it — the controller, the service, or Spring
itself — and how would you implement that validation?

     The endpoint should return 400 Bad Request for month=13. Input validation should happen before business logic execution. I would use validation annotations such as @Min(1) and @Max(12) on the request parameter.

---

## Data Access & SQL

**9.** `TransactionRepository` extends `JpaRepository<Transaction, Long>`. Spring Data JPA
can generate a query from a method named `findByAccountId`. Explain the mechanism behind
this — what is Spring doing at startup to turn that method name into SQL? When would you
write a `@Query` annotation instead of relying on derived query methods?

    Spring Data JPA reads repository method names during startup and generates the corresponding queries automatically. For example, findByAccountId becomes a SQL query based on the entity field. I would use @Query for complex or custom queries.

10.


**10.** `calculateMonthlySpend` had a bug in the date boundary comparison. Describe the
bug in plain language — what was the incorrect behaviour, what caused it at the code level,
and what kind of test input reliably exposes this class of off-by-one error? Why is this
type of bug particularly common in date/time logic?

     The bug excluded transactions occurring on the first day of the month. This happened because isAfter() performs a strict comparison. A transaction dated exactly on the month's first day reliably exposes the issue.

**11.** The application uses H2 in-memory for development. Describe exactly what you
would change to point this application at a PostgreSQL database in production. Be specific:
which files, which properties, and which Maven dependency. What is the risk of using
`spring.jpa.hibernate.ddl-auto=create-drop` in production?

       I would replace the H2 dependency with the PostgreSQL driver in pom.xml and update datasource properties in application.properties. Production should use PostgreSQL connection settings. Using ddl-auto=create-drop is risky because it can delete data.

---

## Testing

**12.** `TransactionServiceTest` uses `@Mock` on `TransactionRepository` and
`@InjectMocks` on `TransactionServiceImpl`. Explain what Mockito is doing here. What is
the repository being replaced with, and what does the test actually verify? What category
of bug can this test suite catch — and what category can it not?

     Mockito creates a fake repository and injects it into the service. This allows testing business logic without a real database. These tests can catch service-level bugs but cannot verify actual database behavior or SQL queries.

**13.** A teammate argues that because the service tests cover all the logic, there is no
need to write controller tests. Do you agree? Describe one specific type of bug that a
controller-level test (using `MockMvc`) would catch that the service tests in this project
would miss entirely.

     No, controller tests are still important. They verify request mapping, validation, JSON serialization, and HTTP status codes. A controller test can catch missing @Valid annotations, which service tests would completely miss.

**14.** Looking at the tests you wrote in `TransactionCandidateTest.java`: what was the
first test you wrote, and why did you choose to start there? What does the order in which
you wrote tests tell you about how you approached the problem?

      My first test focused on the monthly spending calculation because it contained a known business rule and potential bug. I wanted to verify that transactions on the first day of the month were included. This helped validate the most critical logic first.

---

## AI & Modern Engineering

**15.** Describe how you used AI tools during this project. For at least two specific
examples: what did you prompt the tool with, what did it return, and what did you change
or reject? Identify one place where the AI output was immediately trustworthy and one
place where it required meaningful scrutiny before you used it.

     I used AI to review the project structure and identify potential bugs. It helped explain the date comparison issue and suggested areas requiring implementation. I verified all suggestions manually before applying them to the codebase.
