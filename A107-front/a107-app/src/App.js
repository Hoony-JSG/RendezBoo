// import logo from "./logo.svg";
import React from "react";
import { Route, Routes } from "react-router-dom";
import { Rendezboo, Signal, Login, Rocket } from './pages'
// import Rendezboo from './pages/Rendezboo';
// import Signal from './pages/Signal';
// import Login from './pages/Login';
// import Rocket from './pages/Rocket';
import Navbar from './components/Navbar';
import "./App.css";

function App() {
  return (
    <div className="App">
      home(rendezboo)
        <Navbar />
      <Routes>
          <Route exact path="/" component={ <Rendezboo/> } />
          <Route path="/signal" component={ Signal } />
          <Route path="/login" component={ Login } />
          <Route path="/rocket/:userid" component={ Rocket } />
      </Routes>
        {/* <Rendezboo /> */}
    </div>
  );
}

export default App;

