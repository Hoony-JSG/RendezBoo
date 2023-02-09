import { useState } from 'react'
import axios from 'axios'
import VerificationCode from './VerificationCode'

const JoinItemPhoneNumber = (props) => {
  const [PhoneNumber, setPhoneNumber] = useState()
  const [showSendButton, setShowSendButton] = useState(false)
  const [showCheckComponent, setShowCheckComponent] = useState(false)

  const checkPhoneNumberLength = () => {
    PhoneNumber.length === 11 && PhoneNumber.includes('010')
      ? setShowSendButton(true)
      : setShowSendButton(false)
  }

  const sendFirstSMS = async () => {
    try {
      let response = await axios.post('http://52.78.60.53:8080/api/sms/send', {
        phoneNumber: PhoneNumber,
      })
      console.log('정상적으로 전송됨')
      setShowCheckComponent(true)
    } catch (error) {
      console.log('인증코드 전송 에러 발생')
      console.error(error)
      setShowCheckComponent(false)
    }
  }
  return (
    <div>
      <div>
        <span>
          휴대폰 번호 입력 : <br />
        </span>
        <input
          placeholder="휴대폰 번호 (- 제외)"
          onChange={(e) => {
            setPhoneNumber(e.target.value)
          }}
          onKeyUp={checkPhoneNumberLength}
        />
        <br />
        <button
          type="button"
          disabled={!showSendButton || showCheckComponent}
          onClick={sendFirstSMS}
        >
          send Code
        </button>
      </div>
      <div>
        {showCheckComponent && (
          <VerificationCode
            PhoneNumber={PhoneNumber}
            setHas={props.setHas}
            sendSMS={sendFirstSMS}
          />
        )}
      </div>
    </div>
  )
}

export default JoinItemPhoneNumber
