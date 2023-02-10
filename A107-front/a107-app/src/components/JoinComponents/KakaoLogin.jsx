import kakaoLogo from '../../Images/kakao-logo.png'

const KakaoLogin = () => {
  const handleKakaoLogin = () => {
    // Kakao API initialization
    window.Kakao.init('a51740fcfd163300a5d33f0c4ed76473')

    // Kakao Login API call
    window.Kakao.Auth.login({
      redirectUri: 'http://localhost:3000/join',
      success: (authObj) => {
        console.log(authObj)
      },
      fail: (err) => {
        console.error(err)
      },
    })
  }
  return (
    <div>
      <button onClick={handleKakaoLogin}>
        <img src={kakaoLogo} alt="Kakao logo" />
      </button>
    </div>
  )
}

export default KakaoLogin
