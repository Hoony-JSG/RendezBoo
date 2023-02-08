import { React, useRef, useState, useEffect } from "react"
import * as StompJs from '@stomp/stompjs'
import axios from "axios"
import { Publisher } from "openvidu-browser"

const Docking3Waiting = ({roomid}) => {
    const userid=1
    const usertoken = "$$$mytoken$$$"

    const client = useRef({});
    const [members, setMembers] = useState([]);
    const [chatList, setChatList] = useState([]);
    const [message, setMessage] = useState('')

    useEffect(()=>{
        connect()
        Publisher(userid)
        return() => disconnect()
    },[])
    // connect: 웹소켓(stomp)연결
    const connect = () =>{
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
    client.current.subscribe('/sub/waiting/', (body) => {

      // 받아온 제이슨 파싱
      const json_body = JSON.parse(body.body) 

      console.log('메세지 받았당') // 확인용 출력 (이처럼 메세지 수령 시 특정 이벤트를 발생 시킬 수 있습니다.)
      console.log(body.body)
      
    })
  }
  
  const handleChange = (event) => {
    setMessage(event.target.value)
  }
  


  const disconnect = () => {
    console.log('disconnect(): 대기방 연결을 해제합니다.')
    client.current.deactive()
    console.log('나를 이 미팅방-유저 테이블에서 삭제합니다.')
    axios.delete("https://i8a107.p.ssafy.io/api/multi-meetings/"+roomid+'/'+userid).then((response)=>{
        console.log(response.status)
    })
  }

  return(
    <div>
        <h1>단체 미팅방 {roomid}의 대기방입니다.</h1>
        <p>내 유저 시퀀스는 {userid}입니다.</p>
        <p>내 토큰은 {usertoken}입니다.</p>
        <p>내 화면</p>
        <p>{message}</p>
    </div>
  )

}
export default Docking3Waiting;
