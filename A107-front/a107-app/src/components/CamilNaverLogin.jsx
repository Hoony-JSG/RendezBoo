import React, { useEffect, useState } from 'react'
import '../Styles/NaverLoginStyle.css'
const NaverLogin = () => {
  const [loginStatus, setLoginStatus] = useState(null)
  useEffect(() => {
    window.naverLogin = new window.naver.LoginWithNaverId({
      clientId: 'ZvY7wDn48i4hvuK23vQM',
      callbackUrl: 'http://localhost:3000/home',
      isPopup: false,
      loginButton: { color: 'green', type: 1, height: 40 },
    })
    console.log('status : ' + loginStatus)
    console.log('window : ' + window.naverLogin)
    window.naverLogin.init()

    window.naverLogin.getLoginStatus(function (status) {
      console.log(status)
      if (status) {
        const name = window.naverLogin.user.getName()
        const age = window.naverLogin.user.getAge()
        const birthday = window.naverLogin.user.getBirthday()
        const email = window.naverLogin.user.getEmail()
        const gender = window.naverLogin.user.getGender()

        if (!name || !age || !birthday || !email || !gender) {
          alert('필수 정보 제공을 동의해주세요.')
          window.naverLogin.reprompt()
          return
        }
      } else {
        console.log('네이버 비로그인 상태')
      }
    })
  }, [])

  const handleLogout = () => {
    window.naverLogin.logout()
    setLoginStatus(null)
  }

  return (
    <div className="container">
      <h1>Naver Login API 사용하기</h1>
      <div className="login-area">
        {loginStatus !== null ? (
          <div>
            <h3> Login 성공 </h3>
            <div>user Nickname : {loginStatus.nickname}</div>
            <div>user Age(범위) : {loginStatus.age}</div>
            <div>user Birthday : {loginStatus.birthday}</div>
            <button id="btn_logout" onClick={handleLogout}>
              로그아웃
            </button>
          </div>
        ) : (
          <div id="message">
            로그인 버튼을 눌러 로그인 해주세요.
            <div id="button_area"></div>
            <div id="naverIdLogin"></div>
          </div>
        )}
      </div>
    </div>
  )
}

export default NaverLogin
