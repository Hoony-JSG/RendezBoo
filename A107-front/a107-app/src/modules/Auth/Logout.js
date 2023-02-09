import { removeToken, getRefreshTokenFromCookie } from './Jwt'
import React, { useState } from 'react'
import { useNavigate } from 'react-router';
import { SET_TOKEN, REMOVE_TOKEN } from '../../containers/JwtContainer'
import { SET_USER_INFO, REMOVE_USER_INFO } from "../../containers/UserInfo";
import { useDispatch, useSelector } from 'react-redux'
import axios from 'axios'
import jwtDecode from 'jwt-decode'

// const BASE_URL = 'http://localhost:8080'

// test@gmail.com
// 1234

// 안 쓰는 파일임 (나중에 지울 거!!!!)


const Logout = async (event) => {
  // const [error, setError] = useState('');


  event.preventDefault()

  const BASE_URL = 'https://i8a107.p.ssafy.io'

  const dispatch = useDispatch()
  const navigate = useNavigate()
  // const refreshToken = getRefreshTokenFromCookie()
  const accessToken = useSelector(
    (state) => state.accessTokenReducer.accessToken
  )

  try {
    const response = await axios.post(
      `${BASE_URL}/api/user/logout`,
      {},
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    )

    removeToken()
    dispatch(REMOVE_TOKEN())
    dispatch(REMOVE_USER_INFO())
    
  } catch (e) {
    console.log(e)
  }
}

export default Logout
