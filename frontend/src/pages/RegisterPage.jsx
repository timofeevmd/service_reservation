import "../styles/globals.css";
import { useState } from "react";
import { register } from "../services/authService";
import { useNavigate } from "react-router-dom";

const RegisterPage = () => {
    const [formData, setFormData] = useState({ name: "", email: "", password: "" });
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await register(formData);
            navigate("/dashboard");
        } catch (err) {
            setError(err.response?.data?.message || "Registration error");
        }
    };

    return (
        <div className="page-container">
            <div className="login-box">
                <h2 className="title">Registration</h2>
                {error && <p className="error-message">{error}</p>}
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label className="input-label">Name:</label>
                        <input
                            type="text"
                            name="name"
                            className="input-field input-bordered"
                            value={formData.name}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label className="input-label">Email:</label>
                        <input
                            type="email"
                            name="email"
                            className="input-field input-bordered"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label className="input-label">Password:</label>
                        <input
                            type="password"
                            name="password"
                            className="input-field input-bordered"
                            value={formData.password}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <button type="submit" className="submit-button">
                            Register
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default RegisterPage;
