import KakaoLogin from './KakaoLogin'
import NaverLogin from './NaverLogin'
const AllSocialLogins = (props) => {
  return (
    <div>
      <div style={{ border: '1px solid red' }}>
        <NaverLogin />
      </div>
      <div>
        <KakaoLogin />
      </div>
    </div>
  )
}

export default AllSocialLogins
