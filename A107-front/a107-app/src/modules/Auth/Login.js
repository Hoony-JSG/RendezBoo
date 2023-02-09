import {setRefreshToken, removeToken} from "./Jwt";
import React, { useState } from 'react'
import { SET_TOKEN, REMOVE_TOKEN } from "../../containers/JwtContainer";
// import { SET_USER_INFO, REMOVE_USER_INFO } from "../../containers/UserInfo";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import jwtDecode from "jwt-decode";

const BASE_URL = 'https://i8a107.p.ssafy.io'
// const BASE_URL = 'http://localhost:8080'

// test@gmail.com
// 1234

// 안 쓰는 페이지임 나중에 지울 거!!!



const Login = (event) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');


  const dispatch = useDispatch()
  // const selector = useSelector

  console.log(useSelector(state => state.accessTokenReducer.accessToken))


  const login = async (event) => {
    event.preventDefault();
    // console.log(`${BASE_URL}/api/user/login`)

    try {
      const response = await axios.post(`${BASE_URL}/api/user/login`, {
        email,
        password,
      });

      setIsLoggedIn(true);
      console.log(response.data);
      // console.log(response.data.refreshToken)
      setRefreshToken(response.data.refreshToken)

      // console.log(response.data.accessToken)
      
      // console.log(response.data.accessToken)
      console.log(jwtDecode(response.data.accessToken))

      // const decode = jwtDecode(response.data.accessToken)
      // dispatch(SET_USER_INFO(decode))

      dispatch(SET_TOKEN(response.data.accessToken))

      // console.log(getRefreshTokenFromCookie())
      // console.lot(reissueAccessToken())
    } catch (error) {
      setError(error.message);
    }
  }
}

export default Login