import React from 'react'
import { Route, Routes, useLocation } from 'react-router-dom'

import {
  Home,
  Rendezboo,
  Signal,
  Login,
  Join,
  Rocket,
  Docking1,
  Docking3,
  Test,
} from './pages'
import Navbar from './components/Navbar'
import './App.css'
import Userinfo from './pages/Userinfo'
import Inventory from './pages/Inventory'
import WebSocketChatTest from './pages/WebSocketChatTest'

function App() {
  const location = useLocation()

  return (
    <div className="App">
      <div>
        {!(
          location.pathname === '/home' ||
          location.pathname === '/join' ||
          location.pathname === '/login' ||
          location.pathname === '/Login'
        ) && <Navbar />}
        <Routes>
          <Route path="/home" element={<Home />} />
          <Route exact path="/" element={<Rendezboo />} />
          <Route exact path="/signal" element={<Signal />} />
          <Route path="/signal/:userid" element={<Signal />} />
          <Route path="/login" element={<Login />} />
          <Route path="/join" element={<Join />} />
          <Route path="/docking1" element={<Docking1 />} />
          <Route path="/docking3" element={<Docking3 />} />
          <Route path="/docking3/:roomid" element={<Docking3 />} />
          <Route path="/rocket/:userid" element={<Rocket />} />
          <Route path="/userinfo/:userid" element={<Userinfo />}></Route>;
          <Route path="/inventory/:userid" element={<Inventory />}></Route>;
          <Route path="/test" element={<Test />}></Route>;
          {/* 웹소켓 테스트용 라우터 */}
          <Route
            path="/websocketchattest"
            element={<WebSocketChatTest />}
          ></Route>
          ;
        </Routes>
      </div>
    </div>
  )
}

export default App
