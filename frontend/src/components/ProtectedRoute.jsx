import { Navigate } from "react-router-dom";

const AUTH_STORAGE_KEY = import.meta.env.VITE_AUTH_STORAGE_KEY || "auth_token";

const ProtectedRoute = ({ children }) => {
    const isAuthenticated = localStorage.getItem(AUTH_STORAGE_KEY);

    return isAuthenticated ? children : <Navigate to="/login" />;
};

export default ProtectedRoute;
