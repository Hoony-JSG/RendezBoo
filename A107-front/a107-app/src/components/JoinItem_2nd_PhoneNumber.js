import { useState } from 'react'

const JoinItem_2nd_PhoneNumber = () => {
  const [PhoneNumber, setPhoneNumber] = useState('')

  const [DisableButton, setDisableButton] = useState(true)

  const checkPhoneNumberLength = () => {
    PhoneNumber.length === 11 &&
    PhoneNumber.includes('-', 3) &&
    PhoneNumber.includes('-', 7)
      ? setDisableButton(false)
      : setDisableButton(true)
  }

  return (
    <div>
      J_2_휴대폰 인증 및 정보 가져오기
      <div>
        <input
          placeholder="휴대폰 번호 (010-XXXX-XXXX)"
          onChange={(e) => {
            setPhoneNumber(e.target.value)
          }}
          onKeyUp={checkPhoneNumberLength}
        />
      </div>
      <div>
        <button type="button" disabled={DisableButton}>
          인증 버튼
        </button>
      </div>
    </div>
  )
}

export default JoinItem_2nd_PhoneNumber
