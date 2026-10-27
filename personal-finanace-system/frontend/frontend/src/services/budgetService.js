const API_BASE = import.meta.env.VITE_API_URL || "http://localhost:8080";
async function request(path, options = {}) {
  let response;
  try {
    response = await fetch(`${API_BASE}${path}`, {
      ...options, credentials: "include",
      headers: { Accept: "application/json", "Content-Type": "application/json", ...(options.headers || {}) },
    });
  } catch {
    throw new Error("Cannot connect to the backend. Make sure Spring Boot is running on port 8080.");
  }
  const data = await response.json().catch(() => null);
  if (!response.ok) throw new Error(data?.message || data?.error || `Request failed (${response.status})`);
  return data;
}
export const getBudgets = (month) => request(`/api/budgets?month=${encodeURIComponent(`${month}-01`)}`);
export const createBudget = (budget) => request("/api/budgets", { method: "POST", body: JSON.stringify(budget) });
export const updateBudget = (id, budget) => request(`/api/budgets/${id}`, { method: "PUT", body: JSON.stringify(budget) });
export const deleteBudget = (id) => request(`/api/budgets/${id}`, { method: "DELETE" });
