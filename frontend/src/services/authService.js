import api from "../api";

export const register = async (userData) => {
    try {
        
        const formattedData = {
            username: userData.name,
            email: userData.email,
            password: userData.password
        };

        const response = await api.post("/auth/register", JSON.stringify(formattedData), {
            headers: { "Content-Type": "application/json" }
        });

        localStorage.setItem("auth_token", response.data.token);

        return response.data;
    } catch (err) {
        console.error("Registration error:", err.response?.data || err.message);
        throw err;
    }
};

export const login = async (username, password) => {
    try {
        const response = await fetch("http://localhost:8080/api/auth/login", {
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

        localStorage.setItem("auth_token", data.token);
        return response;

    } catch (err) {
        console.error("❌ Full login error:", err);
        console.error("❌ Error message:", err.message);
        console.error("❌ Stack Trace:", err.stack);
    }
};  

export const logout = () => {
    localStorage.removeItem("token");
};