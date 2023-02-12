import { NavLink } from 'react-router-dom'
import FloatingPlanet from '../components/HomeComponents/Floatingplanet'

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
      <FloatingPlanet />
    </div>
  )
}

export default Home
