import { useState } from 'react'
// import ReactDatePicker from 'react-datepicker'

// import 'react-datepicker/dist/react-datepicker.css'
import '../styles/DatePickerCenterStyle.css'

const JoinItemBirth = () => {
  const [birth, setBirth] = useState(new Date())

  const [selectedDate, setSelectedDate] = useState(null)

  return (
    <div>
      생년월일
      <br />
      {/* <ReactDatePicker
        showYearDropdown
        dropdownMode="select"
        showMonthDropdown
        locale="ko"
        selected={birth}
        onChange={(date) => setBirth(date)}
        maxDate={new Date()}
        dateFormat="yyyy년 MM월 dd일 (eee요일)"
      /> */}
    </div>
  )
}

export default JoinItemBirth
