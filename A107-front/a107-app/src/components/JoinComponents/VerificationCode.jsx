import { useEffect, useState } from 'react'
import axios from 'axios'
import '../../Styles/PhoneNumberCodeInputStyle.css'
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
    const intervalId = setInterval(() => {
      setTimeLeft((prevTime) => {
        console.log('남은 시간 : ' + prevTime)
        if (prevTime === 1) {
          setShowResendButton(true)
        } else if (prevTime === 0) {
          clearInterval(intervalId)
          return (prevTime = 0)
        }
        return prevTime - 1
      })
    }, 1000)
  }
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
  const resendSMS = () => {
    console.log('재전송 요청')
    setShowResendButton(false)
    props.sendSMS()
    timer()
  }
  const [VerificationCode, setVerificationCode] = useState(null)
  return (
    <div>
      <input
        className="CodeInput"
        placeholder="인증 번호"
        onChange={(e) => {
          setVerificationCode(e.target.value)
        }}
        readOnly={fixReadOnly}
        onKeyUp={checkCodeLength}
      ></input>
      <button
        disabled={!showCheckButton || fixReadOnly}
        type="button"
        onClick={checkCode}
      >
        인증
      </button>
      <button disabled={!showResendButton} type="button" onClick={resendSMS}>
        재전송 ({timeLeft})
      </button>
    </div>
  )
}

export default VerificationCode
