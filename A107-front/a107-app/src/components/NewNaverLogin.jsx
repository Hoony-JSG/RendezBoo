import { useEffect } from 'react'

const { naver } = window

function NewNaverLogin(props) {
  const initializeNaverLogin = () => {
    const naverLogin = new naver.LoginWithNaverId({
      clientId: 'ZvY7wDn48i4hvuK23vQM',
      callbackUrl: 'http://localhost:3000/home',
      isPopup: false, // popup 형식으로 띄울것인지 설정
      loginButton: { color: 'white', type: 1, height: '47' }, //버튼의 스타일, 타입, 크기를 지정
      callbackHandle: true,
    })
    naverLogin.init()

    naverLogin.getLoginStatus(async function (status) {
      console.log('status : ' + status)
    })
  }

  useEffect(() => {
    initializeNaverLogin()
  }, [])

  return (
    <div>
      <div id="naverIdLogin" /> {/* id 꼭 입력해주어야 함 */}
    </div>
  )
}

export default NewNaverLogin
