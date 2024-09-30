import { Routes, Route } from 'react-router-dom';
import Login from './api/Login.jsx';
import { useEffect, useState } from 'react';
import { getUserInfo } from './api/getUserInfo.js';

export default function App() {
  const [isLogin, setIsLogin] = useState(false);

  useEffect(() => {
    const initLogin = async () => {
      const name = await getUserInfo();
      setIsLogin(!!name);
    };
    initLogin();
  }, []);

  return (
    <div className="App">
      <Routes>
        <Route
          path="/"
          element={<Login isLogin={isLogin} setIsLogin={setIsLogin} />}
        />
        <Route path="/register" element={<Register />} />
        <Route path="*" element={<h2>404 - Page Not Found</h2>} />
      </Routes>
    </div>
  );
}
