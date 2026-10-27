
const API_BASE = "http://localhost:8080/api/auth";

async function request(path, options = {}) {
  let response;

  try {
    response = await fetch(`${API_BASE}${path}`, {
      ...options,
      credentials: "include",
      headers: {
        "Content-Type": "application/json",
        ...options.headers,
      },
    });
  } catch {
    throw new Error(
      "Cannot connect to the server. Check whether Spring Boot is running."
    );
  }

  const data = await response.json().catch(() => ({}));

  if (!response.ok) {
    throw new Error(
      data.message || `Request failed (${response.status})`
    );
  }

  return data;
}

export const registerUser = (user) =>
  request("/register", {
    method: "POST",
    body: JSON.stringify(user),
  });

export const loginUser = (credentials) =>
  request("/login", {
    method: "POST",
    body: JSON.stringify(credentials),
  });

export const getCurrentUser = () =>
  request("/me", { method: "GET" });

export const logoutUser = () =>
  request("/logout", { method: "POST" });
