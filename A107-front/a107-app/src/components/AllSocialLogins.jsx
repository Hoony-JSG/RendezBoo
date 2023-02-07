import { useEffect } from 'react'
import { useLocation } from 'react-router-dom'
import NaverLogin from './NaverLogin'

const AllSocialLogins = (props) => {
  return (
    <div>
      <NaverLogin ver={props.ver} />
      {/* <KakaoLogin /> */}
      {/* <NewNaverLogin /> */}
    </div>
  )
}

export default AllSocialLogins
