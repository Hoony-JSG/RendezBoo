import { useEffect } from 'react'
import moment from 'moment'
import { useState } from 'react'

const JoinItemBirth = () => {
  const [birthday, setBirthday] = useState({
    year: '',
    month: '',
    day: '',
  })

  const [dayOfWeek, setDayOfWeek] = useState('')

  const handleYearChange = (e) => {
    setBirthday({ ...birthday, year: e.target.value })
  }

  const handleMonthChange = (e) => {
    setBirthday({ ...birthday, month: e.target.value })
  }

  const handleDayChange = (e) => {
    setBirthday({ ...birthday, day: e.target.value })
  }

  useEffect(() => {
    const date = moment(
      `${birthday.year}-${birthday.month}-${birthday.day}`,
      'YYYY-MM-DD'
    )
    setDayOfWeek(date.format('dddd'))
  }, [birthday])

  return (
    <div>
      <select value={birthday.year} onChange={handleYearChange}>
        <option value="">Year</option>
        {Array.from({ length: 100 }, (_, i) => (
          <option key={i} value={1920 + i}>
            {1920 + i}
          </option>
        ))}
      </select>
      <select value={birthday.month} onChange={handleMonthChange}>
        <option value="">Month</option>
        {Array.from({ length: 12 }, (_, i) => (
          <option key={i} value={i + 1}>
            {i + 1}
          </option>
        ))}
      </select>
      <select value={birthday.day} onChange={handleDayChange}>
        <option value="">Day</option>
        {Array.from({ length: 31 }, (_, i) => (
          <option key={i} value={i + 1}>
            {i + 1}
          </option>
        ))}
      </select>
      {dayOfWeek && <div>Day of Week: {dayOfWeek}</div>}
    </div>
  )
}

export default JoinItemBirth
