# Personal Finance System

A Java/Spring Boot + MySQL backend with a React/Vite frontend for personal income and expense tracking. Features include registration/login, owner-scoped transactions and categories, monthly category budgets, transaction history, and a monthly dashboard. There is no bank integration or financial advice.

## Technology
- Java 17, Spring Boot, Spring Web, Spring Data JPA, Spring Security, Bean Validation, Maven
- MySQL
- React, Vite, React Router
- `BigDecimal`/SQL `DECIMAL(15,2)` for money

## Requirements
Install JDK 17+, MySQL 8+, Node.js 18+ and npm. Start MySQL and create the database:

```sql
CREATE DATABASE personal_finance CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Configure database access using environment variables (recommended):

```bash
export DB_URL='jdbc:mysql://localhost:3306/personal_finance'
export DB_USERNAME='your_mysql_user'
export DB_PASSWORD='your_mysql_password'
```

The defaults are in `src/main/resources/application.properties`; adjust them for your local MySQL installation. Hibernate `ddl-auto=update` is for local development/demo use, not production migrations.

## Run backend
From the project root:

```bash
./mvnw spring-boot:run
```

If the wrapper is unavailable, use `mvn spring-boot:run`. Backend listens on `http://localhost:8080`.

## Run frontend
```bash
cd frontend/frontend
npm install
npm run dev
```

Open `http://localhost:5173`. `frontend/frontend/.env` sets `VITE_API_URL=http://localhost:8080`. CORS is configured for that local origin. Register a user and sign in.

## Demo workflow
1. Open **Transactions**. The application automatically creates starter income and expense categories for new users and repairs existing accounts that have no categories. You can also add custom categories.
2. Record salary ₹40,000, rent ₹12,000, groceries ₹5,000 and transport ₹2,000.
3. Open **Dashboard** and select the transaction month: expected income ₹40,000, expenses ₹19,000, net balance ₹21,000.
4. Open **Budgets** and set a monthly limit for each expense category. The budget page shows spent, remaining/over amount, and status. Transaction edits/deletes are reflected after refresh.

## API overview
All routes below except registration/login require an authenticated session.

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/register` | Register user |
| POST | `/api/auth/login` | Sign in |
| POST | `/api/auth/logout` | Sign out |
| GET | `/api/auth/me` | Current user |
| GET/POST | `/api/categories` | List/create own categories |
| GET | `/api/transactions?start=YYYY-MM-DD&end=YYYY-MM-DD` | Filter transaction history |
| GET/POST | `/api/transactions`, GET/PUT/DELETE `/api/transactions/{id}` | Transaction CRUD |
| GET | `/api/dashboard?month=YYYY-MM` | Monthly totals and recent transactions |
| GET | `/api/budgets?month=YYYY-MM-01` | List selected month's budgets |
| POST | `/api/budgets` | Create budget (`categoryId`, `month`, `limitAmount`) |
| PUT/DELETE | `/api/budgets/{id}` | Update limit / delete budget |

## Validation and security notes
- Passwords are BCrypt-hashed; authentication uses a server session and HttpOnly session cookie.
- Backend ownership is derived from the authenticated user. Transaction/category/budget queries are owner-scoped.
- Transactions require positive amounts, a past-or-present date, notes <= 500 chars and a matching owned category/type.
- Budgets require a positive amount, an owned expense category, and are unique by user/category/month.
- For production, add CSRF protection appropriate to cookie sessions, HTTPS, schema migrations (Flyway/Liquibase), rate limiting, and environment-specific CORS. Do not use `ddl-auto=update` in production.

## Tests and project documentation
Run `./mvnw test`. See `docs/USE_CASES.md`, `docs/ER_DIAGRAM.md` and `docs/TEST_PLAN.md` for user stories, schema relationships and acceptance checks. Record screenshots and test output as submission evidence after running the application locally.
