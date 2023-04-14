import React, { useEffect, useState } from 'react'
import { MdGppGood, MdOutlineGppGood } from 'react-icons/md'

import '../../Styles/JoinPasswordStyle.css'
const JoinPassword = (props) => {
  const [firstPassword, setFirstPassword] = useState('')
  const [firstPasswordValid, setFirstPasswordValid] = useState(false)

  const [secondPassword, setSecondPassword] = useState('')

  const [passwordsMatch, setPasswordsMatch] = useState(false)

  const [errorMessage, setErrorMessage] = useState(
    '마우스를 올리면 비밀번호가 보입니다.'
  )

  const [showPassword, setShowPassword] = useState(false)

  const validateFirstPassword = (password) => {
    if (password.length < 4 || password.length > 20) {
      setErrorMessage(
        `비밀번호 길이는 4~20자 필수입니다. [${password.length}자]`
      )
      setFirstPasswordValid(false)
      return false
    } else if (
      !/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{4,}$/.test(
        password
      )
    ) {
      setErrorMessage(
        `영문,숫자,특수문자(!,@,#,$) 하나 이상 포함해야합니다 [${password.length}자]`
      )
      setFirstPasswordValid(false)
      return false
    }
    setErrorMessage('마우스를 올리면 비밀번호가 보입니다.')
    setFirstPasswordValid(true)
    return true
  }

  const handleFirstPasswordChange = (event) => {
    setFirstPassword(event.target.value)
    if (secondPassword !== '') {
      //첫번째가 바뀌면 두번쨰 초기화 & 넘겨주기 false
      setSecondPassword('')
      props.setHas(3, false)
      setPasswordsMatch(false)
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
  useEffect(handleWholePWRight, [passwordsMatch]) //두 비번이 동일한가 체크되면 부모로 넘겨주기

  return (
    <div className="JoinPW_container">
      <div className="JoinPW_box-discribe">비밀번호를 입력하세요.</div>
      <div className="JoinPW_password-input-container">
        <div className="JoinPW_two-input-boxes">
          <div className="JoinPW_password-input">
            {firstPasswordValid ? (
              <MdGppGood className="JoinPW_validation-icon JoinPW_success" />
            ) : (
              <MdOutlineGppGood className="JoinPW_validation-icon JoinPW_error" />
            )}
            <input
              className="JoinPW_PWinput"
              placeholder="[새 비밀번호]"
              type={showPassword ? 'text' : 'password'}
              value={firstPassword}
              onChange={handleFirstPasswordChange}
              onMouseEnter={handleMouseEnter}
              onMouseLeave={handleMouseLeave}
            />
          </div>
          {errorMessage && (
            <div className="JoinPW_error-message">{errorMessage}</div>
          )}
          <div className="JoinPW_password-input">
            {passwordsMatch ? (
              <MdGppGood className="JoinPW_validation-icon JoinPW_success" />
            ) : (
              <MdOutlineGppGood className="JoinPW_validation-icon JoinPW_error" />
            )}
            <input
              className="JoinPW_PWinput"
              placeholder="[비밀번호 확인]"
              type={showPassword ? 'text' : 'password'}
              value={secondPassword}
              onChange={handleSecondPasswordChange}
              onMouseEnter={handleMouseEnter}
              onMouseLeave={handleMouseLeave}
            />
          </div>
        </div>
      </div>
    </div>
  )
}

export default JoinPassword
