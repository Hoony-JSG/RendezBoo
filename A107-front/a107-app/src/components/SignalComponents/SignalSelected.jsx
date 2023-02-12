import { useState, useEffect, useRef } from 'react'
import { useSelector } from 'react-redux'
import axios from 'axios'
import * as StompJs from '@stomp/stompjs'
// import SignalForm from './SignalForm'
import SignalSelectedItem from './SignalSelectedItem'
import userLogo from '../../Images/user-profile.png'
import { SiRocketdotchat } from 'react-icons/si'
import '../../Styles/SignalSelected.css'


const SignalSelected = ({
  userSeq, 
  roomSeq,
  meetingRoomSeq,
  handleSystem,
  handleExit,
  handlePhase1,
  handlePhase2,
  handlePhase3,
  handleFinal,
}) => {
  const client = useRef({})

  const me = useSelector((state) => state.userInfoReducer.userSeq)

  const [signal, setSignal] = useState('')
  const [chatList, setChatList] = useState([])
  const [you, setYou] = useState({
    yourname: '',
    yourpImg: '',
  })

  useEffect(() => {
    // const getChatList = async () => {
    //   const chats = await axios.get(
    //     'https://i8a107.p.ssafy.io/api/chat/' + userSeq
    //   )
    //   setChatList(chats.data.filter((chat) => chat.chatRoomSeq == roomSeq))
    // }
    // getChatList()
    axios
      .get('https://i8a107.p.ssafy.io/api/chat/' + 1)
      .then((response) => {
        console.log(response.data)
        setChatList(response.data.filter((chat) => chat.chatRoomSeq == roomSeq))
      })

    .then(() => {
    // axios
    //   .get('https://i8a107.p.ssafy.io/api/chat/' + userSeq)
    //   .then((response) => {
    //     console.log(response.data)
    //     setChatList(response.data.filter((chat) => chat.chatRoomSeq == roomSeq))
        if (chatList[0].senderSeq == me) {
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
        if (meetingRoomSeq > 0) {
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
    client.current.subscribe('/sub/one/' + meetingRoomSeq, (body) => {
      // 받아온 제이슨 파싱
      const json_body = JSON.parse(body.body)
      const flag = json_body.flag
      console.log(json_body)

      if (json_body.flag === 'CHAT') {
        setChatList((_chat_list) => [
          ..._chat_list,
          json_body.senderSeq,
          json_body.message,
          json_body.createdAt,
        ])
      } else if (flag === 'SYSTEM') {
        console.log(json_body.message)
        handleSystem(json_body)
      } else if (flag === 'EXIT') {
        console.log(json_body.message)
        handleExit(json_body)
      } else if (flag === 'PHASE1') {
        console.log(json_body.message)
        handlePhase1(json_body)
      } else if (flag === 'PHASE2') {
        console.log(json_body.message)
        handlePhase2(json_body)
      } else if (flag === 'PHASE3') {
        console.log(json_body.message)
        handlePhase3(json_body)
      } else if (flag === 'FINAL') {
        console.log(json_body.message)
        handleFinal(json_body)
      }
    })
  }

  // publish: 메세지 보내기
  const publish = (message) => {
    // 연결이 안되어있을 경우
    if (!client.current.connected) {
      alert('연결 상태를 확인해주세요.')
      return
    }

    // // 입력된 메세지가 없는 경우
    // if (!message) {
    //   alert('메세지 입력 해')
    //   return
    // }

    let body = JSON.stringify({
      message: message,
      meetingRoomSeq: meetingRoomSeq,
      userSeq: userSeq,
    })
    console.log(body)

    // 메세지를 보내기
    client.current.publish({
      // destination: 보낼 주소
      destination: '/pub/one/chat',
      // body: 보낼 메세지
      body: body,
    })

    // 보내고 메세지 초기화
    setSignal('')
  }

  // disconnect: 웹소켓 연결 끊기
  const disconnect = () => {
    console.log('연결이 끊어졌습니다')
    client.current.deactivate()
  }

  const inputSignal = (e) => {
    setSignal(e.target.value)
  }
  const sendSignal = (e) => {
    e.preventDefault()
    if (signal.trim()) publish(signal)
  }

  useEffect(() => {
    connect()

    return () => disconnect()
  }, [meetingRoomSeq])

  
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
      <div className='signal-selected' style={{ height: '500px' }}>
        {chatList.map((chat) => (
          <SignalSelectedItem chat={chat} key={chat.seq} userSeq={userSeq} you={you}/>
        ))}
      </div>
      <form onSubmit={sendSignal} className={"signal-form"}>
      <input
        placeholder={"메시지를 입력하세요"}
        value={signal}
        onChange={inputSignal}
      />
      <button type={"submit"}>
        <SiRocketdotchat />
      </button>
    </form>
    </div>
  )
}

export default SignalSelected
