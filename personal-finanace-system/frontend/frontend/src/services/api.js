import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  withCredentials: true,
  headers: { "Content-Type": "application/json" },
});

let csrfToken = null;
let csrfHeader = "X-CSRF-TOKEN";

export async function refreshCsrfToken() {
  const response = await api.get("/api/auth/csrf");
  csrfToken = response.data.token;
  csrfHeader = response.data.headerName;
}

api.interceptors.request.use((config) => {
  const method = (config.method || "get").toLowerCase();

  if (!["get", "head", "options", "trace"].includes(method)
      && csrfToken) {
    config.headers[csrfHeader] = csrfToken;
  }

  return config;
});

export async function login(email, password) {
  await refreshCsrfToken();

  await api.post("/api/auth/login", { email, password });

  // Spring Security clears the old CSRF token during authentication.
  await refreshCsrfToken();

  return api.get("/api/auth/me");
}

export async function logout() {
  await refreshCsrfToken();
  await api.post("/api/auth/logout");
  csrfToken = null;
  await refreshCsrfToken();
}

export default api;