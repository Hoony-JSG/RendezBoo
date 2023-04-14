import React, { useState } from 'react'
import { NavLink, useNavigate } from 'react-router-dom'
import logoColor from '../logo_color.png'
import logoWhite from '../logo_white.png'
import { useSelector } from 'react-redux'

import '../Styles/NavbarStyle.css'

const Navbar = () => {
  const navigate = useNavigate()
  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)
  const [logo, setLogo] = useState(logoColor)

  const navStyle = {
    position: 'absolute fixed',
    top: 0,
    width: '100%',
    height: '90px',
    display: 'flex',
    background: 'rgba(23, 49, 71, 0.5)',
    boxShadow: '0px 5px 5px rgba(0, 0, 0, 0.25)',
    backdropFilter: 'blur(2px)',
    fontFamily: 'KIMM_light',
    textShadow:
      '0px 0px 2px rgba(255, 255, 255, 0.25), 0px 0px 5px rgba(0, 0, 0, 0.25)',
  }

  const divStyle = {
    width: '100%',
    height: '90px',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  }

  const logoStyle = {
    height: '60%',
    transition: 'all easy-in-out 0.5s',
  }

  return (
    <nav style={navStyle}>
      <div style={divStyle}>
        <NavLink to="/rendezboo" className="navMenuStyle">
          Rendezboo
        </NavLink>
      </div>
      <div style={divStyle}>
        <NavLink to="/signal" className="navMenuStyle">
          Signal
        </NavLink>
      </div>
      <div style={divStyle}>
        <img
          src={logo}
          className="Main-logo"
          alt="logo"
          style={logoStyle}
          onClick={() => navigate('/rendezboo')}
          onMouseOverCapture={() => setLogo(logoWhite)}
          onMouseOutCapture={() => setLogo(logoColor)}
        />
      </div>
      <div style={divStyle}>
        <NavLink to={'/rocket/' + userSeq} className="navMenuStyle">
          MyRocket
        </NavLink>
      </div>
      <div style={divStyle}>
        <NavLink to="/logout" className="navMenuStyle">
          Logout
        </NavLink>
      </div>
    </nav>
  )
}

export default Navbar
