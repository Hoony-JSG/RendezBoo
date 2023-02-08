import { useState } from 'react'

const JoinItemGender = () => {
  const [gender, setGender] = useState('')
  return (
    <div>
      성별
      <br />
      <select value={gender} onChange={(e) => setGender(e.target.value)}>
        <option value="" disabled>
          Select your gender
        </option>
        <option value="male">Male</option>
        <option value="female">Female</option>
        <option value="other">Other</option>
      </select>
    </div>
  )
}

export default JoinItemGender
