import { useNavigate } from "react-router-dom";
import "../styles/globals.css";

const HomePage = () => {
  const navigate = useNavigate();

  return (
    <main className="page-container">
      <div className="content-container">
        <header className="mb-6">
          <h1 className="text-4xl font-bold mb-2" style={{ color: "var(--keyword)" }}>Welcome to Service Reservation</h1>
          <p className="text-lg max-w-2xl mx-auto">
            This service is designed for managing car and hotel room reservations.
            It is based on a modern microservice architecture utilizing REST API, PostgreSQL database,
            as well as monitoring and logging tools.
          </p>
        </header>
        <section className="mb-6">
          <h2 className="text-2xl font-semibold mb-2" style={{ color: "var(--type)" }}>Project Basic Architecture:</h2>
          <ul className="list-disc text-lg text-left mx-auto max-w-xl">
            <li>Microservice Architecture</li>
            <li>REST API</li>
            <li>PostgreSQL</li>
            <li>Monitoring and Logging</li>
          </ul>
        </section>
        <section className="button-container">
          <button onClick={() => navigate('/register')} className="button button-primary">
            Register
          </button>
          <button onClick={() => navigate('/login')} className="button button-secondary">
            Login
          </button>
        </section>
      </div>
    </main>
  );
};

export default HomePage;