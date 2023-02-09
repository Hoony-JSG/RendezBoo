import { useState } from 'react'

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
      <button
        onClick={handleMale}
        style={
          gender === 'Male'
            ? { backgroundColor: 'lightblue' }
            : { backgroundColor: 'white' }
        }
      >
        Male
      </button>
      <button
        onClick={handleFemale}
        style={
          gender === 'Female'
            ? { backgroundColor: 'lightpink' }
            : { backgroundColor: 'white' }
        }
      >
        Female
      </button>
      <p>Selected Gender: {gender}</p>
    </div>
  )
}

export default JoinItemGender
