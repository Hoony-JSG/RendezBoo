import { useState, useEffect, useRef } from 'react'
import { useNavigate } from 'react-router-dom'
import axios from 'axios'
import * as StompJs from '@stomp/stompjs'
import SignalSelectedItem from './SignalSelectedItem'
import userLogo from '../../Images/user-profile.png'
import { SiRocketdotchat } from 'react-icons/si'
import '../../Styles/SignalSelected.css'

const SignalSelected = ({ userSeq, chatRoomSeq }) => {
  const client = useRef({})

  const [chatList, setChatList] = useState([])
  const [message, setMessage] = useState('')
  const [you, setYou] = useState({
    yourSeq: 0,
    yourname: '',
    yourpImg: '',
  })

  const navigate = useNavigate()

  useEffect(() => {
    axios
      .get('https://i8a107.p.ssafy.io/api/chat/' + chatRoomSeq)
      .then((response) => {
        setChatList(response.data)
        return response.data[0]
      })
      .then((recentChat) => {
        if (recentChat.senderSeq == userSeq) {
          axios
            .get('https://i8a107.p.ssafy.io/api/user/' + recentChat.receiverSeq)
            .then((response) => {
              setYou({
                yourSeq: response.data.seq,
                yourname: response.data.name,
                yourpImg: response.data.profileImagePath,
              })
            })
        } else {
          axios
            .get('https://i8a107.p.ssafy.io/api/user/' + recentChat.senderSeq)
            .then((response) => {
              setYou({
                yourSeq: response.data.seq,
                yourname: response.data.name,
                yourpImg: response.data.profileImagePath,
              })
            })
        }
      })
  }, [chatRoomSeq])

  const connect = () => {
    // stomp js client 객체 생성
    client.current = new StompJs.Client({
      brokerURL: 'wss://i8a107.p.ssafy.io/ws-stomp',

      // 연결 확인용 출력 문구
      debug: function (str) {
        console.log(str)
      },

      // 에러 발생 시 재연결 시도 딜레이
      reconnectDelay: 3000,
      heartbeatIncoming: 2000,
      heartbeatOutgoing: 2000,

      // 연결 시
      onConnect: () => {
        console.log('success')
        if (chatRoomSeq > 0) {
          subscribe() // 메세지(채팅)을 받을 주소를 구독합니다.
        }
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
      const json_body = JSON.parse(body.body)

      setChatList((_chat_list) => [
        {
          createdAt: json_body.createdAt,
          message: json_body.message,
          receiverSeq: json_body.receiverSeq,
          senderSeq: json_body.senderSeq,
        },
        ..._chat_list,
      ])
    })
  }

  // publish: 메세지 보내기
  const publish = (signal) => {
    // 연결이 안되어있을 경우
    if (!client.current.connected) {
      alert('연결 상태를 확인해주세요.')
      return
    }

    let body = JSON.stringify({
      message: signal,
      chatRoomSeq: chatRoomSeq,
      senderSeq: userSeq,
      receiverSeq: you.yourSeq,
    })

    // 메세지를 보내기
    client.current.publish({
      // destination: 보낼 주소
      destination: '/pub/send',
      // body: 보낼 메세지
      body: body,
    })

    // 보내고 메세지 초기화
    setMessage('')
  }

  // disconnect: 웹소켓 연결 끊기
  const disconnect = () => {
    console.log('연결이 끊어졌습니다.')
    client.current.deactivate()
  }

  const inputSignal = (e) => {
    setMessage(e.target.value)
  }
  const sendSignal = (e) => {
    e.preventDefault()
    if (message.trim()) publish(message)
  }

  useEffect(() => {
    connect()
    return () => disconnect()
  }, [chatRoomSeq])

  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'space-between',
        gap: '20px',
      }}
    >
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
          src={userLogo}
          onClick={() => navigate(`/rocket/${you.yourSeq}`)}
          style={{ width: '75px', height: '75px' }}
          alt={userSeq}
        />
        <h1>{you.yourname}</h1>
      </div>
      <div className="signal-selected" style={{ height: '500px' }}>
        {chatList.map((chat, index) => (
          <SignalSelectedItem
            chat={chat}
            key={index}
            userSeq={userSeq}
            you={you}
          />
        ))}
      </div>
      <form onSubmit={sendSignal} className={'signal-form'}>
        <input
          placeholder={'메시지를 입력하세요'}
          value={message}
          onChange={inputSignal}
        />
        <button type={'submit'}>
          <SiRocketdotchat />
        </button>
      </form>
    </div>
  )
}

export default SignalSelected
