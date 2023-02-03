import { useState } from 'react'
import TempBorderStyle from '../Styles/TempBorderStyle'
import DatePicker from 'react-datepicker'

import { ko } from 'date-fns/esm/locale'
import 'react-datepicker/dist/react-datepicker.css'

const JoinItem_2nd_PhoneNumber = () => {
  const name = '훈스'
  const [gender, setGender] = useState('false')
  const [birth, setBirth] = useState(new Date())
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
      J_2_휴대폰 인증 및 정보 가져오기
      <div>
        <div style={TempBorderStyle}>이름</div>
        <div style={TempBorderStyle}>성별</div>
        <div style={TempBorderStyle}>
          생년월일
          <br />
          <div>
            <DatePicker
              showYearDropdown
              dropdownMode="select"
              showMonthDropdown
              locale={ko}
              selected={birth}
              onChange={(date) => setBirth(date)}
              maxDate={new Date()}
              dateFormat="yyyy년 MM월 dd일 (eee요일)"
            />
          </div>
        </div>
        <div style={TempBorderStyle}>
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
      </div>
    </div>
  )
}

export default JoinItem_2nd_PhoneNumber
