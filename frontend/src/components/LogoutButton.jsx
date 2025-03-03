import { useNavigate } from "react-router-dom";
import { logout } from "../services/authService";

const LogoutButton = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate("/");
    };

    return <button className="button button-primary" onClick={handleLogout}>logout</button>;
    
};

export default LogoutButton;
