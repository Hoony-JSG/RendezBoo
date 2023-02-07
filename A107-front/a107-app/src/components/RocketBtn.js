import RocketBtn_Diff from './RocketBtn_Diff'
import RocketBtn_Same from './RocketBtn_Same'
import RocketBtn_Start from './RocketBtn_Start'

const RocketBtn = (props) => {
  if (props.ver === 'Me') {
    return (
      <div>
        헉 같다
        <RocketBtn_Same />
      </div>
    )
  } else if (props.ver === 'Other') {
    return (
      <div>
        다르다
        <RocketBtn_Diff />
      </div>
    )
  } else {
    return (
      <div>
        <RocketBtn_Start />
      </div>
    )
  }
}

export default RocketBtn
