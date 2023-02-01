import React from "react";
import { Route, Routes } from "react-router-dom";
import { Rendezboo, Signal, Login, Rocket } from './pages'
import Navbar from './components/Navbar';
import "./App.css";

function App() {
  return (
    <div className="App">
        <Navbar />
      <Routes>
          <Route exact path="/" element={ <Rendezboo/> } />
          <Route exact path="/signal" element={ <Signal/> } />
          <Route path="/signal/:userid" element={ <Signal/> } />
          <Route path="/login" element={ <Login/> } />
          <Route path="/rocket/:userid" element={ <Rocket /> } />
      </Routes>
    </div>
  );
}

export default App;

