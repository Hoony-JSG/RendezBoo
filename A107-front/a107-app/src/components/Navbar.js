import React from "react";
import { NavLink } from "react-router-dom";
import logo from "../logo.png";

const Navbar = () => {
  const navStyle = {
    position: "initial",
    top: 0,
    width: "100%",
    height: "90px",
    display: "flex",
    border: "solid 2px",
  };

  const divStyle = {
    width: "100%",
    height: "90px",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
  };

  const logoStyle = {
    height: "inherit",
  };

  return (
    <nav style={navStyle}>
      <div style={divStyle}>
        <NavLink to="/">Rendezboo</NavLink>
      </div>
      <div style={divStyle}>
        <NavLink to="/signal">Signal</NavLink>
      </div>
      <div style={divStyle}>
        <img src={logo} className="Main-logo" alt="logo" style={logoStyle} />
      </div>
      <div style={divStyle}>
        <NavLink to="/login">Login</NavLink>
      </div>
      <div style={divStyle}>
        <NavLink to="/rocket/:userid">MyRocket</NavLink>
      </div>
    </nav>
  );
};

export default Navbar;
