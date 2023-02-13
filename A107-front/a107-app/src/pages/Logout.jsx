import { removeToken } from '../modules/Auth/Jwt'
import { REMOVE_TOKEN } from '../containers/JwtContainer'
import { REMOVE_USER_INFO } from '../containers/UserInfoContainer'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate } from 'react-router'
import axios from 'axios'
import { useEffect } from 'react'

// const BASE_URL = 'http://localhost:8080'

// test@gmail.com
// 1234

const Logout = () => {
  const BASE_URL = 'https://i8a107.p.ssafy.io'

  const dispatch = useDispatch()
  const navigate = useNavigate()

  const accessToken = useSelector(
    (state) => state.accessTokenReducer.accessToken
  )

  useEffect(() => {
    logout()
  }, [])

  const logout = async () => {
    console.log(accessToken)
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

      navigate('/home')
    } catch (e) {
      removeToken()
      dispatch(REMOVE_TOKEN())
      dispatch(REMOVE_USER_INFO())

      navigate('/home')
      console.log(e)
    }
  }

  return (
    <div>
      {/* <button type="button" onClick={logout}>
        Logout
      </button> */}
    </div>
  )
}

export default Logout
