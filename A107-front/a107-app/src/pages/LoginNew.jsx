import { setRefreshToken } from '../modules/Auth/Jwt'
import React, { useState } from 'react'
import { SET_TOKEN } from '../containers/JwtContainer'
import { SET_USER_INFO } from '../containers/UserInfoContainer'
import { useDispatch, useSelector } from 'react-redux'
import axios from 'axios'
import jwtDecode from 'jwt-decode'
import { useNavigate } from 'react-router'

const LoginNew = () => {
  const [userSeq, setUserSeq] = useState('')
  const [userName, setUserName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  const LOCAL_SERVER_URL = 'http://localhost:8080'
  const APPLICATION_SERVER_URL = 'https://i8a107.p.ssafy.io'

  const dispatch = useDispatch()
  const accessToken = useSelector(
    (state) => state.accessTokenReducer.accessToken
  )

  const navigate = useNavigate()

  const login = async (event) => {
    event.preventDefault()

    try {
      const response = await axios.post(`${LOCAL_SERVER_URL}/api/user/login`, {
        email: email,
        password: password,
      })

      setRefreshToken(response.data.refreshToken)

      const decode = jwtDecode(response.data.accessToken)
      dispatch(SET_USER_INFO(decode))
      dispatch(SET_TOKEN(response.data.accessToken))

      setUserName(decode.name)
      setUserSeq(decode.seq)
      setEmail(decode.email)

      navigate('/')
    } catch (error) {
      setError(error.message)
    }
  }

  const serverLogin = async (event) => {
    event.preventDefault()

    try {
      const response = await axios.post(
        `${APPLICATION_SERVER_URL}/api/user/login`,
        {
          email: email,
          password: password,
        }
      )

      setRefreshToken(response.data.refreshToken)

      const decode = jwtDecode(response.data.accessToken)
      dispatch(SET_USER_INFO(decode))
      dispatch(SET_TOKEN(response.data.accessToken))

      setUserName(decode.name)
      setUserSeq(decode.seq)
      setEmail(decode.email)

      navigate('/rendezboo')
    } catch (error) {
      setError(error.message)
    }
  }

  return (
    <div>
      <form onSubmit={login}>
        <div>
          <label htmlFor="email">email:</label>
          <input
            type="text"
            id="email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
          />
        </div>
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <button type="submit">로컬로 로그인</button>
      </form>
      <button onClick={serverLogin}>서버로 로그인</button>
    </div>
  )
}

export default LoginNew
