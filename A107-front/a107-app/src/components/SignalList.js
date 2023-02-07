import { useNavigate } from 'react-router-dom'

const SignalList = ({ chat }) => {
  const { userFemaleSeq, content } = chat
  const navigate = useNavigate()
  const goToPage = (props) => {
    navigate('/' + props)
  }
  const chatListStyle = {
    boxSizing: 'border-box',
    width: '100%',
    background:
      'linear-gradient(180deg, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 100%)',
    border: '2px solid #FFFFFF',
    filter:
      'drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25)) drop-shadow(0px 0px 5px rgba(0, 0, 0, 0.25))',
    borderRadius: '30px',
  }
  return (
    <div
      style={chatListStyle}
      onClick={() => goToPage('signal/' + chat.chatRoomSeq)}
    >
      <div>
        <h3>{userFemaleSeq}</h3>
        <p>{content[0].created_at}</p>
      </div>
      <p>{content[0].message}</p>
    </div>
  )
}

export default SignalList
