import RocketBtn_Diff from './RocketBtn_Diff'
import RocketBtn_Same from './RocketBtn_Same'
import MBTISelector from '../JoinComponents/MBTISelector'
import '../../Styles/RocketBtnStyle.css'
const RocketBtn = (props) => {
  if (props.ver === 'Me') {
    return <RocketBtn_Same rocketUser={props.rocketUser} />
  } else if (props.ver === 'Other') {
    return <RocketBtn_Diff {...props} />
  } else if (props.ver === 'Start') {
    return (
      <div className="RocketBtn_MBTI-container">
        <MBTISelector mbti={props.mbti} />
      </div>
    )
  } else {
    return <div></div>
  }
}

export default RocketBtn
