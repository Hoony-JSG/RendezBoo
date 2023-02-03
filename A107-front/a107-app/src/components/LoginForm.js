import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'
const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://i8a107.p.ssafy.io:8080/';

const LoginForm = () => {
  console.log('로그인 폼')

  const realId = 'wjdgnsxhsl@naver.com'
  const realPw = '123123'

  let [id, setId] = useState('')
  let [pw, setPw] = useState('')

  const [button, setButton] = useState(false)

  function changeButton() {
    id.length <= 40 &&
    id.includes('@') &&
    (id.includes('.com') || id.includes('.net')) &&
    pw.length >= 5 &&
    pw.length <= 20
      ? setButton(true)
      : setButton(false)
  }

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

  const userValid = async function(loginForm) {
    await axios.post('api/user/login', {loginForm}, {
      headers: { 'Content-Type': 'application/json', },
    })
}

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
          onKeyUp={changeButton}
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
          onKeyUp={changeButton}
        />
      </div>
      <div>
        <button
          style={loginButtonStyle}
          type="button"
          disabled={!button}
          onClick={(e) => {
            userValid({id: id, pw: pw})
            }}
        />
      </div>
    </div>
  )

}

export default LoginForm
