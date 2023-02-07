import React, { useState } from 'react'
import '../Styles/SocialLoginButton.css'
import kakaoLogo from '../Images/kakao-logo.png'
import naverlogo from '../Images/naver-logo.png'

const JoinItemSocialLoginButton = ({ type, onClick }) => {
  const [loading, setLoading] = useState(false)

  const handleSocialLogin = () => {
    console.log('헉')
    setLoading(true)

    setLoading(false)
  }

  return (
    <div>
      <button className={`SocialLoginButton `} onClick={onClick}>
        <img src={naverlogo} alt="Naver logo" />
      </button>
      <button
        className={`SocialLoginButton `}
        // onClick={onClick}
        onClick={handleSocialLogin}
        disabled={loading}
      >
        <img src={kakaoLogo} alt="Kakao logo" />
      </button>
    </div>
  )
}

export default JoinItemSocialLoginButton
