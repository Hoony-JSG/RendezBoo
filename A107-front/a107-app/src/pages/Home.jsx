// import FloatingPlanet from '../components/HomeComponents/Floatingplanet'
// import SpaceShip from '../components/HomeComponents/SpaceShip'
import Logo from '../logo_color.png'
import KakaoLogin from '../components/JoinComponents/KakaoLogin'
import NaverLogin from '../components/JoinComponents/NaverLogin'
import GameBr31 from '../components/DockingComponents/GameComponents/GameBR31'

const Home = () => {
  const homeDivStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    margin: '120px',
    gap: '90px',
  }

  const sclLgnStyle = {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    gap: '30px',
  }

  return (
    <div style={homeDivStyle}>
      <img src={Logo} alt="Logo" style={{ height: '240px' }} />
      <div style={sclLgnStyle}>
        <NaverLogin />
        <KakaoLogin />
      </div>
      {/* 
      <FloatingPlanet />
      <SpaceShip /> */}
    </div>
  )
}

export default Home
