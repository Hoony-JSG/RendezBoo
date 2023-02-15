import kakaoLogo from '../../Images/kakaologin.png'

const KakaoLogin = () => {
  const KAKAO_SOCIAL_LOGIN_URL =
    'https://kauth.kakao.com/oauth/authorize?client_id=712c281c808f36c63bc8cea4a11ba42f&redirect_uri=https%3a%2f%2fi8a107.p.ssafy.io%2foauth%2fkakao&response_type=code'

  return (
      <a href={KAKAO_SOCIAL_LOGIN_URL}>
        <img
          src={kakaoLogo}
          alt="Login with Kakao"
          style={{ filter: 'drop-shadow(0px 4px 4px rgba(0, 0, 0, 0.25)) drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25))' }}
        />
      </a>
  )
}

export default KakaoLogin
