import React from "react";
import { NavLink } from "react-router-dom";
import logo from "../logo.png";

const Navbar = () => {

    const navStyle = {
        position: "absolute fixed",
        top: 0,
        width: "100%",
        height: "90px",
        display: "flex",
        // border: "solid 2px",
        background: "rgba(23, 49, 71, 0.5)",
        boxShadow: "0px 5px 5px rgba(0, 0, 0, 0.25)",
        backdropFilter: "blur(2px)",
        
    }

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
          <NavLink to="/" style={{color: "white", fontFamliy: "KIMM_bold"}}>
            Rendezboo
          </NavLink>
        </div>
        <div style={divStyle}>
          <NavLink to="/signal" style={{color: "white"}}>
            Signal
          </NavLink>
        </div>
        <div style={divStyle}>
            <img src={logo} className="Main-logo" alt="logo" style={logoStyle} />
        </div>
        <div style={divStyle}>
          <NavLink to="/login" style={{color: "white"}}>
            Login
          </NavLink>
        </div>
        <div style={divStyle}>
          <NavLink to="/rocket/:userid" style={{color: "white"}}>
            MyRocket
          </NavLink>
        </div>
      </nav>
    );
  };
  
  export default Navbar;
  
