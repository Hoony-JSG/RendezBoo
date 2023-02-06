import React from 'react'
import { Route, Routes } from 'react-router-dom'
import {
  Home,
  Rendezboo,
  Signal,
  Login,
  Join,
  Rocket,
  Docking1,
  Userinfo,
  Inventory,
  Test,
} from './pages'
import Navbar from './components/Navbar'
import './App.css'


function App() {

  return (
    <div className="App">
      <div>
        <Navbar />
        <Routes className="AppContainer">
          <Route path="/home" element={<Home />} />
          <Route exact path="/" element={<Rendezboo />} />
          <Route exact path="/signal" element={<Signal />} />
          <Route path="/signal/:userid" element={<Signal />} />
          <Route path="/login" element={<Login />} />
          <Route path="/join" element={<Join />} />
          <Route path="/docking1" element={<Docking1 />} />
          <Route path="/rocket/:userid" element={<Rocket />} />
          <Route path="/userinfo/:userid" element={<Userinfo />}></Route>;
          <Route path="/inventory/:userid" element={<Inventory />}></Route>;
          <Route path="/test" element={<Test />}></Route>;
        </Routes>
      </div>
    </div>
  )
}

export default App
