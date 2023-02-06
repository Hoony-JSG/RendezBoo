import {React, useRef, useState, useEffect} from 'react'
import { useParams } from 'react-router-dom'
import * as StompJs from '@stomp/stompjs'

const WebSocketChatTest = () => {
  const client = useRef({})

  const [chatList, setChatList] = useState([])
  const [message, setMessage] = useState('')

  // 임시로 설정해둠
  const chatRoomSeq = 1
  const senderSeq = 1
  const receiverSeq = 2

  useEffect(() => {
    connect()

    return () => disconnect()
  }, [])

  // 웹소켓(stomp) 연결
  const connect = () => {
    client.current = new StompJs.Client({
      brokerURL: 'ws://localhost:8080/ws-stomp', // 연결할 url(이후에 localhost는 배포 도메인으로 바꿔주세요)

      debug: function (str) {
        console.log(str)
      },

      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,

      // 연결 시
      onConnect: () => {
        console.log('success')
        subscribe() // 메세지(채팅)을 받을 주소를 구독합니다.
      },

      onStompError: (frame) => {
        console.log(frame)
      },
    })

    client.current.activate()
  }

  const subscribe = () => {
    client.current.subscribe('/sub/chat/' + chatRoomSeq, (body) => {
      const json_body = JSON.parse(body.body)
      console.log("메세지 받았당")
      console.log(body.body)
      setChatList((_chat_list) => [..._chat_list, json_body])
    })
  }

  const publish = (message) => {
    if (!client.current.connected) return

    // 메세지를 보낼 주소입니다.
    client.current.publish({
      destination: '/pub/send',
      body: JSON.stringify({
        message: message,
        chatRoomSeq: chatRoomSeq,
        senderSeq: senderSeq,
        receiverSeq: receiverSeq,
      }),
    })

    setMessage('')
  }

  const disconnect = () => {
    console.log("연결이 끊어졌습니다")
    client.current.deactivate()
  }

  const handleChange = (event) => {
    // 채팅 입력 시 state에 값 설정
    setMessage(event.target.value)
  }

  const handleSubmit = (event, message) => {
    // 보내기 버튼 눌렀을 때 publish
    event.preventDefault()

    publish(message)
  }

  return (
    <div>
      <div className={'chat-list'}>{chatList}</div>
      <form onSubmit={(event) => handleSubmit(event, message)}>
        <div>
          <input
            type={'text'}
            name={'chatInput'}
            onChange={handleChange}
            value={message}
          />
        </div>
        <input type={'submit'} value={'메세지 보내기'} />
      </form>
    </div>
  )
}

export default WebSocketChatTest
