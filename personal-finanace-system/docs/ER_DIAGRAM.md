# Relational ER diagram

```mermaid
erDiagram
    USERS ||--o{ CATEGORIES : owns
    USERS ||--o{ TRANSACTIONS : records
    USERS ||--o{ BUDGETS : plans
    CATEGORIES ||--o{ TRANSACTIONS : classifies
    CATEGORIES ||--o{ BUDGETS : limits
    USERS {
        bigint id PK
        varchar name
        varchar email UK
        varchar password_hash
        varchar role
    }
    CATEGORIES {
        bigint id PK
        bigint user_id FK
        varchar name
        varchar type
    }
    TRANSACTIONS {
        bigint id PK
        bigint user_id FK
        bigint category_id FK
        varchar type
        decimal amount
        date transaction_date
        varchar notes
    }
    BUDGETS {
        bigint id PK
        bigint user_id FK
        bigint category_id FK
        date budget_month
        decimal limit_amount
    }
```

Constraints: unique `(user_id, name, type)` on categories; unique `(user_id, category_id, budget_month)` on budgets; non-null ownership/category/date/type/amount fields; decimal precision/scale 15,2 for transaction and budget amounts. The application also checks ownership in backend queries. Hibernate `ddl-auto=update` creates/updates the schema for local development.
