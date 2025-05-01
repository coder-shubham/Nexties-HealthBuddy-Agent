import React, { useState } from 'react';
import './styles/global.css';
import ChatScreen from './components/chat/ChatScreen';
import Navbar from './components/generic/NavBar';

const App = () => {
  const [callActive, setCallActive] = useState(false);

  return (
    <div className="app-container">
      <Navbar />

      <main className="app-main">
      <ChatScreen />
      </main>
    </div>
  );
};

export default App;
