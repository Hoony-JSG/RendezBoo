import LoginForm from '../components/JoinComponents/LoginForm'

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
