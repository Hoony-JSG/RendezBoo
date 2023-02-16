import { useState } from 'react'
import { SiRocketdotchat } from 'react-icons/si'
import '../../Styles/SignalSelected.css'
import DockingChatSelectedItem from './DockingChatSelectedItem'

const Docking3Chat = ({ client, multiMeetingRoomSeq, userSeq, chatList }) => {
  const [message, setMessage] = useState('')
  //채팅 입력
  const inputChat = (e) => {
    setMessage(e.target.value)
  }
  //채팅 보내기(publish함수 이용)
  const sendChat = (e, message) => {
    e.preventDefault()
    if (message.trim()) publish(message)
  }
  // publish: 웹소켓으로 메세지 보내기
  const publish = (message) => {
    // 연결이 안되어있을 경우
    if (!client.current.connected) {
      alert('연결 상태를 확인해주세요.')
      return
    }

    // 메세지를 보내기
    client.current.publish({
      // destination: 보낼 주소
      // (/pub: 웹소켓 공통 발신용 주소), (/send: 기능별 개별 발신용 주소)
      destination: '/pub/send-multi',

      // body: 보낼 메세지
      body: JSON.stringify({
        multiMeetingRoomSeq: multiMeetingRoomSeq,
        message: message,
        senderSeq: userSeq,
      }),
    })

    // 보내고 메세지 초기화
    setMessage('')
  }

  return (
    <div className={'signal-container'} style={{ height: '300px' }}>
      <div className={'signal-selected'}>
        {chatList &&
          chatList.map((chat, index) => (
            <DockingChatSelectedItem chat={chat} key={index} />
          ))}
      </div>
      <form className={'signal-form'} onSubmit={(e) => sendChat(e, message)}>
        <input
          type={'text'}
          name={'chatInput'}
          placeholder={'메시지를 입력하세요'}
          onChange={inputChat}
          value={message}
        />
        <button type="submit">
          <SiRocketdotchat />
        </button>
      </form>
    </div>
  )
}

export default Docking3Chat
