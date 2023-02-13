import kakaoLogo from '../../Images/kakao_login_large_narrow.png'

const KakaoLogin = () => {
  const handleKakaoLogin = () => {
    // Kakao API initialization
    window.Kakao.init('a51740fcfd163300a5d33f0c4ed76473')

    // Kakao Login API call
    window.Kakao.Auth.login({
      redirectUri: 'https://i8a107.p.ssafy.io/join',
      success: (authObj) => {
        console.log('카카오 성공 : ')
        console.log(authObj)
      },
      fail: (err) => {
        console.log('카카오 실패 : ')
        console.error(err)
      },
    })
  }
  return (
    <div>
      <button
        style={{ backgroundColor: 'rgba(0,0,0,0)', border: '0px' }}
        onClick={handleKakaoLogin}
      >
        <img
          src={kakaoLogo}
          alt="Kakao logo"
          style={{ width: '346.8px', height: '75px' }}
        />
      </button>
    </div>
  )
}

export default KakaoLogin
