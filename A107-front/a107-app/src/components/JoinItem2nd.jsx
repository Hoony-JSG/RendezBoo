import '../Styles/temp_border_style.css'
import JoinItemBirth from './JoinItemBirth'
import JoinItemName from './JoinItemName'
import JoinItemPhoneNumber from './JoinItemPhoneNumber'
import JoinItemGender from './JoinItemGender'

const JoinItem2nd = () => {
  return (
    <div>
      J_2_휴대폰 인증 및 정보 가져오기
      <div>
        <div className="temp_border_style">
          <JoinItemName />
        </div>
        <div className="temp_border_style">
          <JoinItemGender />
        </div>
        <div className="temp_border_style">
          <JoinItemBirth />
        </div>
        <div className="temp_border_style">
          <JoinItemPhoneNumber />
        </div>
      </div>
    </div>
  )
}

export default JoinItem2nd
