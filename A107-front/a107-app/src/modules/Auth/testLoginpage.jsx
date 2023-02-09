import { setRefreshToken, removeToken } from './Jwt'
import React, { useState } from 'react'
import { SET_TOKEN, REMOVE_TOKEN } from '../../containers/JwtContainer'
import { SET_USER_INFO, REMOVE_USER_INFO } from '../../containers/UserInfo'
import { useDispatch, useSelector } from 'react-redux'
import axios from 'axios'
import jwtDecode from 'jwt-decode'

const BASE_URL = 'https://i8a107.p.ssafy.io'
// const BASE_URL = 'http://localhost:8080'

// test@gmail.com
// 1234

const LoginTest = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userSeq, setUserSeq] = useState('')
  const [userName, setUserName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  const dispatch = useDispatch()
  const accessToken = useSelector(
    (state) => state.accessTokenReducer.accessToken
  )

  const login = async (event) => {
    event.preventDefault()

    try {
      const response = await axios.post(`${BASE_URL}/api/user/login`, {
        email: email,
        password: password,
      })

      console.log(response.data);
      setRefreshToken(response.data.refreshToken)
      
      const decode = jwtDecode(response.data.accessToken)
      console.log(12)
      console.log(decode)
      dispatch(SET_USER_INFO(decode))
      dispatch(SET_TOKEN(response.data.accessToken))

      
      setIsLoggedIn(true);
      setUserName(decode.name)
      setUserSeq(decode.seq)
      setEmail(decode.email)

      // setUserSeq()
    } catch (error) {
      setError(error.message)
    }
  }

  // const logout = () => {
  //   // const { accessToken } = selector(state => state.accessToken)
  //   // console.log(selector(state => state.accessTokenReducer.accessToken))

  //   removeToken()
  //   dispatch(REMOVE_TOKEN())
  //   // dispatch(REMOVE_USER_INFO())

  //   setIsLoggedIn(false);
  // };

  return (
    <div>
      {isLoggedIn ? (
        <div>
          <ul>
          <li>username = {userName}</li>
          <li>userSeq = {userSeq}</li>
          <li>userEmail = {email}</li>
          </ul>
        </div>
      ) : (
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
          <button type="submit">Login</button>
        </form>
      )}
    </div>
  )
}

export default LoginTest
