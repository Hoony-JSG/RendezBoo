import { useEffect } from 'react'
import { useState } from 'react'
import '../../Styles/BirthCalendarStyle.css'

const JoinItemBirth = (props) => {
  const [selectedDate, setSelectedDate] = useState(new Date())
  const [month, setMonth] = useState()
  const [year, setYear] = useState()
  const currentYear = new Date().getFullYear()
  const currentMonth = new Date().getMonth()
  const handlePrevMonth = () => {
    setMonth((month) => (month === 0 ? 11 : month - 1))
    if (month === 0) {
      setYear((year) => year - 1)
    }
  }

  const handleNextMonth = () => {
    setMonth((month) => (month === 11 ? 0 : month + 1))
    console.log('현재 데이터 속 year : ' + year)
    if (month === 11) {
      setYear((year) => year + 1)
    }
  }

  const handleSelect = (day) => {
    setSelectedDate(new Date(year, month, day))
  }

  const handleSelectYear = (e) => {
    console.log('선택 이어 : ' + e.target.value)
    const toInteger = parseInt(e.target.value)
    setYear(toInteger)
  }

  const getDays = () => {
    console.log('getDays 내부 데이터 : ' + year + ', ' + month)
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
  const [underNineteen, setUnderNineteen] = useState(true)
  useEffect(() => {
    setMonth(selectedDate.getMonth())
    console.log(year + ']]]]]]]]]]]]]]')
    if (year === undefined) setYear(selectedDate.getFullYear())
    const birthYear = selectedDate.getFullYear()
    if (currentYear - birthYear >= 19) {
      props.birthday(selectedDate)
      props.setHas(true)
      setUnderNineteen(false)
    } else {
      props.setHas(false)
      setUnderNineteen(true)
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
      <div className="Birth_body">
        <div className="Birth_body_dates">
          <div className="weekdays">{getDaysOfWeek()}</div>
          <div className="days">{getDays().map((day) => day)}</div>
        </div>
        <div
          className={underNineteen ? 'Birth_undernineteen' : 'selected-date'}
        >
          {underNineteen
            ? '19세 미만 이용 불가'
            : selectedDate.toLocaleString('default', {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                weekday: 'long',
              })}
        </div>
      </div>
    </div>
  )
}

export default JoinItemBirth
