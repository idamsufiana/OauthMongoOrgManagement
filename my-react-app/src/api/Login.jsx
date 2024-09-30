import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import GoogleLogin from '../components/GoogleLogin';
import Nav from '../components/Nav';
import { postLoginToken } from '../postLoginToken';

export default function Login({ isLogin, setIsLogin }) {
  const navigate = useNavigate();

  const onGoogleSignIn = async res => {
    const { credential } = res;
    const result = await postLoginToken(credential, setIsLogin);
    setIsLogin(result);
  };

  useEffect(() => {
    if (!isLogin) return;
    navigate('/mypage');
  }, [isLogin]);

  return (
    <div>
      <h1>Google Login</h1>
      <Nav />
      <GoogleLogin onGoogleSignIn={onGoogleSignIn} text="Login" />
    </div>
  );
}