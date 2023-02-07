import KakaoLogin from './KakaoLogin'
import NaverLogin from './NaverLogin'
import '../Styles/SocialLoginButton.css'
const AllSocialLogins = (props) => {
  return (
    <div>
      <div className="SocialLoginButtonNaver">
        <NaverLogin />
      </div>
      <div className="SocialLoginButtonKakao">
        <KakaoLogin />
      </div>
    </div>
  )
}

export default AllSocialLogins
