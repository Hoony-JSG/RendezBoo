import React, { useEffect } from 'react'
import { Route, Routes, useLocation, useNavigate } from 'react-router-dom'

import {
  Home,
  Rendezboo,
  Signal,
  Login,
  LoginNew,
  Join,
  Rocket,
  Docking1,
  Userinfo,
  Inventory,
  Docking3,
  Error,
  Docking3ing,
} from './pages'
import Navbar from './components/Navbar'
import './App.css'
import WebSocketChatTest from './modules/WebSocketChatTest'
import JoinSocial from './pages/JoinSocial'
import LoginTest from './pages/LoginNew'
import Logout from './pages/Logout'
import {
  getRefreshTokenFromCookie,
  reissueAccessToken,
} from './modules/Auth/Jwt'
import { useDispatch } from 'react-redux'
import { SET_TOKEN } from './containers/JwtContainer'
import NaverOauth from './components/JoinComponents/NaerOauth'
import OauthKakao from './pages/OauthKakao'
import OauthNaver from './pages/OauthNaver'

function App() {
  const location = useLocation()
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const refreshToken = getRefreshTokenFromCookie()
  const allowedPaths = [
    '/loginnew',
    '/home',
    '/join',
    '/joinsocial',
    '/oauth/naver',
    '/oauth/kakao',
  ]
  const isAllowedPath = allowedPaths.includes(location.pathname)

  const reissueAndSetToken = async () => {
    // 토큰이 없고 접근 제한된 라우트에 접근하려고 할 때 홈으로 보내기
    if (!isAllowedPath && !refreshToken) {
      alert('로그인이 필요한 서비스입니다.')
      navigate('/home')
      //  그 외에는 토큰 재발급
    } else {
      const accessToken = await reissueAccessToken(refreshToken)
      dispatch(SET_TOKEN(accessToken))
    }
  }

  useEffect(reissueAndSetToken(), [location.pathname, refreshToken])

  return (
    <div className="App">
      <div>
        {!(
          location.pathname === '/home' ||
          location.pathname === '/join' ||
          location.pathname === '/login' ||
          location.pathname === '/joinsocial' ||
          location.pathname === '/docking1' ||
          location.pathname === '/docking3ing'
        ) && <Navbar />}
        <Routes>
          <Route path="/home" element={<Home />} />
          <Route exact path="/" element={<Rendezboo />} />
          <Route exact path="/signal" element={<Signal />} />
          <Route path="/signal/:tmpChatRoomSeq" element={<Signal />} />
          <Route path="/login" element={<Login />} />
          <Route path="/loginnew" element={<LoginNew />}></Route>;
          <Route path="/joinsocial" element={<JoinSocial />} />
          <Route path="/join" element={<Join />} />
          <Route path="/docking1" element={<Docking1 />} />
          <Route path="/docking3" element={<Docking3 />} />
          <Route path="/docking3ing" element={<Docking3ing />} />
          <Route path="/docking3/:roomid" element={<Docking3 />} />
          <Route path="/rocket/:userid" element={<Rocket />} />
          <Route path="/userinfo/:userid" element={<Userinfo />}></Route>;
          <Route path="/inventory/:userid" element={<Inventory />}></Route>;
          {/* 웹소켓 테스트용 라우터 */}
          <Route
            path="/websocketchattest"
            element={<WebSocketChatTest />}
          ></Route>
          {/* 로그인 테스트용 라우터 */}
          <Route path="/logintest" element={<LoginTest />}></Route>
          <Route path="/logout" element={<Logout />}></Route>
          <Route path="/oauth/naver" element={<OauthNaver />}></Route>
          <Route path="/oauth/kakao" element={<OauthKakao />}></Route>
          <Route path="/*" element={<Error />}></Route>
        </Routes>
      </div>
    </div>
  )
}

export default App
