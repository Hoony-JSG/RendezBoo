import { useNavigate } from 'react-router-dom'
import userLogo from '../../Images/user-profile.png'

const SignalList = ({ userSeq, chat }) => {
  const { userFemale, userMale } = chat
  const navigate = useNavigate()
  const goToPage = (props) => {
    navigate('/' + props)
  }
  const chatListStyle = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
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
        { userFemale.seq !== userSeq ? (
          <div>
            <img
              src={userFemale.profileImagePath || {userLogo}}  
              style={{ width: '75px', height: '75px' }}
              alt={userFemale.name}
            />
            <h3>{userFemale.name}</h3>
          </div>
        ) : (
          <div>
            <img
              src={userMale.profileImagePath || {userLogo}}  
              style={{ width: '75px', height: '75px' }}
              alt={userMale.name}
            />
            <h3>{userMale.name}</h3>
          </div>
        )}
    </div>
  )
}

export default SignalList
