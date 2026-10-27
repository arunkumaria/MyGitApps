# Test plan and evidence checklist

Automated backend tests are under `src/test/java/com/finance` and run with `./mvnw test` (or `mvn test`). Existing tests cover application context, transaction request validation/service behavior and dashboard calculations.

## Manual acceptance checklist
- [ ] Register a new user, sign out, sign back in; verify password is never stored as plain text (BCrypt hash in `users.password_hash`).
- [ ] Add income ₹40,000 and expenses ₹12,000, ₹5,000 and ₹2,000; dashboard should show ₹40,000 income, ₹19,000 expenses, ₹21,000 balance.
- [ ] Edit an amount and verify totals/budget progress update; delete a transaction and verify totals update.
- [ ] Filter transaction history by month/date and verify other months do not appear.
- [ ] Create an expense category and a monthly budget. Verify spending at 80% is marked Near limit and spending above 100% is Over budget.
- [ ] Verify zero transactions in a month yields zero totals and an empty history.
- [ ] Submit amount zero/negative, more than two decimal places, a future date, invalid category/type pairing, or a duplicate budget; API should reject the request.
- [ ] Sign in as a second user and verify the first user's transactions/categories/budgets cannot be read, changed or deleted (404/400/401 as appropriate).
- [ ] Call a protected API while signed out and verify it is rejected.

## Test evidence
Capture terminal output from `./mvnw test`, browser screenshots of the dashboard, transaction CRUD, budget status, and an invalid/unauthorized request. Do not claim these manual checks passed until actually executed against the configured database.
