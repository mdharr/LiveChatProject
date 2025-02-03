import { Link, useNavigate } from 'react-router-dom';
import PropTypes from 'prop-types';
import './Navbar.css';

const Navbar = ({ isLoggedIn, setIsLoggedIn }) => {
  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem('jwtToken');
    setIsLoggedIn(false);
    navigate('/');
  };

  return (
    <nav className="navbar">
      <div className="logo navbar-left">
        <Link to="/" className="navbar-home-button">Home</Link>
      </div>
      <div className="auth-links navbar-right">
        {!isLoggedIn ? (
          <>
            <Link className="navbar-login-button" to="/login">Login</Link>
            <Link className="navbar-signup-button" to="/register">Sign Up</Link>
          </>
        ) : (
          <>
            <span className="navbar-username">Welcome, {localStorage.getItem('username')}</span>
            <button className="navbar-signout-button" onClick={handleLogout}>Logout</button>
          </>
        )}
      </div>
    </nav>
  );
};

Navbar.propTypes = {
  setIsLoggedIn: PropTypes.func.isRequired,
  isLoggedIn: PropTypes.bool.isRequired,
};

export default Navbar;