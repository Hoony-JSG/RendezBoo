import axios from "axios";
import { Cookies } from "react-cookie";

// responseData = {
//   grantType: "Bearer ",
//   accessToekn: "dfdfdfdfdfdf",
//   refreshToken: "dfdfdfdfdfdf"
// }

const REFRESH_TOKEN_AGE = 604800   // 1 week
const ACCESS_TOKEN_AGE = 1800      // 30 min


const cookies = new Cookies()


export const setRefreshToken = (refreshToken) => {
  // console.log(refreshToken)
  cookies.set(
    'refresh_token',
    refreshToken,
    {
      path: "/",
      sameSite: 'none',
      // httpOnly: true,
      secure: true,
      maxAge: REFRESH_TOKEN_AGE
    }
  )
  // console.log(cookies.get(refreshToken.maxAge))
}


export const getRefreshTokenFromCookie = () => {
  return cookies.get('refresh_token')
}


export const getAccessToken = async () => {
  const refreshToken = getRefreshTokenFromCookie()

  if (!refreshToken) {
    console.log("토큰이 없습니다")
    return
  }

  try {
    const response = await axios.post(
      'https:i8a107.p.ssafy.io/api/user/reissue',
      {
        Authorization: `Bearer ${refreshToken}`
      }
    )
    
    setRefreshToken(response.data.refreshToken)
    return response.data.getAccessToken

  } catch (e) {
    console.log(e)
  }
}

export const removeToken = () => {
  console.log('지워')
  cookies.remove('refresh_token',
  {
    path: "/",
    sameSite: 'none',
    // httpOnly: true,
    secure: true,
    maxAge: REFRESH_TOKEN_AGE
  })
}

export const getHeader = (accessToekn) => {
  return {
    Authorization: `Bearer ${accessToekn}`
  }
}