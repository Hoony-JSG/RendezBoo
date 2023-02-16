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

  // const [profileImage, setProfileImage] = useState('')
  const chekcSetProfileImage = (inputFile) => {
    // setProfileImage(inputFile.name)
    console.log('체크 셋 프로필 이미지')
    console.log(inputFile.name)
    if (window.confirm('이미지 업로드?')) {
      changeProfileImage(inputFile)
    }
  }
  // const setNewProfile = (file) => {
  //   console.log('Rocket페이지에서 사진 setNewProfile : ' + file)
  //   changeProfileImage(setProfileImage)
  // }

  const changeProfileImage = async (profileImage) => {
    const formData = new FormData()
    formData.append('file', profileImage)
    console.log('변경할 파일 전체: ')
    console.log(profileImage)
    try {
      axios({
        method: 'post',
        url:
          'https://i8a107.p.ssafy.io/api/user/profile?userSeq=' +
          rocketUser.seq,
        data: formData,
        headers: { 'Content-Type': 'multipart/form-data' },
      })
      console.log('프로필 이미지 변경 성공')
    } catch (error) {
      console.log('프로필 이미지 변경 에러')
      console.error(error)
    }
  }
  const [first, setFirst] = useState(false)

  // useEffect(() => {
  //   if (first === true) {
  //     console.log('지금 보는 곳')
  //     console.log(profileImage)
  //     if (window.confirm('이미지 업로드?')) {
  //       changeProfileImage(profileImage)
  //     }
  //   }
  // }, [profileImage])

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
    console.log('useEffect')
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
  const notJoin = () => {
    console.log('MyRocket')
  }
  return (
    <div className="bottom-nav-page">
      <div className="Rocket_container">
        <div className="Rocket_title">
          <span>{rocketUser.name}'s License</span>
        </div>
        <div className="Rocket_rocketitem-whole-box">
          <div className="Rocket_rocketitem-right-box">
            <div className="Rocket_rocketitem-right-top-box">
              <RocketBadge {...MeAndYou} badgeList={badgeList} />
            </div>
            <div className="Rocket_rocketitem-right-bottom-box">
              <div className="Rocket_radar-container">
                <div className="Rocket_radar-title">
                  <span>감정 분석 기록</span>
                </div>
                <RocketRadar rocketUserEmotion={rocketUserEmotion} />
              </div>
            </div>
          </div>
          <div className="Rocket_rocketitem-left-box">
            <RocketItem
              {...MeAndYou}
              setProfileImage={chekcSetProfileImage}
              profileImagePath={rocketUser.profileImagePath}
              setTrue={notJoin}
              checkRocket={true}
              checkFirst={setFirst}
              rocketUser={rocketUser}
            />
          </div>
        </div>
      </div>
    </div>
  )
}

export default Rocket
