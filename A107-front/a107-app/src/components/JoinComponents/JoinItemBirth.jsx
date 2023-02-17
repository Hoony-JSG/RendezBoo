import { useEffect } from 'react'
import { useState } from 'react'
import '../../Styles/BirthCalendarStyle.css'

const JoinItemBirth = (props) => {
  const [selectedDate, setSelectedDate] = useState(new Date())
  const [month, setMonth] = useState(selectedDate.getMonth())
  const [year, setYear] = useState(selectedDate.getFullYear())

  const currentYear = new Date().getFullYear()
  const currentMonth = new Date().getMonth()
  const handlePrevMonth = () => {
    setMonth((prevMonth) => (prevMonth === 0 ? 11 : prevMonth - 1))
    if (month === 0) {
      setYear((prevYear) => prevYear - 1)
    }
  }

  const handleNextMonth = () => {
    setMonth((prevMonth) => (prevMonth === 11 ? 0 : prevMonth + 1))
    if (month === 11) {
      setYear((prevYear) => prevYear + 1)
    }
  }

  const handleSelect = (day) => {
    setSelectedDate(new Date(year, month, day))
  }

  const handleSelectYear = (e) => {
    setYear(e.target.value)
  }

  const getDays = () => {
    const firstDayOfMonth = new Date(year, month, 1)
    const firstDayOfWeek = firstDayOfMonth.getDay()
    const daysInMonth = new Date(year, month + 1, 0).getDate()
    const days = []

    for (let i = 0; i < firstDayOfWeek; i++) {
      days.push(<div key={`empty-${i}`} className="empty"></div>)
    }

    for (let i = 1; i <= daysInMonth; i++) {
      const date = new Date(year, month, i)
      const isSelected = date.getTime() === selectedDate.getTime()

      days.push(
        <div
          key={i}
          className={`day ${isSelected ? 'selected' : ''}`}
          onClick={() => handleSelect(i)}
        >
          {i}
        </div>
      )
    }

    return days
  }

  const getDaysOfWeek = () => {
    const daysOfWeek = []
    const weekdays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat']

    for (let i = 0; i < 7; i++) {
      daysOfWeek.push(
        <div key={i} className="day-of-week">
          {weekdays[i]}
        </div>
      )
    }

    return daysOfWeek
  }

  useEffect(() => {
    const birthYear = selectedDate.getFullYear()
    if (currentYear - birthYear >= 19) {
      props.setHas(true)
    } else {
      props.setHas(false)
    }
    console.log(
      '년도 : ' +
        selectedDate.toLocaleDateString('default', { year: 'numeric' })
    )
    console.log(
      '월 : ' + selectedDate.toLocaleDateString('default', { month: 'numeric' })
    )
    console.log(
      '일 : ' + selectedDate.toLocaleDateString('default', { day: 'numeric' })
    )
  }, [selectedDate])

  return (
    <div className="date-picker">
      <div className="header">
        <div className="header-left-right">
          <select
            className="year-select"
            value={year}
            onChange={handleSelectYear}
          >
            {Array.from({ length: 90 }, (_, i) => (
              <option key={i} value={currentYear - i}>
                {currentYear - i} 년
              </option>
            ))}
          </select>
        </div>
        <div className="select-month-withbuttons header-center">
          <button
            className="prev-month-btn"
            onClick={handlePrevMonth}
            disabled={year === currentYear - 89 && month === currentMonth}
          >
            {'<'}
          </button>
          <div className="month-year">
            {new Date(Date.UTC(year, month)).toLocaleString('default', {
              month: 'long',
            })}{' '}
          </div>
          <div>
            <button
              className="next-month-btn"
              onClick={handleNextMonth}
              disabled={year === currentYear && month === currentMonth}
            >
              {'>'}
            </button>
          </div>
        </div>
        <div className="header-left-right"></div>
      </div>
      <div className="weekdays">{getDaysOfWeek()}</div>
      <div className="days">{getDays().map((day) => day)}</div>
      <div className="selected-date">
        {selectedDate.toLocaleString('default', {
          year: 'numeric',
          month: 'long',
          day: 'numeric',
          weekday: 'long',
        })}
      </div>
    </div>
  )
}

export default JoinItemBirth
