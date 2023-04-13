// Change this API_URL to your own backend API
export const API_URL = "http://localhost:8080/api/v1";

// Common auth header
export const AUTH_HEADERS = async (token) => {
    return {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
    };
};
