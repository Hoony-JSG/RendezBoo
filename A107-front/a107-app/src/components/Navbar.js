import React from "react";
import { NavLink } from "react-router-dom";

const Navbar = () => {

    const navStyle = {
        position: "fixed",
        top: 0,
        width: "100%",
        height: "60px",
        display: "flex",
        border: "solid 2px",
    }

    const divStyle = {
        width: "100%",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }

    return (
      <nav style={navStyle}>
        <div style={divStyle}>
          <NavLink to="/">
            Rendezboo
          </NavLink>
        </div>
        <div style={divStyle}>
          <NavLink to="/signal">
            Signal
          </NavLink>
        </div>
        <div style={divStyle}>
          <NavLink to="/login">
            Login
          </NavLink>
        </div>
        <div style={divStyle}>
          <NavLink to="/rocket/:userid">
            MyRocket
          </NavLink>
        </div>
      </nav>
    );
  };
  
  export default Navbar;
  
