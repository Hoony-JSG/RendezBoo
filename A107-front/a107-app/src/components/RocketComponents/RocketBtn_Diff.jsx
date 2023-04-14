import { useNavigate } from 'react-router-dom'
import '../../Styles/RocketItemButtonStyle.css'
import rocketImg from '../../Images/RendezRocket.png'
const RocketBtn_Diff = (props) => {
  const navigate = useNavigate()
  return (
    <div className="RocketBtnDiff_container">
      <img
        className="RocketBtnDiff_rocket-img"
        src={rocketImg}
        alt="Rocket"
      ></img>
    </div>
  )
}

export default RocketBtn_Diff
