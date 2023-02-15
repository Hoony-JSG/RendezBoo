import { useEffect, useState } from 'react'
import axios from 'axios'
import { FiUserCheck, FiUserX } from 'react-icons/fi'

import '../../Styles/JoinPhoneNumberCodeStyle.css'
const VerificationCode = (props) => {
  const [showResendButton, setShowResendButton] = useState(false)
  const [fixReadOnly, setFixReadOnly] = useState(false)
  const checkCodeLength = () => {
    VerificationCode.length === 6
      ? setShowCheckButton(true)
      : setShowCheckButton(false)
  }

  const [showCheckButton, setShowCheckButton] = useState(false)
  const [timeLeft, setTimeLeft] = useState()

  const timer = () => {
    setTimeLeft(10)
    setFillingBtn(true)
    const intervalId = setInterval(() => {
      setTimeLeft((prevTime) => {
        console.log('남은 시간 : ' + prevTime)
        if (prevTime === 1) {
          setShowResendButton(true)
          setFillingBtn(false)
        } else if (prevTime === 0) {
          setFillingBtn(false)
          clearInterval(intervalId)
          return (prevTime = 0)
        }
        return prevTime - 1
      })
    }, 1000)
  }
  const [newTimer, setNewTimer] = useState(false)
  const [remainThreeMinutes, setRemainThreeMinutes] = useState()
  const timer3min = () => {
    if (!newTimer) {
      setNewTimer(true)
      setRemainThreeMinutes(180)
      const intervalId = setInterval(() => {
        setRemainThreeMinutes((prevTime) => {
          if (prevTime === 1) {
            setNewTimer(false)
          } else if (prevTime === 0) {
            clearInterval(intervalId)
            setNewTimer(false)
            return (prevTime = 0)
          }
          return prevTime - 1
        })
      }, 1000)
    } else {
      setRemainThreeMinutes(180)
    }
  }

  const timeFormatter = (time) => {
    const minutes = Math.floor(time / 60)
    const seconds = time % 60
    return `${minutes} : ${seconds < 10 ? '0' : ''}${seconds}`
  }

  useEffect(timer3min, [])
  useEffect(timer, [])
  const [checkRight, setCheckRight] = useState()
  const checkCode = async () => {
    console.log('입력된 인증 코드 : ' + VerificationCode)
    try {
      let response = await axios.post(
        'https://i8a107.p.ssafy.io/api/sms/check',
        {
          code: VerificationCode,
          phoneNumber: props.PhoneNumber,
        }
      )
      setFixReadOnly(true)
      setShowCheckButton(false)
      setShowResendButton(false)
      props.setHas(2, true)
      setRemainThreeMinutes(0)
      setTimeLeft(0)
      setCheckRight(true)
    } catch (error) {
      console.log('인증 코드 에러 발생')
      console.log('전송된 인증번호 : ' + VerificationCode)
      console.error(error)
      setCheckRight(false)
    }
  }
  const [fillingBtn, setFillingBtn] = useState(false)

  const resendSMS = () => {
    setCheckRight('')
    timer3min()
    console.log('재전송 요청')
    setShowResendButton(false)
    props.sendSMS()
    timer()
  }
  const [VerificationCode, setVerificationCode] = useState(null)
  return (
    <div className="Code_container">
      <div className="Code_left-box">
        <div className="Code_check-result-icons">
          <div
            className={`Code_result-right ${
              checkRight === true ? 'Code_light-greening' : 'Code_light-hided'
            }`}
          >
            <FiUserCheck size="30" />
          </div>
          <div
            className={`Code_result-error ${
              checkRight === false ? 'Code_light-redding' : 'Code_light-hided'
            }`}
          >
            <FiUserX size="30" />
          </div>
        </div>
        <input
          className="Code_code-input"
          placeholder="인증 번호"
          onChange={(e) => {
            setVerificationCode(e.target.value)
          }}
          readOnly={fixReadOnly}
          onKeyUp={checkCodeLength}
          maxLength="6"
        ></input>
      </div>
      <div className="Code_right-box">
        <div className="Code_up-items">
          <button
            className="Code_check-btn"
            disabled={!showCheckButton || fixReadOnly}
            type="button"
            onClick={checkCode}
          >
            인증
          </button>
          <div className="Code_remain-time">
            남은 시간
            <br />({timeFormatter(remainThreeMinutes)})
          </div>
        </div>
        <div className="Code_bottom-items">
          <button
            className={`
            ${
              fillingBtn
                ? 'Code_resend-filling-animation Code_filling-background'
                : 'Code_filling-background'
            }
          `}
          ></button>
          <button
            className="Code_resend-btn"
            disabled={!showResendButton}
            type="button"
            onClick={resendSMS}
          >
            재전송 ({timeLeft})
          </button>
        </div>
      </div>
    </div>
  )
}

export default VerificationCode
