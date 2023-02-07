import AllSocialLogins from '../components/AllSocialLogins'
import LoginForm from '../components/LoginForm'
import LoginSocial from '../components/LoginSocial'

const Login = (props) => {
  console.log('로그인 화면')
  return (
    <div>
      로그인
      <div>
        <LoginForm />
        {/* <AllSocialLogins ver="login" /> */}
      </div>
    </div>
  )
}

export default Login
