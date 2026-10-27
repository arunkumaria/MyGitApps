const API_BASE = import.meta.env.VITE_API_URL || "http://localhost:8080";

async function request(path, options = {}) {
  let response;
  try {
    response = await fetch(`${API_BASE}${path}`, {
      ...options,
      credentials: "include",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        ...(options.headers || {}),
      },
    });
  } catch {
    throw new Error("Cannot connect to the backend. Make sure Spring Boot is running on port 8080.");
  }

  const data = await response.json().catch(() => null);
  if (!response.ok) {
    throw new Error(data?.message || data?.error || `Request failed (${response.status})`);
  }
  return data;
}

export function getTransactions(start, end) {
  const query = new URLSearchParams({ start, end });
  return request(`/api/transactions?${query}`);
}

export function createTransaction(transaction) {
  return request("/api/transactions", { method: "POST", body: JSON.stringify(transaction) });
}

export function updateTransaction(id, transaction) {
  return request(`/api/transactions/${id}`, { method: "PUT", body: JSON.stringify(transaction) });
}

export function deleteTransaction(id) {
  return request(`/api/transactions/${id}`, { method: "DELETE" });
}

export function getCategories() {
  return request("/api/categories");
}

export function createCategory(category) {
  return request("/api/categories", { method: "POST", body: JSON.stringify(category) });
}
