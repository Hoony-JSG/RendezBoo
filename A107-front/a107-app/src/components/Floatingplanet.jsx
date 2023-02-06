import React from 'react'
import '../Styles/FloatingPlanet.css'
import { GiRingedPlanet } from 'react-icons/gi'
const FloatingPlanet = () => {
  return (
    <div class="solar-system">
      <div class="sun">
        <h2>ME</h2>
      </div>
      <div class="planet planet1">
        <div class="point">
          <GiRingedPlanet size="50" />
        </div>
      </div>
      <div class="planet planet2">
        <div class="point">
          <GiRingedPlanet size="100" />
        </div>
      </div>
      <div class="planet planet3">
        <div class="point">
          <GiRingedPlanet size="80" />
        </div>
      </div>
    </div>
  )
}

export default FloatingPlanet
