import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import RoomList from './pages/RoomList';
import ChatRoom from './pages/ChatRoom';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/rooms" element={<RoomList />} />
        <Route path="/rooms/:roomId" element={<ChatRoom />} />
      </Routes>
    </Router>
  );
}

export default App;
