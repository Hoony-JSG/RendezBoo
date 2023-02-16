import { useSelector } from 'react-redux'

const GameBr31 = (props) => {
  const {
    client,
    multiMeetingRoomSeq,
    br31MyTurnFlag,
    setBr31MyTurnFlag,
    br31Point,
  } = props
  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)
  const pubBr31Point = (num) => {
    if (!client || !client.current.connected) {
      alert('연결 상태를 확인해주세요.')
      return
    }
    client.current.publish({
      destination: '/pub/br31',
      body: JSON.stringify({
        multiMeetingRoomSeq: multiMeetingRoomSeq,
        userSeq: userSeq,
        point: num,
      }),
    })
    setBr31MyTurnFlag(false)
  }

  return (
    <div>
      {br31MyTurnFlag ? (
        <div>
          내차례가 되면 나오는 베스킨라빈스 버튼
          <button value={1} onClick={(e) => pubBr31Point(e.target.value)}>
            1
          </button>
          <button value={2} onClick={(e) => pubBr31Point(e.target.value)}>
            2
          </button>
          <button value={3} onClick={(e) => pubBr31Point(e.target.value)}>
            3
          </button>
        </div>
      ) : null}
      <div>현재 번호: {br31Point}</div>
    </div>
  )
}
export default GameBr31
