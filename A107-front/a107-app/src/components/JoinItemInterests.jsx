import React, { useState } from 'react'
import '../Styles/JoinItemInterestsStyle.css'
const JoinItemInterests = () => {
  const [selectedInterests, setSelectedInterests] = useState([])
  const [glow, setGlow] = useState(false)
  const handleInterestToggle = (interest) => {
    if (selectedInterests.includes(interest)) {
      setSelectedInterests(selectedInterests.filter((i) => i !== interest))
      setGlow(false)
    } else {
      setSelectedInterests([...selectedInterests, interest])
      setGlow(true)
    }
  }

  return (
    <div className="interest-selector">
      <h3>Select your interests:</h3>
      <ul>
        <li>
          <label
            className={`interest ${
              selectedInterests.includes('Reading') ? 'active' : ''
            } ${glow ? 'glow' : ''}`}
          >
            <input
              type="checkbox"
              checked={selectedInterests.includes('Reading')}
              onChange={() => handleInterestToggle('Reading')}
              className="checkbox"
            />
            <span className="checkbox-text">Reading</span>
          </label>
        </li>
        <li>
          <label
            className={`interest ${
              selectedInterests.includes('Sports') ? 'active' : ''
            } ${glow ? 'glow' : ''}`}
          >
            <input
              type="checkbox"
              checked={selectedInterests.includes('Sports')}
              onChange={() => handleInterestToggle('Sports')}
              className="checkbox"
            />
            <span className="checkbox-text">Sports</span>
          </label>
        </li>
        <li>
          <label
            className={`interest ${
              selectedInterests.includes('Camping') ? 'active' : ''
            } ${glow ? 'glow' : ''}`}
          >
            <input
              type="checkbox"
              checked={selectedInterests.includes('Camping')}
              onChange={() => handleInterestToggle('Camping')}
              className="checkbox"
            />
            <span className="checkbox-text">Camping</span>
          </label>
        </li>
        {/* Add more interests as needed */}
      </ul>
    </div>
  )
}

export default JoinItemInterests
