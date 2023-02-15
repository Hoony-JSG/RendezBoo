import { BsFillDoorClosedFill, BsFillDoorOpenFill } from 'react-icons/bs'
import { useNavigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import RocketItem from '../components/RocketComponents/RocketItem'
import '../Styles/Rendezboo.css'
import { useEffect, useState } from 'react'
import { getHeader } from '../modules/Auth/Jwt'
import axios from 'axios'

const Rendezboo = () => {
  const userid = useSelector((state) => state.userInfoReducer.userEmail)
  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)

  const APPLICATION_SERVER_URL = 'https://i8a107.p.ssafy.io/'
  const REQUEST_HEADER = getHeader()

  const [rocketUser, setRocketUser] = useState({
    seq: 0,
    email: '',
    city: '',
    gender: false,
    phoneNumber: '',
    name: '',
    profileImagePath: '',
    mbti: '',
    point: 0,
    createdAt: [2023, 1, 1, 0, 0, 0],
    updatedAt: [2023, 1, 1, 0, 0, 0],
    badge: null,
    userInterests: [],
  })

  useEffect(() => {
    axios
      .get(APPLICATION_SERVER_URL + 'api/user/' + userSeq, REQUEST_HEADER)
      .then((res) => {
        setRocketUser(res.data)
      })
      .catch((e) => {
        console.log(e)
      })
  }, [])

  console.log('랑데부 페이지')
  const navigate = useNavigate()

  // const goToPage = (props) => {
  //   navigate('/' + props)
  // }

  // const Contents = {
  //   display: 'flex',
  //   flexDirection: 'row',
  //   justifyContent: 'center',
  //   alignItems: 'center',
  //   padding: '0px',
  //   margin: '30px',
  //   height: 'auto',
  //   gap: '50px',
  // }

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
            navigate('/docking1')
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
          <RocketItem
            {...MeAndYou}
            profileImagePath={rocketUser.profileImagePath}
          />
        </div>
      </div>
      <div className="onThisButtonStyle">
        <button
          type="button"
          className="RendezbooBtn"
          onClick={(e) => {
            navigate('/docking3')
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
