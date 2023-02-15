import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import MBTISelector from '../JoinComponents/MBTISelector'

import '../../Styles/RocketItemButtonStyle.css'

const RocketBtn_Same = (props) => {
  const navigate = useNavigate()
  const { Inquire } = props
  const [rocketUser, setRocketUser] = useState({
    email: 'wjdgnsxhsl@naver.com',
    gender: props.rocketUser.gender,
    phoneNumber: props.rocketUser.phoneNumber,
    name: props.rocketUser.name,
    profileImagePath: props.rocketUser.profileImagePath,
    mbti: 'ENFP',
    createdAt: props.rocketUser.createdAt,
    updatedAt: props.rocketUser.updatedAt,
  })
  const [popBtn, setPopBtn] = useState(false)
  const [userMBTI, setUserMBTI] = useState(rocketUser.mbti)
  const [tempMBTI, setTempMBTI] = useState()
  const [modalPop, setModalPop] = useState(false)
  const handleClick = () => {
    if (modalPop) {
      setUserMBTI(tempMBTI)
      setModalPop(false)
      setPopBtn(false)
    } else {
      setPopBtn(true)
      setModalPop(true)
    }
  }

  console.log(rocketUser)
  return (
    <div className="RocketBtnSame_container">
      <div className="RocketBtnSame_form-container">
        <div className="RocketBtnSame_form-box">
          <div className="RocketBtnSame_form-top-box">
            <p className="RocketBtnSame_form-p">INFORMATION</p>
          </div>
          <form className="RocketBtnSame_form-form">
            <div className="RocketBtnSame_form-input-box">
              <label className="RocketBtnSame_form-label">Name</label>
              <input
                className="RocketBtnSame_form-input"
                required=""
                name=""
                type="text"
                disabled
                value={`   ${rocketUser.name}`}
              />
            </div>
            <div className="RocketBtnSame_form-input-box">
              <label className="RocketBtnSame_form-label">Email</label>
              <input
                className="RocketBtnSame_form-input"
                required=""
                name=""
                type="text"
                disabled
                value={`   ${rocketUser.email}`}
              />
            </div>
            <div className="RocketBtnSame_form-input-box">
              <label className="RocketBtnSame_form-label">Tel</label>
              <input
                className="RocketBtnSame_form-input"
                required=""
                name=""
                type="text"
                disabled
                value={`   ${rocketUser.phoneNumber}`}
              />
            </div>
            <div className="RocketBtnSame_form-input-box">
              <label className="RocketBtnSame_form-label">MBTI</label>
              <input
                className="RocketBtnSame_form-input RocketBtnSame_form-input-mbti"
                required=""
                name=""
                type="text"
                disabled
                value={`   ${userMBTI}`}
              />
              <div className="RocketBtnSame_form-edit-box">
                <button
                  className="RocketBtnSame_form-button"
                  onClick={handleClick}
                >
                  <span className="RocketBtnSame_form-span"></span>
                  <span className="RocketBtnSame_form-span"></span>
                  <span className="RocketBtnSame_form-span"></span>
                  <span className="RocketBtnSame_form-span"></span>
                  {modalPop ? 'Edit' : 'New'}
                </button>
              </div>
            </div>
            {modalPop && (
              <div className="RocketBtnSame_modal">
                <MBTISelector mbti={setTempMBTI} setBtn={popBtn} />
              </div>
            )}
          </form>
        </div>
      </div>
    </div>
  )
}

export default RocketBtn_Same
