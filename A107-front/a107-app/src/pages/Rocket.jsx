import { useEffect, useState } from 'react'

import { useParams } from 'react-router-dom'
import RocketBadge from '../components/RocketComponents/RocketBadge'
import RocketItem from '../components/RocketComponents/RocketItem'
import RocketRadar from '../components/RocketComponents/RocketRadar'
import { getHeader } from '../modules/Auth/Jwt'
import axios from 'axios'
import { useSelector } from 'react-redux'

import '../Styles/RocketStyle.css'

const Rocket = () => {
  const APPLICATION_SERVER_URL = 'https://i8a107.p.ssafy.io/'
  const CLOUD_FRONT_URL = 'https://d156wamfkmlo3m.cloudfront.net/'
  const REQUEST_HEADER = getHeader()

  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)
  const Inquire = useParams().userid
  const ver = userSeq == Inquire ? 'Me' : 'Other'

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

  const [rocketUserEmotion, setRocketUserEmotion] = useState({
    anger: 0,
    disgust: 0,
    fear: 0,
    happiness: 0,
    sadness: 0,
    surprise: 0,
  })

  const [badgeList, setBadgeList] = useState([])

  const MeAndYou = {
    Me: rocketUser.name,
    Inquire: Inquire,
    ver: ver,
    BadgeRep: rocketUser.badge,
    rocketUser: rocketUser,
  }

  useEffect(() => {
    axios
      .get(APPLICATION_SERVER_URL + 'api/user/' + Inquire, REQUEST_HEADER)
      .then((res) => {
        setRocketUser(res.data)
      })
      .catch((e) => {
        console.log(e)
      })
    axios
      .get(APPLICATION_SERVER_URL + 'api/emotion/' + Inquire, REQUEST_HEADER)
      .then((res) => {
        setRocketUserEmotion(res.data)
        console.log(res.data)
      })
      .catch((e) => {
        console.log(e)
      })
    axios
      .get(APPLICATION_SERVER_URL + 'api/badges/' + Inquire, REQUEST_HEADER)
      .then((res) => {
        setBadgeList(res.data)
      })
      .catch((e) => {
        console.log(e)
      })
  }, [])

  console.log(ver)

  return (
    <div className="Rocket_Page">
      <div className="Rocket_container">
        <div className="Rocket_title">
          <span>{rocketUser.name}'s License</span>
        </div>
        <div className="Rocket_rocketitem-whole-box">
          <div className="Rocket_rocketitem-left-box">
            <RocketItem
              {...MeAndYou}
              profileImagePath={rocketUser.profileImagePath}
            />
          </div>
          <div className="Rocket_rocketitem-right-box">
            <div className="Rocket_rocketitem-right-top-box">
              <RocketBadge {...MeAndYou} badgeList={badgeList} />
            </div>
            <div className="Rocket_rocketitem-right-bottom-box">
              <RocketRadar rocketUserEmotion={rocketUserEmotion} />
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Rocket
