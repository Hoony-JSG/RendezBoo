import RocketBtn_Diff from './RocketBtn_Diff'
import RocketBtn_Same from './RocketBtn_Same'
import MBTISelector from '../JoinComponents/MBTISelector'

const RocketBtn = (props) => {
  if (props.ver === 'Me') {
    return (
      <div>
        헉 같다
        <RocketBtn_Same {...props} />
      </div>
    )
  } else if (props.ver === 'Other') {
    return (
      <div>
        다르다
        <RocketBtn_Diff {...props} />
      </div>
    )
  } else if (props.ver === 'Start') {
    return (
      <div>
        <MBTISelector mbti={props.mbti} />
      </div>
    )
  } else {
    return <div></div>
  }
}

export default RocketBtn
