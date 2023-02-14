import { useEffect, useState } from 'react'
import '../../Styles/JoinItemSocialEmailStyle.css'
const JoinItemSocialEmail = (props) => {
  const [email, setEmail] = useState()
  useEffect(() => {
    setEmail(props.email)
  }, [])
  return (
    <div className="JoinEmail_container">
      <div className="JoinEmail_box-discribe">
        이메일
        <br />
        {email}
      </div>
      <div className="JoinEmail_box-email"></div>
    </div>
  )
}
export default JoinItemSocialEmail
