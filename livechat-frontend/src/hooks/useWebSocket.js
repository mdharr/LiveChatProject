import { useState, useEffect, useRef, useCallback } from 'react';

const useWebSocket = (url) => {
  const [wsMessages, setWsMessages] = useState([]);
  const socketRef = useRef(null);

  useEffect(() => {
    socketRef.current = new WebSocket(url);

    socketRef.current.onopen = () => {
      console.log('WebSocket connected');
    };

    socketRef.current.onmessage = (event) => {
      try {
        const message = JSON.parse(event.data);
        setWsMessages((prev) => [...prev, message]);
      } catch (error) {
        console.error('Error parsing WebSocket message:', error);
      }
    };

    socketRef.current.onerror = (error) => {
      console.error('WebSocket error:', error);
    };

    socketRef.current.onclose = () => {
      console.log('WebSocket disconnected');
    };

    return () => {
      if (socketRef.current) {
        socketRef.current.close();
      }
    };
  }, [url]);

  const sendMessage = useCallback((message) => {
    if (socketRef.current && socketRef.current.readyState === WebSocket.OPEN) {
      socketRef.current.send(JSON.stringify(message));
    } else {
      console.error('WebSocket is not open. Ready state:', socketRef.current.readyState);
    }
  }, []);

  return { wsMessages, sendMessage };
};

export default useWebSocket;