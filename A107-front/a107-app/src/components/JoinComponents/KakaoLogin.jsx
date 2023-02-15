import kakaoLogo from '../../Images/kakao_login_large_narrow.png'

const KakaoLogin = () => {
  const KAKAO_SOCIAL_LOGIN_URL =
    'https://kauth.kakao.com/oauth/authorize?client_id=712c281c808f36c63bc8cea4a11ba42f&redirect_uri=https%3a%2f%2fi8a107.p.ssafy.io%2foauth%2fkakao&response_type=code'

  return (
      <a href={KAKAO_SOCIAL_LOGIN_URL}>
        <img
          src={kakaoLogo}
          alt="Kakao logo"
          style={{ width: '300px', height: '75px' }}
        />
      </a>
  )
}

export default KakaoLogin
