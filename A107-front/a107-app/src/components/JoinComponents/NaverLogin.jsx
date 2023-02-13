import { useEffect, useState } from 'react'

const NaverLogin = (props) => {
  const { naver } = window
  const NAVER_CLIENT_ID = 'F3K8r9yyEG_RFk8RpLgi' // 발급 받은 Client ID 입력
  const NAVER_CALLBACK_URL = 'https://i8a107.p.ssafy.io/api/oauth/naver'

  // const initializeNaverLogin = () => {
  //   console.log('여기로 안오나')
  //   const naverLogin = new naver.LoginWithNaverId({
  //     clientId: NAVER_CLIENT_ID,
  //     callbackUrl: NAVER_CALLBACK_URL,
  //     isPopup: false,
  //     loginButton: { color: 'green', type: 3, height: 75 },
  //     callbackHandle: true,
  //   })

  //   naverLogin.init()

  //   naverLogin.getLoginStatus(async function (status) {
  //     console.log('satus : ' + status)
  //     console.log(naverLogin.user)
  //     console.log(naverLogin)
  //     if (status) {
  //       // 아래처럼 선택하여 추출이 가능하고,
  //       const userid = naverLogin.user.getEmail()
  //       // 정보 전체를 아래처럼 state 에 저장하여 추출하여 사용가능하다.
  //       // setUserInfo(naverLogin.user)
  //       console.log('id : ' + userid)
  //     } else {
  //       console.log('client')
  //       console.log('status 없음')
  //     }
  //   })
  //   // 요기!
  // }

  useEffect(() => {
    // initializeNaverLogin()
  }, [])
  return (
    <>
      {/* // 구현할 위치에 아래와 같이 코드를 입력해주어야 한다. 
         // 태그에 id="naverIdLogin" 를 해주지 않으면 오류가 발생한다! */}
      {/* <div id="naverIdLogin"> </div> */}
      <a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=F3K8r9yyEG_RFk8RpLgi&redirect_uri=http%3A%2F%2Flocalhost%3A3000%2foauth%2fnaver&state=1234">
        Naver Login
      </a>
    </>
  )
}

export default NaverLogin
