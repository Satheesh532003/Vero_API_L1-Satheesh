# Decisions

**Name: Satheesh P S

**Date started: 19/06/2026

**Date submitted: 24/06/2026

In two or three sentences, describe your overall approach before getting into specifics.
What did you read first? What did you prioritise, and why?

---

## 1. Code & Design Decisions

**The codebase includes an `Auditable` abstract class that is not currently used by any
entity. What did you do with it, if anything? Walk through your reasoning — what is the
purpose of the Auditable pattern, what are the tradeoffs of using it versus not, and why
did you make the choice you did?**

   I kept the Auditable class because it avoids duplicating audit fields across entities. It improves maintainability and follows a common JPA pattern.

**`TransactionResponse` is used as the outbound DTO for the API. What changes did you
make to it, if any? Why does the shape of a response DTO matter — and what is the risk
of returning an entity directly from a controller?**

    I used TransactionResponse to control what the API exposes. Returning entities directly can expose internal database details.

**The `BudgetCalculator` requires grouping and sorting data. What data structure or
approach did you choose to implement it? Walk through the alternatives you considered
and why you landed where you did.**

    I used a Map<Category, BigDecimal> to group totals and a LinkedHashMap to keep sorted results. It is simple and efficient.

**Were there any decisions you made that are not covered by the questions above? Describe
the most significant one and your reasoning.**

    My main focus was fixing the monthly spend calculation because it affects customer-facing functionality.
  
## 2. Bug Fixes & Issues Found

**Describe each problem you found in the codebase. For each one: where was it, how did
you identify it, what did it cause, and how did you fix it?**

    I found a date boundary bug in monthly spend calculations and incomplete implementations in utility and service methods. I fixed them and added tests.

**Were there any problems you noticed but chose not to fix? If so, explain why.**

     I avoided large architectural changes because they were outside the assessment scope.

## 3. Testing Decisions

**What tests did you write in `TransactionCandidateTest.java`? For each test, explain
what behaviour it validates and why you chose to cover that behaviour.**

     I added tests for monthly spending and budget aggregation. These cover the most important business logic.

**What did you deliberately not test, and why? If you had more time, what would be the
next most important test to add?**

     I did not add integration tests due to time constraints. Controller tests would be my next priority.

**What is the difference between what `TransactionServiceTest` covers and what your
`TransactionCandidateTest` covers? Are they testing the same things?**

    Existing tests cover service behaviour. My tests focus on the bugs and functionality I modified.

## 4. AI Tool Usage

AI tool usage is expected and encouraged. Using AI is not cheating — it is a core skill
of modern engineering. What we are evaluating is whether you used it thoughtfully:
whether you understood and verified what it produced, and whether you can recognise
when its output should not be trusted.

**Which AI tools did you use? (e.g. ChatGPT, Claude, GitHub Copilot, Cursor, other)**

     I used ChatGPT for code review, understanding the project structure, and discussing solutions.

**Give two or three specific examples of how you used AI on this project. For each:
what did you prompt it with, what did it return, and what did you accept, change, or
reject?**

      I used AI to identify the monthly spend bug and suggest approaches for BudgetCalculator. I verified all suggestions before using them.

**Describe a moment where AI gave you something wrong, incomplete, or subtly misleading.
How did you catch it, and what did you do?**

      Some implementation suggestions did not fully match the project structure. I checked the code before applying changes.

**What is your general philosophy on using AI when writing backend code? Where does it
help, and where do you not trust it?**

      AI is useful for learning and reviewing code, but all outputs should be verified before use.

## 5. What You'd Do Next

**If you had two more days on this project, what would you build or fix first?
List in priority order, with one sentence of justification for each.**

      Two More Days:
      
      1.Add controller tests.
      2.Improve validation.
      3.Add integration tests.

**What is the biggest remaining risk or weakness in the code you have submitted?**

     The main remaining risk is limited integration and end-to-end test coverage.
