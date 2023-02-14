import { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { useSelector } from 'react-redux'
import axios from 'axios'
import { SiRocketdotchat } from 'react-icons/si'
import SignalList from '../components/SignalComponents/SignalList'
import SignalSelected from '../components/SignalComponents/SignalSelected'

const Signal = () => {
  const tmpChatRoomSeq = useParams().tmpChatRoomSeq
  
  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)
  const [chatRoom, setChatRoom] = useState([])
  // const [chatList, setChatList] = useState([])

  useEffect( () => {
    axios.get('https://i8a107.p.ssafy.io/api/chatroom/'+ userSeq).then((response)=>{
      setChatRoom(response.data)
      console.log(response.data)
    })
  }, [])

  // useEffect( () => {
  //   axios.get('https://i8a107.p.ssafy.io/api/chat/'+ tmpChatRoomSeq).then((response)=>{
  //     setChatList(response.data)
  //     console.log(response.data)
  //   })
  // }, [])

  const windowStyle = {
    boxSizing: 'border-box',
    display: 'flex',
    flexDirection: 'row',
    padding: '40px',
    gap: '20px',
    justifyContent: 'center',
    alignItems: 'flex-start',
    position: 'absolute',
    width: '1600px',
    height: '800px',
    background:
      'linear-gradient(180deg, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 100%)',
    border: '2px solid #FFFFFF',
    filter:
      'drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25)) drop-shadow(0px 0px 5px rgba(0, 0, 0, 0.25))',
    borderRadius: '50px',
  }
  const asideStyle = {
    display: 'flex',
    width: '35%',
    flexDirection: 'column',
    padding: '10px',
    gap: '20px',
    justifyContent: 'center',
    alignItems: 'center',
  }

  const iconStyle = {
    color: 'white',
    width: '60px',
    height: '60px',
  }

  return (
    <div style={{
      position: 'relative',
      left: '140px',
      margin: '20px',
    }}>
      <div className="window" style={windowStyle}>
        <div className="aside" style={asideStyle}>
          <SiRocketdotchat style={iconStyle} />
          <h1>Signal</h1>
          <div
            style={{
              display: 'flex',
              flexDirection: 'column',
              gap: '20px',
              width: '100%',
              height: '500px',
              overflowY: 'scroll',
            }}
          >
            {chatRoom.length ? 
              (chatRoom.map((chat) => (
                <SignalList userSeq={userSeq} chat={chat} key={chat.seq} />
              ))
             ) : (
              <h2>주고받은 시그널이 없습니다.</h2>
            )}
          </div>
        </div>
        <div style={{ width: '65%', alignSelf: 'center' }}>
          {tmpChatRoomSeq ? (
            <SignalSelected userSeq={userSeq} chatRoomSeq={tmpChatRoomSeq} />
          ) : (
            <h1>메시지를 선택하세요.</h1>
          )}
        </div>
      </div>
    </div>
  )
}

export default Signal
