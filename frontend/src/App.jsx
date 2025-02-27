import { Routes, Route } from "react-router-dom";
import React, { useEffect } from "react";
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";
import DashboardPage from "./pages/DashboardPage";
import NotFoundPage from "./pages/NotFoundPage";
import RegisterPage from "./pages/RegisterPage";
import ReservationsPage from "./pages/ReservationPage";

const AUTH_STORAGE_KEY = import.meta.env.VITE_AUTH_STORAGE_KEY || "auth_token";

function App() {

    useEffect(() => {
        console.log("deleted token from localStorage");
        localStorage.removeItem(AUTH_STORAGE_KEY);
    }, []);


    return (
        <Routes>
            <Route path="*" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/dashboard" element={<DashboardPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/reservations" element={<ReservationsPage />} />
        </Routes>
    );
}

export default App;
