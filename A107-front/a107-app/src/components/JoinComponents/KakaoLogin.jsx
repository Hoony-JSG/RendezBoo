import kakaoLogo from '../../Images/kakao_login_large_narrow.png'
import '../../Styles/SocialLogin.css'

const KakaoLogin = () => {
  const KAKAO_SOCIAL_LOGIN_URL =
    'https://kauth.kakao.com/oauth/authorize?client_id=712c281c808f36c63bc8cea4a11ba42f&redirect_uri=https%3a%2f%2fi8a107.p.ssafy.io%2foauth%2fkakao&response_type=code'

  // const handleKakaoLogin = () => {
  //   // Kakao API initialization
  //   window.Kakao.init('a51740fcfd163300a5d33f0c4ed76473')

  //   // Kakao Login API call
  //   window.Kakao.Auth.login({
  //     redirectUri: 'https://i8a107.p.ssafy.io/oauth/kakao',
  //     success: (authObj) => {
  //       console.log('카카오 성공 : ')
  //       console.log(authObj)
  //     },
  //     fail: (err) => {
  //       console.log('카카오 실패 : ')
  //       console.error(err)
  //     },
  //   })
  // }
  return (
    <button className={'kakao-btn'} href={KAKAO_SOCIAL_LOGIN_URL}>
      카카오 로그인
    </button>
    // <div>
    //   <a href={KAKAO_SOCIAL_LOGIN_URL}>
    //     <img
    //       src={kakaoLogo}
    //       alt="Kakao logo"
    //       style={{ width: '300px', height: '75px' }}
    //     />
    //   </a>
    // </div>
  )
}

export default KakaoLogin
