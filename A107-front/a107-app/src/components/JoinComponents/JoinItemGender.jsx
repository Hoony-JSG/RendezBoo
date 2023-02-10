import { useState } from 'react'

import '../../Styles/JoinGenderStyle.css'

const JoinItemGender = (props) => {
  const [gender, setGender] = useState(null)

  const handleMale = () => {
    props.setHas(1, true)
    setGender('Male')
  }

  const handleFemale = () => {
    props.setHas(1, true)
    setGender('Female')
  }

  return (
    <div>
      <button onClick={handleMale} className={'gender-select'}>
        Male
      </button>
      <button onClick={handleFemale} className={'gender-select'}>
        Female
      </button>
      <p>Selected Gender: {gender}</p>
    </div>
  )
}

export default JoinItemGender
