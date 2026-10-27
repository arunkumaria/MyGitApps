const API_URL = "http://localhost:8080/api/dashboard";

export async function getDashboard(month) {
	let response;

	try {
		response = await fetch(
			`${API_URL}?month=${encodeURIComponent(month)}`,
			{
				method: "GET",
				credentials: "include",
				headers: { Accept: "application/json" },
			}
		);
	} catch {
		throw new Error(
			"Cannot connect to the backend. Check whether Spring Boot is running."
		);
	}

	const data = await response.json().catch(() => ({}));

	if (!response.ok) {
		throw new Error(data.message || "Unable to load dashboard.");
	}

	return data;
}