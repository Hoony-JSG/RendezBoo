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
  );
}

export default App;
