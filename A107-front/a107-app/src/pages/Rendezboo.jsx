import { BsFillDoorClosedFill, BsFillDoorOpenFill } from 'react-icons/bs'
import { useNavigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import RocketItem from '../components/RocketComponents/RocketItem'
import '../Styles/Rendezboo.css'

const Rendezboo = () => {
  const userid = useSelector((state) => state.userInfoReducer.userEmail)

  console.log('랑데부 페이지')
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
    gap: '50px',
  }
  const MeAndYou = {
    Me: userid,
    Inquire: userid,
    Rendez: true,
    BadgeRep: 1,
    ver: '',
  }

  return (
    <div className="Contents">
      <div className="onThisButtonStyle">
        <button
          type="button"
          className="RendezbooBtn"
          onClick={(e) => {
            goToPage('docking1')
          }}
        >
          <div className="doorIconLayoutStyle">
            <BsFillDoorOpenFill className="doorIconStyle" />
          </div>
          <h3>1:1 Docking</h3>
        </button>
      </div>
      <div className="onThisButtonStyle">
        <div className="rocketItemSizeStyle">
          <RocketItem {...MeAndYou} />
        </div>
      </div>
      <div className="onThisButtonStyle">
        <button
          type="button"
          className="RendezbooBtn"
          onClick={(e) => {
            goToPage('docking3')
          }}
        >
          <div className="doorIconLayoutStyle">
            <BsFillDoorClosedFill className="doorIconStyle" />
            <BsFillDoorOpenFill className="doorIconStyle" />
            <BsFillDoorClosedFill className="doorIconStyle" />
          </div>
          <h3>3:3 Docking</h3>
        </button>
      </div>
    </div>
  )
}

export default Rendezboo
