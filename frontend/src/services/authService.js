import api from "../api";

const AUTH_STORAGE_KEY = import.meta.env.VITE_AUTH_STORAGE_KEY || "auth_token";

export const register = async (userData) => {
    try {
        const response = await api.post("/auth/register", {
            username: userData.name,
            password: userData.password,
        });

        localStorage.setItem(AUTH_STORAGE_KEY, response.data.token);

        return response.data;
    } catch (err) {
        console.error("Registration error:", err.response?.data || err.message);
        throw err;
    }
};

export const login = async (username, password) => {
    const response = await fetch(`/api/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
        credentials: "include",
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Error ${response.status}: ${errorText}`);
    }

    const data = await response.json();

    localStorage.setItem(AUTH_STORAGE_KEY, data.token);
    return data;
};

export const logout = () => {
    localStorage.removeItem(AUTH_STORAGE_KEY);
};