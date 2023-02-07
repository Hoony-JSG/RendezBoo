import { React, useRef, useState, useEffect } from 'react'
import * as StompJs from '@stomp/stompjs'

const WebSocketChatTest = () => {
  const client = useRef({})

  const [chatList, setChatList] = useState([])
  const [message, setMessage] = useState('')

  // 임시로 설정해둔 인자 변수 (나중에 프론트에서 넣어주세요)
  const chatRoomSeq = 1
  const senderSeq = 1
  const receiverSeq = 2

  useEffect(() => {
    connect()

    return () => disconnect()
  }, [])

  // connect: 웹소켓(stomp) 연결 
  const connect = () => {

    // stomp js client 객체 생성
    client.current = new StompJs.Client({

      brokerURL: 'ws://localhost:8080/ws-stomp', // 연결할 url(이후에 localhost는 배포 도메인으로 바꿔주세요)

      // 연결 확인용 출력 문구
      debug: function (str) {
        console.log(str)
      },

      // 에러 발생 시 재연결 시도 딜레이
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,

      // 연결 시
      onConnect: () => {
        console.log('success')
        subscribe() // 메세지(채팅)을 받을 주소를 구독합니다.
      },

      // 에러 발생 시 로그 출력
      onStompError: (frame) => {
        console.log(frame)
      },
    })

    // client 객체 활성화
    client.current.activate()
  }

  // subscribe: 메세지 받을 주소 구독
  const subscribe = () => {

    // 구독한 주소로 메세지 받을 시 이벤트 발생
    // (/sub: 웹소켓 공통 구독 주소), (/chat: 기능별(1:1, 3:3, 친구 추가후) 구독 주소), (/chatRoomSeq: 하위 구독 주소(채팅방))    
    client.current.subscribe('/sub/chat/' + chatRoomSeq, (body) => {

      // 받아온 제이슨 파싱
      const json_body = JSON.parse(body.body) 

      console.log('메세지 받았당') // 확인용 출력 (이처럼 메세지 수령 시 특정 이벤트를 발생 시킬 수 있습니다.)
      console.log(body.body)
      
      // 받아온 채팅 채팅 리스트에 넣기 (이부분은 임시로 한 거고 이후 프론트에서 필요에 따라 받아온 메서지를 렌더링 하면 됩니다.)
      setChatList((_chat_list) => [
        ..._chat_list,
        json_body.senderSeq,
        json_body.message,
        json_body.createdAt,
      ])
    })
  }


  // publish: 메세지 보내기
  const publish = (message) => {

    // 연결이 안되어있을 경우
    if (!client.current.connected) {
      alert('연결이 안 되어있어')
      return
    }

    // 입력된 메세지가 없는 경우
    if (!message) {
      alert('메세지 입력 해')
      return
    }

    // 메세지를 보내기
    client.current.publish({
      
      // destination: 보낼 주소
      // (/pub: 웹소켓 공통 발신용 주소), (/send: 기능별 개별 발신용 주소)
      destination: '/pub/send',

      // body: 보낼 메세지
      body: JSON.stringify({
        message: message,
        chatRoomSeq: chatRoomSeq,
        senderSeq: senderSeq,
        receiverSeq: receiverSeq,
      }),
    })

    // 보내고 메세지 초기화
    setMessage('')
  }

  // disconnect: 웹소켓 연결 끊기
  const disconnect = () => {
    console.log('연결이 끊어졌습니다')
    client.current.deactivate()
  }


  // handleChage: 채팅 입력 시 state에 값 설정
  const handleChange = (event) => {
    setMessage(event.target.value)
  }


  // handleSubmit: 보내기 버튼 눌렀을 때 보내기(publish 실행)
  const handleSubmit = (event, message) => {
    event.preventDefault()

    publish(message)
  }

  // 아래는 테스트를 위해 임시로 작성한 템플릿으로 이후에 필요에 따라 템플릿을 작성해주세요
  return (
    <div>
      <div className={'chat-list'}>
        {chatList.map((item, index) => {
          return <div key={index}>{item}</div>
        })}
      </div>
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