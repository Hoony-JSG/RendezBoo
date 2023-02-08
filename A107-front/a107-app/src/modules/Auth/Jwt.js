import axios from "axios";
import { Cookies } from "react-cookie";

// responseData = {
//   grantType: "Bearer ",
//   accessToekn: "dfdfdfdfdfdf",
//   refreshToken: "dfdfdfdfdfdf"
// }



// refresh token / access token 유효시간 입니다.
const REFRESH_TOKEN_AGE = 604800   // 1 week
const ACCESS_TOKEN_AGE = 1800      // 30 min

// 새로운 쿠키 객체 생성
const cookies = new Cookies()


// setRefreshToken: 발급 받은 Refresh token 쿠키에 저장
export const setRefreshToken = (refreshToken) => {

  cookies.set(
    'refresh_token', // 이름
    refreshToken, // 값
    
    // 보안 관련 설정 (에러 시 조정)
    /**
     * path: 모든 url에 대해 쿠키 전송 허용
     * sameSite: 모든 도메인에서 쿠키에 접근 가능
     * httpOnly: xss(자바스크립트를 통한 쿠키 탈취) 방지 옵션인데 도메인에서만 적용 가능(ip에서는 불가)
     * sercure: https 통신만 허용 (sameSite: none 설정 위해 필요)
     * maxAge: 쿠키 만료 기한 (일주일)
     */
    {
      path: "/",
      sameSite: 'none',
      // httpOnly: true,
      secure: true,
      maxAge: REFRESH_TOKEN_AGE
    }
  )
}


// getRefreshTokenFromCookie: 쿠키에 저장되어있는 refresh token 가져오기
export const getRefreshTokenFromCookie = () => {
  return cookies.get('refresh_token')
}


// reissueAccessToken: Refresh token을 이용해 access token 재발급(reissue) 요청
export const reissueAccessToken = async () => {

  // 쿠키 가져오기
  const refreshToken = getRefreshTokenFromCookie()

  // 쿠키가 없을 경우
  if (!refreshToken) {
    console.log("토큰이 없습니다") // 이후 적절히 예외처리 해주세요
    return
  }

  // access 토큰 발급 axios 요청
  try {
    const response = await axios.post(
      'https://i8a107.p.ssafy.io/api/user/reissue',
      {},
      // 헤더 부착
      {
        headers: {
          Authorization: `Bearer ${refreshToken}`
        }
      }
    )
    
    // 갱신 된 refresh token 저장
    setRefreshToken(response.data.refreshToken)

    // access 토큰 반환
    return response.data.getAccessToken

  } catch (e) {
    console.log(e)
  }
}


// removeToken: (로그아웃 시) 쿠키에 저장된 토큰 제거
export const removeToken = () => {
  cookies.remove('refresh_token',
  {
    path: "/",
    sameSite: 'none',
    // httpOnly: true,
    secure: true,
    maxAge: REFRESH_TOKEN_AGE
  })
}


// getHeader: 헤더에 토큰 부착해주기
export const getHeader = (accessToekn) => {
  return {
    Authorization: `Bearer ${accessToekn}`
  }
}