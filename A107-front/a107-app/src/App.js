import React from 'react'
import { Route, Routes } from 'react-router-dom'
import { Home, Rendezboo, Signal, Login, Rocket, Docking1 } from './pages'
import Navbar from './components/Navbar'
import './App.css'
import Userinfo from './pages/Userinfo'
import Inventory from './pages/Inventory'

function App() {
  return (
    <div className="App">
      <div>
        <Navbar />
        <Routes>
          <Route path="/home" element={<Home />} />
          <Route exact path="/" element={<Rendezboo />} />
          <Route exact path="/signal" element={<Signal />} />
          <Route path="/signal/:userid" element={<Signal />} />
          <Route path="/login" element={<Login />} />
          <Route path="/docking1" element={<Docking1 />} />
          <Route path="/rocket/:userid" element={<Rocket />} />
          <Route path="/Userinfo/:userid" element={<Userinfo />}></Route>;
          <Route path="/Inventory/:userid" element={<Inventory />}></Route>;
        </Routes>
      </div>
    </div>
  )
}

export default App
