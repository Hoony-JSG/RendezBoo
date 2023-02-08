import { useState } from 'react'

const JoinItemPhoneNumber = () => {
  const [PhoneNumber, setPhoneNumber] = useState()

  const [DisableButton, setDisableButton] = useState(true)

  const checkPhoneNumberLength = () => {
    const indexL = PhoneNumber.indexOf('-')
    const indexR = PhoneNumber.indexOf('-', 4)
    PhoneNumber.length === 13 &&
    indexL === 3 &&
    indexR === 8 &&
    PhoneNumber.includes('010')
      ? setDisableButton(false)
      : setDisableButton(true)
  }
  return (
    <div>
      <span>
        휴대폰 번호 입력 : <br />
      </span>
      <input
        placeholder="휴대폰 번호 (010-XXXX-XXXX)"
        onChange={(e) => {
          setPhoneNumber(e.target.value)
        }}
        onKeyUp={checkPhoneNumberLength}
      />
      <br />
      <button type="button" disabled={DisableButton}>
        인증 버튼
      </button>
    </div>
  )
}

export default JoinItemPhoneNumber
