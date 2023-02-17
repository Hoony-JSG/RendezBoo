import { useState } from 'react'
import axios from 'axios'
import VerificationCode from './VerificationCode'
import '../../Styles/JoinPhoneNumberStyle.css'
const JoinItemPhoneNumber = (props) => {
  const [PhoneNumber, setPhoneNumber] = useState()
  const [showSendButton, setShowSendButton] = useState(false)
  const [showCheckComponent, setShowCheckComponent] = useState(false)
  const [showPNInput, setShowPNInput] = useState(true)
  const checkPhoneNumberLength = () => {
    PhoneNumber.length === 11 && PhoneNumber.includes('010')
      ? setShowSendButton(true)
      : setShowSendButton(false)
  }

  const sendFirstSMS = async () => {
    try {
      let response = await axios.post(
        'https://i8a107.p.ssafy.io/api/sms/send',
        {
          phoneNumber: PhoneNumber,
        }
      )
      console.log('정상적으로 전송됨')
      setShowCheckComponent(true)
    } catch (error) {
      console.log('인증코드 전송 에러 발생')
      console.error(error)
      setShowCheckComponent(false)
    }
  }

  const fixPhoneNumber = (index, value) => {
    props.setHas(index, value)
    props.fixedPhoneNumber(PhoneNumber)
    if (value === true) {
      setShowPNInput(false)
    } else {
      setShowPNInput(true)
    }
  }
  return (
    <div className="JoinPhoneNumber_container">
      <div className="JoinPhoneNumber_box-discribe">
        휴대폰 번호를 인증해주세요.
      </div>
      <div className="JoinPhoneNumber_box-input">
        <div
          className={`JoinPhoneNumber_pn-input-container ${
            showCheckComponent ? 'slide-up' : ''
          }`}
        >
          <input
            className="JoinPhoneNumber_pn-input"
            placeholder="휴대폰 번호 (- 제외)"
            disabled={!showPNInput}
            onChange={(e) => {
              setPhoneNumber(e.target.value)
            }}
            onKeyUp={checkPhoneNumberLength}
            maxLength="11"
            autoFocus
          />
          <button
            className="JoinPhoneNumber_send-code-button"
            type="button"
            disabled={!showSendButton || showCheckComponent}
            onClick={sendFirstSMS}
          >
            Send Code
          </button>
        </div>
        <div
          className={`JoinPhoneNumber_code-input-container ${
            showCheckComponent ? 'show' : ''
          }`}
        >
          <div className="JoinPhoneNumber_VerificationCode-comp">
            {showCheckComponent && (
              <VerificationCode
                PhoneNumber={PhoneNumber}
                setHas={fixPhoneNumber}
                sendSMS={sendFirstSMS}
              />
            )}
          </div>
        </div>
      </div>
    </div>
  )
}

export default JoinItemPhoneNumber
