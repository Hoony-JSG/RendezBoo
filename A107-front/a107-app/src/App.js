import React from "react";
import { Route, Routes } from "react-router-dom";
import { Rendezboo, Signal, Login, Rocket, Join } from "./pages";
import Navbar from "./components/Navbar";
import "./App.css";
import Home from "./pages/Home";

function App() {
  return (
    <div className="App">
      <div style={{ height: "100px" }}>
        <Navbar />
      </div>
      <Routes>
        {/* <Route exact path="/" element={ <Rendezboo/> } /> */}
        <Route exact path="/" element={<Home />} />
        <Route path="/signal" element={<Signal />} />
        <Route path="/login" element={<Login />} />
        <Route path="/rocket/:userid" element={<Rocket />} />
        <Route path="/Login" element={<Login />} />
        <Route path="/Join" element={<Join />} />
      </Routes>
    </div>
  );
}

export default App;
