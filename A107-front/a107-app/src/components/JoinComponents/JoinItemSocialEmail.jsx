import { useState } from 'react'
import '../../Styles/JoinItemSocialEmailStyle.css'
const JoinItemSocialEmail = (props) => {
  const [email, setEmail] = useState()
  //   const [type, setType] = useState()
  //   setEmail(props.email)
  //   props.type(type)
  return (
    <div className="JoinEmail_container">
      <div className="JoinEmail_box-discribe">이메일 : {email}</div>
    </div>
  )
}
export default JoinItemSocialEmail
