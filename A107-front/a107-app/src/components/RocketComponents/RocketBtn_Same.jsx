import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import '../../Styles/RocketItemButtonStyle.css'
const RocketBtn_Same = (props) => {
  const navigate = useNavigate()
  const { Inquire } = props
  const [rocketUser, setRocketUser] = useState(props.rocketUser)
  // seq: 0,
  // email: '',
  // city: '',
  // gender: false,
  // phoneNumber: '',
  // name: '',
  // profileImagePath: '',
  // mbti: '',
  // point: 0,
  // createdAt: [2023, 1, 1, 0, 0, 0],
  // updatedAt: [2023, 1, 1, 0, 0, 0],
  // badge: null,
  // userInterests: [],
  function edit() {
    console.log('정보 수정')
    navigate('/userinfo/' + Inquire)
  }
  function itemBox() {
    console.log('아이템 상자')
    navigate('/inventory/' + Inquire)
  }
  console.log(rocketUser)
  return (
    <div className="RocketBtnSame_container">
      정보 수정
      <div className="tempborders">{rocketUser.email}</div>
      {rocketUser.mbti}
      {/* <button className="RocketItemButton" onClick={edit}></button> */}
      {/* <button className="RocketItemButton" onClick={itemBox}>
        아이템 상자
      </button> */}
    </div>
  )
}

export default RocketBtn_Same
