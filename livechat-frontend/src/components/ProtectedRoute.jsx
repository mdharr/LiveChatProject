import { Navigate } from 'react-router-dom';
import PropTypes from 'prop-types';

const ProtectedRoute = ({ element, isLoggedIn, redirectPath = '/login' }) => {
  if (!isLoggedIn) {
    return <Navigate to={redirectPath} />;
  }

  return element;
};

ProtectedRoute.propTypes = {
    element: PropTypes.node.isRequired,
    isLoggedIn: PropTypes.bool.isRequired,
    redirectPath: PropTypes.string,
  };

export default ProtectedRoute;