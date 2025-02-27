import "../styles/globals.css";
import { useState } from "react";
import { login } from "../services/authService";
import { useNavigate } from "react-router-dom";

function LoginPage() {
    const [identifier, setIdentifier] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError(null); 
    
        try {
            const response = await login(identifier, password);
            if (!response.ok) {
                const errorData = await response.json();
                console.log("Error data:", errorData);
                throw new Error(errorData.error || "Authorization error");
            }
            const data = response.headers.get("content-length") > 0 ? await response.json() : {};
            localStorage.setItem("token", data.token || "");    
            navigate("/dashboard");
    
        } catch (err) {
            setError(err.message);
        }
    };
    

    return (
        <div className="page-container">
            <div className="login-box">
                <h2 className="title">Login</h2>
                {error && <p className="error-message">{error}</p>}
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label className="input-label">Email or Username:</label>
                        <input
                            type="text"
                            className="input-field input-bordered"
                            value={identifier}
                            onChange={(e) => setIdentifier(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label className="input-label">Password:</label>
                        <input
                            type="password"
                            className="input-field input-bordered"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <button type="submit" className="submit-button">
                            Login
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );

}

export default LoginPage;
