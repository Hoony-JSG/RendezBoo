import KakaoLogin from './KakaoLogin'
import NaverLogin from './NaverLogin'
const AllSocialLogins = (props) => {
  return (
    <div>
      <div>
        <NaverLogin />
      </div>
      <div>
        <KakaoLogin />
      </div>
    </div>
  )
}

export default AllSocialLogins
