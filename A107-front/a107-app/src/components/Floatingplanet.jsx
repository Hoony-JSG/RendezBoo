import React from 'react'
import '../Styles/FloatingPlanet.css'
import { GiRingedPlanet } from 'react-icons/gi'
const FloatingPlanet = () => {
  return (
    <div className="solar-system">
      <div className="sun">
        <h2>ME</h2>
      </div>
      <div className="planet planet1">
        <div className="point">
          <GiRingedPlanet size="50" />
        </div>
      </div>
      <div className="planet planet2">
        <div className="point">
          <GiRingedPlanet size="100" />
        </div>
      </div>
      <div className="planet planet3">
        <div className="point">
          <GiRingedPlanet size="80" />
        </div>
      </div>
    </div>
  )
}

export default FloatingPlanet
