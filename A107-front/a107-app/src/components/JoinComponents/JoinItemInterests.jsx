import React, { useState } from 'react'
import '../../Styles/JoinItemInterestsStyle.css'
const JoinItemInterests = () => {
  const [selectedInterests, setSelectedInterests] = useState([])
  const [count, setCount] = useState(0)
  const interests = [
    'Reading',
    'Sports',
    'Camping',
    '1',
    '2',
    '3',
    '4',
    '5',
    '6',
    '7',
    '8',
    '9',
    '10',
  ]

  const handleInterestChange = (interest) => {
    setSelectedInterests(
      selectedInterests.includes(interest)
        ? selectedInterests.filter((i) => i !== interest)
        : [...selectedInterests, interest]
    )
    setCount(selectedInterests.includes(interest) ? count - 1 : count + 1)
  }

  return (
    <div className="interest-selector">
      <h3>Select your interests:</h3>
      <ul>
        {interests.map((interest) => (
          <li key={interest}>
            <label
              className={`interest ${
                selectedInterests.includes(interest) ? 'active' : ''
              }`}
            >
              <input
                type="checkbox"
                checked={selectedInterests.includes(interest)}
                onChange={() => handleInterestChange(interest)}
                className="checkbox"
              />
              <span className="checkbox-text">{interest}</span>
            </label>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default JoinItemInterests
