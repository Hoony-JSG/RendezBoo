import { useState, useEffect } from 'react'
import axios from 'axios'
import SignalForm from './SignalForm'
import SignalSelectedItem from './SignalSelectedItem'
import userLogo from '../../Images/user-profile.png'


const SignalSelected = ({ userSeq, roomSeq }) => {
  const [chatList, setChatList] = useState([])

  useEffect(() => {
    axios
      .get('https://i8a107.p.ssafy.io/api/chat/' + userSeq)
      .then((response) => {
        console.log(response.data)
        setChatList(response.data.filter((chat) => chat.chatRoomSeq == roomSeq))
      })
  }, [])


  return (
    <div>
        <div
            style={{
                textAlign: 'left',
                display: 'flex',
                flexDirection: 'row',
                alignItems: 'center',
                justifyContent: 'flex-start',
                gap: '20px',
            }}
        >
            <img
              src={userSeq.profileImagePath || userLogo}  
              style={{ width: '75px', height: '75px' }}
              alt={userSeq}
            />
            <h1>SignalSelected</h1>
        </div>
      <div
        style={{
            display: 'flex',
            height: '540px',
            overflowY: 'scroll',
            flexDirection: 'column',
        }}
      >
        {chatList.map((chat) => (
          <SignalSelectedItem chat={chat} key={chat.seq} />
        ))}
      </div>
      <SignalForm />
    </div>
  )
}

export default SignalSelected
