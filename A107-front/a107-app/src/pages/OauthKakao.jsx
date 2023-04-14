import axios from 'axios'
import jwtDecode from 'jwt-decode'
import { useDispatch } from 'react-redux'
import { useLocation, useNavigate } from 'react-router-dom'
import { SET_TOKEN } from '../containers/JwtContainer'
import { SET_USER_INFO } from '../containers/UserInfoContainer'
import { setRefreshToken } from '../modules/Auth/Jwt'

const OauthKakao = (props) => {
  const location = useLocation()
  const navigate = useNavigate()
  const dispatch = useDispatch()

  axios
    .get('https://i8a107.p.ssafy.io/api/oauth/kakao' + location.search)
    .then((res) => {
      console.log(res)
      if (res.status === 200) {
        // 회원가입으로
        const email = res.data.email
        const type = res.data.type
        navigate({ pathname: '/join', search: `?email=${email}&type=${type}` })
      } else if (res.status === 202) {
        // 로그인
        setRefreshToken(res.data.refreshToken)
        const decode = jwtDecode(res.data.accessToken)
        dispatch(SET_USER_INFO(decode))
        dispatch(SET_TOKEN(res.data.accessToken))
        navigate('/')
      }
    })
    .catch((e) => {
      console.log(e)
      navigate('/home')
    })

  return <div></div>
}

export default OauthKakao
