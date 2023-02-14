import axios from 'axios'
import { useLocation, useParams } from 'react-router-dom'

const NaverOauth = () => {
  const location = useLocation()
  const searchParams = new URLSearchParams(location.search)
  const code = searchParams.get('code')
  const state = searchParams.get('state')
  axios
    .get('https://i8a107.p.ssafy.io/api/oauth/naver' + location.search)
    .then((res) => {
      console.log(res)
    })
  console.log('location:', location.hash)
  console.log('code:', code)
  console.log('state:', state)
  return (
    <div>
      <div>
        <span> 네이버 로그인 됐스</span>
      </div>
    </div>
  )
}

export default NaverOauth
