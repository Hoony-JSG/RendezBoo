import SignalListItem from './SignalListItem'
import { SiRocketdotchat } from 'react-icons/si'

const SignalList = ({ chatRoom }) => {
  const iconStyle = {
    position: 'inherit',
    color: 'white',
    width: '60px',
    height: '60px',
  }
  return (
    <div>
      {/* <h2>SignalList</h2> */}
      <SiRocketdotchat style={iconStyle} />
      <h1>Signal</h1>
      <div
        className="SignalList"
        style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}
      >
        {chatRoom.map((chat) => (
          <SignalListItem chat={chat} key={chat.chatRoomSeq} />
        ))}
      </div>
    </div>
  )
}

export default SignalList
