import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div className="landing-page">
      <div className="container">
        <header className="header">
          <h1>Welcome to LiveChat</h1>
          <p>The real-time chat application built for seamless communication!</p>
        </header>

        <div className="features">
          <h2>Why Choose LiveChat?</h2>
          <ul>
            <li>Real-time messaging using WebSockets</li>
            <li>Easy room creation and management</li>
            <li>Secure login and JWT authentication</li>
            <li>Multiple chat rooms and message history</li>
          </ul>
        </div>

        <div className="cta-buttons">
          <h3>Get Started</h3>
          <div className="button-group">
            <Link to="/login" className="btn btn-primary">Login</Link>
            <Link to="/register" className="btn btn-secondary">Sign Up</Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
