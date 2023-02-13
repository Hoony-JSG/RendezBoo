import { NavLink } from 'react-router-dom'
import FloatingPlanet from '../components/HomeComponents/Floatingplanet'
import SpaceShip from '../components/HomeComponents/SpaceShip'
import AllSocialLogins from '../components/JoinComponents/AllSocialLogins'
import Logo from '../logo_color.png'

const Home = (setGetToken) => {
  return (
    <div>
      <div style={{ height: '150px' }}></div>

      <img src={Logo} alt="Logo" style={{ height: '120px' }} />

      <div style={{ height: '150px' }}></div>
      <AllSocialLogins />

      {/* <div>
        <NavLink to="/loginnew">로그인</NavLink>
      </div>
      <div>
        <NavLink to="/joinsocial">조인</NavLink>
      </div>
      <div style={{ height: '100px' }}></div>
      <FloatingPlanet />
<<<<<<< Updated upstream
      <SpaceShip /> */}
=======
>>>>>>> Stashed changes
    </div>
  )
}

export default Home
