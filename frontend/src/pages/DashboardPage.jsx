import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import LogoutButton from "../components/LogoutButton";
import api from "../api";

const DashboardPage = () => {
    const navigate = useNavigate();
    const [reservations, setReservations] = useState([]);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const [hasMore, setHasMore] = useState(true);
    const pageRef = useRef(0);
    const tableRef = useRef(null);

    useEffect(() => {
        fetchReservations();
    }, []);

    const fetchReservations = async () => {
        if (loading || !hasMore) return;

        setLoading(true);
        try {
            const response = await api.get(`/reservations/user?limit=10&offset=${pageRef.current * 15}`);
            if (response.data.length > 0) {
                setReservations((prev) => [...prev, ...response.data]);
                pageRef.current += 1;
            } else {
                setHasMore(false);
            }
        } catch (err) {
            setError("Error loading reservations");
            console.error("Error:", err);
        }
        setLoading(false);
    };

    const handleScroll = () => {
        if (
            tableRef.current &&
            tableRef.current.scrollTop + tableRef.current.clientHeight >= tableRef.current.scrollHeight - 10
        ) {
            fetchReservations();
        }
    };

    const handleCancelReservation = async (id) => {
        if (!window.confirm("Are you sure you want to cancel the reservation?")) return;
        
        try {
            await api.delete(`/reservations/${id}`);
            setReservations(reservations.map(reservation => 
                reservation.id === id ? { ...reservation, status: "Completed" } : reservation
            ));
            alert("Reservation canceled");
        } catch (error) {
            console.error("Error canceling reservation:", error);
            alert("Failed to cancel reservation");
        }
    };

    return (
        <div className="page-container">
            <div className="content-container">
                <h1 className="title" style={{ color: "var(--keyword)" }}>Dashboard</h1>
                <p className="subtitle">Welcome to the reservation system!</p>

                <div className="button-container">
                    <button 
                        className="button button-primary"
                        onClick={() => navigate("/reservations", { state: { type: "Car" } })}
                    >
                        Book a Car
                    </button>
                    <button 
                        className="button button-primary"
                        onClick={() => navigate("/reservations", { state: { type: "Apartments" } })}
                    >
                        Book an Apartment
                    </button>
                </div>

                <h2 className="subtitle keyword-text" style={{ color: "var(--keyword)" }}>Reservation List</h2>
                {error && <p className="error-message">{error}</p>}

                <div 
                    ref={tableRef}
                    className="table-container"
                    onScroll={handleScroll}
                >
                    <table className="reservations-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Type</th>
                                <th>Date</th>
                                <th>Status</th>
                                <th>Cancel</th>
                            </tr>
                        </thead>
                        <tbody>
                            {reservations.length > 0 ? (
                                reservations.map((res) => (
                                    <tr key={res.id}>
                                        <td>{res.id}</td>
                                        <td>{res.type}</td>
                                        <td>{res.startDate}</td>
                                        <td>{res.status}</td>
                                        <td>
                                            <button 
                                                onClick={() => handleCancelReservation(res.id)} 
                                                disabled={res.status !== "Active"}
                                            >
                                                Complete
                                            </button>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="5" className="no-reservations">You have no reservations yet</td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>

                {loading && <p className="loading-text">Loading...</p>}
                <LogoutButton />
            </div>
        </div>
    );
};

export default DashboardPage;
