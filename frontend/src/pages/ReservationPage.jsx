import { useState, useEffect, useRef } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import "../styles/globals.css";
import api from "../api";

const ReservationPage = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const { type } = location.state || { type: "Car" };

    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [duration, setDuration] = useState(1);
    const [itemId, setItemId] = useState("");
    const [error, setError] = useState("");
    const [user, setUser] = useState(null);

    const [loading, setLoading] = useState(true);
    const fetchedOnce = useRef(false);

    const [hasFetched, setHasFetched] = useState(false);

    useEffect(() => {
        let isMounted = true;

        const fetchUser = async () => {
            try {
                const response = await api.get("http://localhost:8080/api/users/me");
                if (isMounted) {
                    setUser(response.data);
                }
            } catch (error) {
                console.error("Error loading user:", error);
                if (isMounted) {
                    setUser(null);
                }
            } finally {
                if (isMounted) {
                    setLoading(false);
                }
            }
        };

        fetchUser();

        return () => {
            isMounted = false;
        };
    }, []);
    
    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
    
        if (!user) {
            setError("Error: user not found");
            console.error("Error: user not found");
            return;
        }
    
        const reservationData = {
            type,
            startDate,
            endDate,
            duration,
            status: "Active",
            user: { id: user.id },
            itemId
        };
    
        try {
            const token = localStorage.getItem("auth_token");
            const response = await fetch("http://localhost:8080/api/reservations/create", {
                method: "POST",
                headers: { 
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(reservationData)
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Reservation error: ${errorText}`);
            }
    
            navigate("/dashboard");
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div className="page-container">
            <div className="content-container">
                <h1 className="title" style={{ color: "var(--keyword)" }}>Reservation {type}</h1>
                {error && <p className="error-message">{error}</p>}
                <form onSubmit={handleSubmit} className="form-grid">
                    <div className="form-group">
                        <label className="input-label">Start Date:</label>
                        <input
                            type="date"
                            className="input-field input-bordered"
                            value={startDate}
                            onChange={(e) => setStartDate(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label className="input-label">End Date:</label>
                        <input
                            type="date"
                            className="input-field input-bordered"
                            value={endDate}
                            onChange={(e) => setEndDate(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label className="input-label">Duration (days):</label>
                        <input
                            type="number"
                            className="input-field input-bordered"
                            value={duration}
                            min="1"
                            onChange={(e) => setDuration(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label className="input-label">Item ID:</label>
                        <input
                            type="number"
                            className="input-field input-bordered"
                            value={itemId}
                            onChange={(e) => setItemId(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <button type="submit" className="submit-button">Confirm Reservation</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default ReservationPage;