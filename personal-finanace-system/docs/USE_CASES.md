# Use cases and business rules

## Core user stories
- As a visitor, I can register and sign in so my records are private to my account.
- As an authenticated user, I can create, view, edit, delete and date-filter income/expense transactions.
- As an authenticated user, I can create income and expense categories and assign transactions only to my own category of the matching type.
- As an authenticated user, I can set, edit and delete a monthly limit for each expense category.
- As an authenticated user, I can see selected-month income, expenses, net balance, recent history, category spending and budget status.

## Rules implemented
1. All transaction, category and budget API routes require an authenticated session.
2. The backend resolves the user from the authenticated email; client-supplied owner IDs are never trusted.
3. Transaction and budget amounts must be positive decimal values with at most two fractional digits. Transaction dates cannot be in the future; notes are limited to 500 characters.
4. A transaction must reference an expense category for an expense or an income category for income, and that category must belong to the signed-in user.
5. Budgets are expense-category limits, normalized to the first day of the selected month. A user cannot create duplicate budgets for the same category/month.
6. Income, expenses and balance are calculated from stored records using `BigDecimal`/SQL decimal values. Balance = income - expenses.
7. Budget spent = sum of that user's expense transactions for the category within the selected calendar month. At least 80% of the limit is “Near limit”; spending above the limit is “Over budget”.
8. An empty month returns zero totals and an empty transaction list.

## Demo scenario
For a month, record salary income ₹40,000, rent ₹12,000, groceries ₹5,000 and transport ₹2,000. The dashboard should show income ₹40,000, expenses ₹19,000 and balance ₹21,000. Add rent/groceries/transport budgets to see category spending against limits.
