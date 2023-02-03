import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'

const Join = () => {
  console.log("조인 페이지");
  
    let [id, setId] = useState('')
    let [pw, setPw] = useState('')

    const [button, setButton] = useState(true)
    
    const navigate = useNavigate()
    
    const goToAfterLogin = (props) => {
      console.log(props)
      const idString = props.split('@', 1)
      console.log(idString)
      navigate('/')
    }

    const loginButtonStyle = {
      margin: '10px 0 0 0',
      padding: '0',
      width: '100px',
      height: '30px',
      border: 'none',
      backgroundcolor: 'black',
      color: 'white',
      fontsize: '17px',
      borderradius: '5px',
    }

    // {
    //   "city": "string",
    //   "email": "string",
    //   "gender": true,
    //   "mbti": "string",
    //   "name": "string",
    //   "password": "string",
    //   "phoneNumber": "string",
    //   "profileImagePath": "string"
    // }


  return (
    <div>
      로그인폼
      <div>
        <input
          placeholder="Enter ID..."
          id="id"
          onChange={(e) => {
            setId(e.target.value)
          }}
        />
      </div>
      <div>
        <input
          type="password"
          placeholder="Enter PW..."
          id="password"
          onChange={(e) => {
            setPw(e.target.value)
          }}
        />
      </div>
      <div>
        <button
          style={loginButtonStyle}
          type="button"
          disabled={!button}
        />
      </div>
    </div>
  )};
export default Join;
