# Personal Finance System — Transaction CRUD

This project version adds a real Transactions page connected to the existing Spring Boot APIs. It does not use hard-coded demo amounts: all records are saved to the database and are scoped to the authenticated user by the backend.

## Features
- Create income and expense records.
- Read/list records for the selected month.
- Edit an existing record.
- Delete a record after a confirmation prompt.
- Create categories for income or expenses when needed.
- Monthly income, expense and balance totals on the Transactions page.
- Dashboard route points to the full `src/pages/Dashboard.jsx` page.
- Dashboard summary updates from the same persisted transactions.

## Run locally

### Backend
1. Ensure MySQL is running and `src/main/resources/application.properties` has the correct database URL, username and password.
2. From the project root, run `./mvnw spring-boot:run` (or `mvn spring-boot:run` if Maven is installed).
3. Backend default URL: `http://localhost:8080`.

### Frontend
1. Open a second terminal and run:
   ```bash
   cd frontend/frontend
   npm install
   npm run dev
   ```
2. Open the local URL printed by Vite (usually `http://localhost:5173`).
3. Sign in, then choose **Transactions** from the dashboard navigation.

## How to use
1. Choose the report month.
2. If no category exists for the selected type, click **+ New category**, create one (e.g. `Groceries` for Expense or `Salary` for Income), and select it.
3. Enter type, amount, category, date and optional notes, then click **Add transaction**.
4. Use **Edit** to change a record or **Delete** to permanently remove it. The dashboard sums are computed from the database, so they reflect these changes on refresh/navigation.

## API endpoints used
- `GET /api/categories`
- `POST /api/categories`
- `GET /api/transactions?start=YYYY-MM-DD&end=YYYY-MM-DD`
- `POST /api/transactions`
- `PUT /api/transactions/{id}`
- `DELETE /api/transactions/{id}`
- `GET /api/dashboard?month=YYYY-MM`

The backend's existing transaction service checks record ownership and category ownership. Do not put real financial records in a demo database that is not backed up.
