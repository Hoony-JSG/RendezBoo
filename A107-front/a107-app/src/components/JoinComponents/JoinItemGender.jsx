import { useState } from 'react'

import '../../Styles/JoinGenderStyle.css'

const JoinItemGender = (props) => {
  const [gender, setGender] = useState(null)

  const handleMale = () => {
    props.setHas(1, true)
    props.fixedGender(true)
    setGender('Male')
  }

  const handleFemale = () => {
    props.setHas(1, true)
    props.fixedGender(false)
    setGender('Female')
  }

  return (
    <div className="JoinGender_whole-box">
      <div className="JoinGender_box-discribe">성별을 입력해주세요.</div>
      <div className="JoinGender_bottom-box">
        <button
          onClick={handleMale}
          className={`JoinGender_gender-select-male ${
            gender === 'Male' ? 'clicked-male' : ''
          }`}
        >
          Male
        </button>
        <div className="JoinGender_between-space"></div>
        <button
          onClick={handleFemale}
          className={`JoinGender_gender-select-female ${
            gender === 'Female' ? 'clicked-female' : ''
          }`}
        >
          Female
        </button>
      </div>
    </div>
  )
}

export default JoinItemGender
