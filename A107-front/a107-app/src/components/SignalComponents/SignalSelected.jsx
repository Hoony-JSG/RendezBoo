import { useState, useEffect } from 'react'
import axios from 'axios'
import SignalForm from './SignalForm'
import SignalSelectedItem from './SignalSelectedItem'
import userLogo from '../../Images/user-profile.png'


const SignalSelected = ({ userSeq, roomSeq }) => {
  const [chatList, setChatList] = useState([])
  const [you, setYou] = useState({
    yourname: '',
    yourpImg: '',
  })

  useEffect(() => {
    const getChatList = async () => {
      const chats = await axios.get(
        'https://i8a107.p.ssafy.io/api/chat/' + userSeq
      )
      setChatList(chats.data.filter((chat) => chat.chatRoomSeq == roomSeq))
    }
    getChatList()
    .then(() => {
    // axios
    //   .get('https://i8a107.p.ssafy.io/api/chat/' + userSeq)
    //   .then((response) => {
    //     console.log(response.data)
    //     setChatList(response.data.filter((chat) => chat.chatRoomSeq == roomSeq))
        if (chatList[0].senderSeq == userSeq) {
          axios.get('https://i8a107.p.ssafy.io/api/user/'+ chatList[0].receiverSeq)
          .then((response)=>{
            console.log(response.data)
            setYou({
              yourname: response.data.name,
              yourpImg: response.data.profileImagePath,
            })
          })
        } else {
          axios.get('https://i8a107.p.ssafy.io/api/user/'+ chatList[0].senderSeq)
          .then((response)=>{
            console.log(response.data)
            setYou({
              yourname: response.data.name,
              yourpImg: response.data.profileImagePath,
            })
          })
        }
      })
  }, [])


  return (
    <div style={{
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'space-between',
      gap: '20px',
    }}>
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
              src={you.yourpImg || userLogo}  
              style={{ width: '75px', height: '75px' }}
              alt={userSeq}
            />
            <h1>{you.yourname}</h1>
        </div>
      <div
        style={{
            display: 'flex',
            width: '100%',
            height: '500px',
            overflowY: 'scroll',
            flexDirection: 'column',
            borderRadius: '40px',
            border: '2px solid #FFFFFF',
            background: 'linear-gradient(180deg, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 100%)',
            filter: 'drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25)) drop-shadow(0px 0px 5px rgba(0, 0, 0, 0.25))',
        }}
      >
        {chatList.map((chat) => (
          <SignalSelectedItem chat={chat} key={chat.seq} userSeq={userSeq} you={you}/>
        ))}
      </div>
      <SignalForm />
    </div>
  )
}

export default SignalSelected
