import { NavLink } from 'react-router-dom'
import FloatingPlanet from '../components/HomeComponents/Floatingplanet'
import SpaceShip from '../components/HomeComponents/SpaceShip'

const Home = (setGetToken) => {
  return (
    <div>
      <div style={{ height: '100px' }}></div>
      <div>
        {/* <NavLink to="/login">로그인</NavLink> */}
        <NavLink to="/loginnew">로그인</NavLink>
      </div>
      <div>
        <NavLink to="/joinsocial">조인</NavLink>
      </div>
      <div style={{ height: '100px' }}></div>
      {/* <FloatingPlanet /> */}
      <SpaceShip />
    </div>
  )
}

export default Home
