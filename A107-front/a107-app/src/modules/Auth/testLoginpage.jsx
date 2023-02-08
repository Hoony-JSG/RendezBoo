import {setRefreshToken, getRefreshTokenFromCookie, getAccessToken, getHeader, removeToken, reissueAccessToken} from "./Jwt";
import React, { useState } from 'react'
import axios from "axios";

const BASE_URL = 'https://i8a107.p.ssafy.io'
// const BASE_URL = 'http://localhost:8080'

// test@gmail.com
// 1234

const LoginTest = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const login = async (event) => {
    event.preventDefault();
    console.log(`${BASE_URL}/api/user/login`)

    try {
      const response = await axios.post(`${BASE_URL}/api/user/login`, {
        email,
        password,
      });

      setIsLoggedIn(true);
      console.log(response.data);
      // console.log(response.data.refreshToken)
      setRefreshToken(response.data.refreshToken)
      console.log(getRefreshTokenFromCookie())
      console.lot(reissueAccessToken())
    } catch (error) {
      setError(error.message);
    }
  }

  const logout = () => {
    removeToken()
    setIsLoggedIn(false);
  };

  return (
    <div>
      {isLoggedIn ? (
        <button type="button" onClick={logout}>
          Logout
        </button>
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
  );
};

export default LoginTest;