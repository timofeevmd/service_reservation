import { Link } from "react-router-dom";

const Navbar = () => {
    return (
        <nav>
            <Link to="/">reservation-Service</Link> | <Link to="/login">sign-in</Link> | <Link to="/register">register</Link>
        </nav>
    );
};

export default Navbar;
