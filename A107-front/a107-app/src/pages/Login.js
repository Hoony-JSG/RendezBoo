import LoginForm from '../components/LoginForm'
import LoginSocial from '../components/LoginSocial'

const Login = () => {
    console.log("로그인 화면");
    return (
      <div>
        로그인
        <div>
          <LoginForm />
          <LoginSocial />
        </div>
      </div>
    );
  };


export default Login
