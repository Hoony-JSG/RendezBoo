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

function App() {
  const location = useLocation()
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const refreshToken = getRefreshTokenFromCookie()

  const allowedPaths = ['/loginnew', '/home', '/join', '/joinsocial']
  const isAllowedPath = allowedPaths.includes(location.pathname)
  
  
  useEffect(() => {
    (async () => {
      if (!refreshToken && !isAllowedPath) {
        navigate('/home')
        // alert('로그인이 필요합니다')
        console.log('토큰 업슴')
      } else {
        const accessToken = await reissueAccessToken(refreshToken)
        dispatch(SET_TOKEN(accessToken))
      }
    })()
  }, [location.pathname, refreshToken])

  // useEffect(async () => {
  //   console.log('refresh')
  //   const refreshToken = await getRefreshTokenFromCookie()
  //   if (refreshToken) {
  //     console.log('토큰이 있습니다')
  //     const accessToken =  await reissueAccessToken(refreshToken)
  //     dispatch(SET_TOKEN(accessToken))
  //   } else {
  //     alert('로그인이 필요합니다')
  //     console.log('토큰이 없습니다')
  //     navigate('/logintest')
  //   }
  // }, [])

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
          <Route path="/*" element={<Error />}></Route>
          {/* 웹소켓 테스트용 라우터 */}
          <Route
            path="/websocketchattest"
            element={<WebSocketChatTest />}
          ></Route>
          {/* 로그인 테스트용 라우터 */}
          <Route path="/logintest" element={<LoginTest />}></Route>
          <Route path="/logout" element={<Logout />}></Route>
        </Routes>
      </div>
    </div>
  )
}

export default App
