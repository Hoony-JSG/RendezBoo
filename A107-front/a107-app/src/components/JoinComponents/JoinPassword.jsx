import React, { useEffect, useState } from 'react'
import { FaCheckCircle, FaExclamationCircle } from 'react-icons/fa'
import { MdGppGood, MdOutlineGppGood } from 'react-icons/md'

import '../../Styles/JoinPasswordStyle.css'
const JoinPassword = (props) => {
  const [firstPassword, setFirstPassword] = useState('')
  const [firstPasswordValid, setFirstPasswordValid] = useState(false)

  const [secondPassword, setSecondPassword] = useState('')

  const [passwordsMatch, setPasswordsMatch] = useState(false)

  const [errorMessage, setErrorMessage] = useState('')

  const [showPassword, setShowPassword] = useState(false)

  const validateFirstPassword = (password) => {
    if (password.length < 4 || password.length > 20) {
      setErrorMessage('Password length should be between 4 and 20 characters.')
      setFirstPasswordValid(false)
      return false
    } else if (
      !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{4,}$/.test(
        password
      )
    ) {
      setErrorMessage(
        'Password should contain at least one letter, one number, and one special character (!,@,#,$)'
      )
      setFirstPasswordValid(false)
      return false
    }
    setErrorMessage('')
    setFirstPasswordValid(true)
    return true
  }

  const handleFirstPasswordChange = (event) => {
    setFirstPassword(event.target.value)
    if (secondPassword !== '') {
      setSecondPassword('')
      props.setHas(3, false)
    }
    validateFirstPassword(event.target.value)
  }

  const handleSecondPasswordChange = (event) => {
    setSecondPassword(event.target.value)
    setPasswordsMatch(event.target.value === firstPassword)
  }

  const handleMouseEnter = () => {
    setShowPassword(true)
  }

  const handleMouseLeave = () => {
    setShowPassword(false)
  }
  const handleWholePWRight = () => {
    if (passwordsMatch) props.setHas(3, true)
  }
  useEffect(handleWholePWRight, [passwordsMatch])

  return (
    <div className="password-input-container">
      <div className="password-input">
        <input
          className="PWinput"
          type={showPassword ? 'text' : 'password'}
          value={firstPassword}
          onChange={handleFirstPasswordChange}
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
        />
        {firstPasswordValid ? (
          <MdGppGood className="validation-icon success" />
        ) : (
          <MdOutlineGppGood className="validation-icon error" />
        )}
      </div>
      {errorMessage && <div className="error-message">{errorMessage}</div>}
      <div className="password-input">
        <input
          className="PWinput"
          type={showPassword ? 'text' : 'password'}
          value={secondPassword}
          onChange={handleSecondPasswordChange}
          onMouseEnter={handleMouseEnter}
          onMouseLeave={handleMouseLeave}
        />
        {passwordsMatch ? (
          <MdGppGood className="validation-icon success" />
        ) : (
          <MdOutlineGppGood className="validation-icon error" />
        )}
      </div>
    </div>
  )
}

export default JoinPassword
