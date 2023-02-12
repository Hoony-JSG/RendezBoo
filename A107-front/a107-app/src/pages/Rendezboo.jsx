import { BsFillDoorClosedFill, BsFillDoorOpenFill } from 'react-icons/bs'
import { useNavigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import RocketItem from '../components/RocketComponents/RocketItem'
import '../Styles/Rendezboo.css'

const Rendezboo = () => {

  const userid = useSelector((state) => state.userInfoReducer.userEmail)

  console.log('랑데부 페이지')
  const id = 'wjdgnsxhsl'
  const navigate = useNavigate()
  const goToPage = (props) => {
    navigate('/' + props)
  }
  const Contents = {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '0px',
    margin: '30px',
    height: 'auto',
    gap: '50px'
  }
  const MeAndYou = {
    Me: userid,
    Inquire: userid,
    Rendez: true,
    BadgeRep: 1,
    ver: 'Me',
  }
  const doorIconStyle = {
    height: '100px',
    width: '100px',
    display: 'flex',
    fill: '#FFFFFF',
  }
  const doorIconLayoutStyle = {
    width: '400px',
    height: '200px',
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '0px',
  }
  const doorTripleIconLayoutStyle = {
    width: '400px',
    height: '200px',
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '0px',
  }
  const rocketItemSizeStyle = {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '0px',
    width: '200px',
    height: '400px',
  }
  const onThisButtonStyle = {
    height: '500px',
    display: 'flex',
    alignItems: 'center',
  }

  return (
    <div style={Contents}>
      <div style={onThisButtonStyle}>
        <button
          type="button"
          className='RendezbooBtn'
          onClick={(e) => {
            goToPage('docking1')
          }}
        >
          <div style={doorIconLayoutStyle}>
            <BsFillDoorOpenFill style={doorIconStyle} />
          </div>
          <h3>1:1 Docking</h3>
        </button>
      </div>
      <div style={onThisButtonStyle}>
        <div style={rocketItemSizeStyle}>
          <RocketItem {...MeAndYou} />
        </div>
      </div>
      <div style={onThisButtonStyle}>
        <button
          type="button"
          className='RendezbooBtn'
          onClick={(e) => {
            goToPage('docking3')
          }}
        >
          <div style={doorTripleIconLayoutStyle}>
            <BsFillDoorClosedFill style={doorIconStyle} />
            <BsFillDoorOpenFill style={doorIconStyle} />
            <BsFillDoorClosedFill style={doorIconStyle} />
          </div>
          <h3>3:3 Docking</h3>
        </button>
      </div>
    </div>
  )
}

export default Rendezboo
