import { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import useWebSocket from '../hooks/useWebSocket';
import { useNavigate } from 'react-router-dom';

const ChatRoom = () => {
  const { roomId } = useParams();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState('');
  const navigate = useNavigate();

  const token = localStorage.getItem('jwtToken');
  const { wsMessages, sendMessage } = useWebSocket(`ws://localhost:8096/api/v1/chat?roomId=${roomId}&token=${token}`);

  useEffect(() => {
    const fetchMessages = async () => {
      try {
        const response = await axios.get(`http://localhost:8096/api/v1/messages/room/${roomId}`, {
          headers: { Authorization: `Bearer ${token}` }
        });
        setMessages(response.data);
      } catch (error) {
        console.error('Error fetching messages:', error);
      }
    };
    fetchMessages();
  }, [roomId, token]);

  useEffect(() => {
    if (wsMessages.length > 0) {
      setMessages((prevMessages) => {
        const newMessages = wsMessages.filter(msg => !prevMessages.some(prevMsg => prevMsg.id === msg.id));
        return [...prevMessages, ...newMessages];
      });
    }
  }, [wsMessages]);  

  const handleSendMessage = (e) => {
    e.preventDefault();
    const messageData = {
      content: newMessage,
      roomId: roomId,
    };
    sendMessage(messageData);
    console.log("Sending message payload:", messageData);
    setNewMessage('');
  };

  const navigateToRoomList = () => navigate('/rooms');

  return (
    <div>
      <h3>Chat Room {roomId}</h3>
      <div className="messages">
        {messages && Array.isArray(messages) && messages.map((msg, index) => (
            <div key={`${msg.id}-${index}`}>
                <strong>{msg.senderUsername || msg.sender?.username}:</strong> {msg.content}
            </div>
        ))}
      </div>
      <form onSubmit={handleSendMessage}>
        <input
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="Type your message..."
          required
        />
        <button type="submit">Send</button>
      </form>
      <button type="button" onClick={() => navigateToRoomList()}>Back</button>
    </div>
  );
};

export default ChatRoom;