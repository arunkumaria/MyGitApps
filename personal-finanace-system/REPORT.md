# Personal Finance System — concise project report

## Abstract
This full-stack application lets an authenticated user record income and expenses, classify records, define monthly category budgets and review monthly financial summaries. It intentionally does not connect to banks or provide financial advice.

## Objectives
- Secure user-specific financial record management.
- Accurate monthly income, expense and net-balance calculations.
- Category-level budget planning and over-budget indicators.
- Demonstrate Java full-stack design, relational persistence, validation and session authentication.

## Architecture
React/Vite provides registration/login, dashboard, transaction history/CRUD and budget management. Spring Boot exposes REST endpoints; Spring Security handles session authentication and BCrypt password hashing; Spring Data JPA persists users, categories, transactions and budgets in MySQL. Money uses `BigDecimal` in Java and decimal columns in the database. Backend ownership checks scope data to the signed-in user.

## Main entities
`User` owns `Category`, `Transaction` and `Budget` records. A transaction references a category and has type, amount, date and notes. A budget references an expense category and one calendar month, with one unique budget per user/category/month. See `docs/ER_DIAGRAM.md`.

## Example result
With ₹40,000 income and expenses of ₹12,000 rent, ₹5,000 groceries and ₹2,000 transport, monthly expenses total ₹19,000 and net balance is ₹21,000. Category budget progress is calculated from stored expenses in that month and updates after transaction changes.

## Validation and testing
The API validates positive monetary amounts, decimal precision, dates, notes length, category type/ownership and budget uniqueness. Existing automated tests are in `src/test/java`; run `./mvnw test`. `docs/TEST_PLAN.md` contains manual security, CRUD, empty-month and budget acceptance checks. Add actual terminal output/screenshots from the local environment before submission; they are not represented as executed here.

## Team allocation
- Surender R — add the actual contribution summary before submission.
- Dipti Shankar Shinde — add the actual contribution summary before submission.
- Guirguis Nashat Zaki — add the actual contribution summary before submission.

## Limitations and future work
This demo uses Hibernate schema auto-update and local-origin CORS. For deployment, use managed schema migrations, HTTPS, environment-specific configuration and CSRF protection appropriate for cookie sessions. Optional stretch goals include CSV export and recurring transaction templates.
