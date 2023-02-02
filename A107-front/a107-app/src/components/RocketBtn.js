import RocketBtn_Diff from './RocketBtn_Diff'
import RocketBtn_Rendez from './RocketBtn_Rendez'
import RocketBtn_Same from './RocketBtn_Same'

const RocketBtn = (props) => {
  console.log('btn controller')
  console.log(props.Rendez)
  if (props.Rendez) {
    return (
      <div>
        <RocketBtn_Rendez id={props.Me} />
      </div>
    )
  }
  if (props.Same) {
    return (
      <div>
        헉 같다
        <RocketBtn_Same />
      </div>
    )
  } else {
    return (
      <div>
        다르다
        <RocketBtn_Diff />
      </div>
    )
  }
}
export default RocketBtn
