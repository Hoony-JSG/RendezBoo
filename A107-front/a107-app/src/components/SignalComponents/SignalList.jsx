import { useNavigate } from 'react-router-dom'
import userLogo from '../../Images/user-profile.png'

const SignalList = ({ userSeq, chat }) => {
  const { userFemale, userMale } = chat
  const navigate = useNavigate()

  const chatListStyle = {
    width: '100%',
    height: '120px',
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
    gap: '20px',
    boxSizing: 'border-box',
    padding: '30px',
    border: '2px solid #FFFFFF',
    borderRadius: '30px',
    background:
    'linear-gradient(180deg, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 100%)',
    filter:
      'drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25)) drop-shadow(0px 0px 5px rgba(0, 0, 0, 0.25))',
  }
  return (
    <div onClick={() => navigate(`/signal/${chat.seq}`)}>
      {userFemale.seq !== userSeq ? (
        <div style={chatListStyle}>
          <img
            src={userFemale.profileImagePath || userLogo}
            style={{ width: '75px', height: '75px' }}
            alt={userFemale.name}
          />
          <h3>{userFemale.name}</h3>
        </div>
      ) : (
        <div style={chatListStyle}>
          <img
            src={userMale.profileImagePath || userLogo}
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
