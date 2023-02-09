import { useState } from 'react'

const JoinItemPhoneNumber = () => {
  const [PhoneNumber, setPhoneNumber] = useState()

  const [DisableButton, setDisableButton] = useState(true)

  const checkPhoneNumberLength = () => {
    PhoneNumber.length === 11 && PhoneNumber.includes('010')
      ? setDisableButton(false)
      : setDisableButton(true)
  }

  const sesndFirstSMS = async () =>{
    try {
      let response = await axios.post('http://52.78.60.53:8080/api/sms/send', {
        phoneNumber: props.PhoneNumber,
      })
    } catch (error) {
      console.log('인증코드 전송 에러 발생')
      console.error(error)
    }
  }
  }
  return (
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
      <button type="button" disabled={DisableButton} onClick={sesndFirstSMS}>
        send Code
      </button>
    </div>
  )
}

export default JoinItemPhoneNumber
