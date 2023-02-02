import React from "react";
import { Route, Routes } from "react-router-dom";
import { Rendezboo, Signal, Login, Rocket } from "./pages";
import Navbar from "./components/Navbar";
import "./App.css";
import Userinfo from "./pages/Userinfo";
import Inventory from "./pages/Inventory";

function App() {
  return (
    <div className="App">
      <div>
        <Navbar />
        <Routes>
          <Route exact path="/" element={<Rendezboo />} />
          <Route path="/signal" element={<Signal />} />
          <Route path="/login" element={<Login />} />
          <Route path="/rocket/:userid" element={<Rocket />} />
          <Route path="/Userinfo/:userid" element={<Userinfo />}></Route>;
          <Route path="/Inventory/:userid" element={<Inventory />}></Route>;
        </Routes>
      </div>
      {/*  <div style={{ height: "100px" }}>
        <Navbar />
      </div>
      <Routes>
        <Route exact path="/" element={<Home />} />
        <Route path="/signal" element={<Signal />} />
        <Route path="/login" element={<Login />} />
        <Route path="/rocket/:userid" element={<Rocket />} />
        <Route path="/Login" element={<Login />} />
        <Route path="/Join" element={<Join />} />
      </Routes> */}
    </div>
  );
}

export default App;
