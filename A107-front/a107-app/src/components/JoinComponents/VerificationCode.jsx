import { useEffect, useState } from 'react'
import axios from 'axios'
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

  const checkCode = async () => {
    console.log('입력된 인증 코드 : ' + VerificationCode)
    try {
      let response = await axios.post('http://52.78.60.53:8080/api/sms/check', {
        code: VerificationCode,
        phoneNumber: props.PhoneNumber,
      })
      setFixReadOnly(true)
      setShowCheckButton(false)
      setShowResendButton(false)
      props.setHas(2, true)
    } catch (error) {
      console.log('인증 코드 에러 발생')
      console.log('전송된 인증번호 : ' + VerificationCode)
      console.error(error)
    }
  }
  const [fillingBtn, setFillingBtn] = useState(false)

  const resendSMS = () => {
    timer3min()
    console.log('재전송 요청')
    setShowResendButton(false)
    props.sendSMS()
    timer()
  }
  const [VerificationCode, setVerificationCode] = useState(null)
  return (
    <div className="Code-container">
      <input
        className="Code_code-input"
        placeholder="인증 번호"
        onChange={(e) => {
          setVerificationCode(e.target.value)
        }}
        readOnly={fixReadOnly}
        onKeyUp={checkCodeLength}
      ></input>
      <div className="Code_two-btn-container">
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
            남은 시간 ({timeFormatter(remainThreeMinutes)})
          </div>
        </div>
        <div className="Code_bottom-items">
          <div className="Code_filling-background"></div>
          <button
            className={`
          ${
            fillingBtn
              ? 'Code_resend-filling Code_resend-btn'
              : 'Code_resend-btn'
          }
          `}
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
