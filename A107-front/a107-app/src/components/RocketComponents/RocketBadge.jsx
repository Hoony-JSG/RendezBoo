import RocketBadgeList from './RocketBadgeList'
import RocketBadgeRep from './RocketBadgeRep'
const RocketBadge = (props) => {
  const { badgeList } = props

  console.log(props.ver === 'Me' ? '내꺼' : '다른사람꺼')
  const WholeFrame = {
    // border: '1px solid blue',
    padding: '5px',
    // height: '200px',
  }
  const BadgeDes = {
    border: '1px solid black',
    height: props.ver == 'Me' ? '50px' : '100px',
  }
  console.log('뱃지 프레임')
  return (
    <div style={WholeFrame}>
      {/* <RocketBadgeRep  /> */}
      <div>
        <RocketBadgeList badgeList={badgeList} />
      </div>
    </div>
  )
}

export default RocketBadge
