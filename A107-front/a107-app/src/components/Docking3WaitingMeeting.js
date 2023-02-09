import { React, useRef, useState, useEffect } from "react"
import * as StompJs from '@stomp/stompjs'
import axios from "axios"
import { OpenVidu } from 'openvidu-browser'
import { FilteredVideo } from '../components/DockingComponents/FilteredVideo'
import * as faceapi from 'face-api.js'
import * as tf from '@tensorflow/tfjs'

const Docking3WaitingMeeting = ({multiMeetingRoomSeq}) => {
    const userid = 1
    const usertoken = "$$$mytoken$$$"

    // 임시로 설정해둔 인자 변수 (나중에 프론트에서 넣어주세요)
    const client = useRef({});
    const [chatList, setChatList] = useState([])
    const [message, setMessage] = useState('')
    const [completeFlag, setCompleteFlag] = useState(false);

    const [userSeq, setUserSeq] = useState(-1)
    const [myUserName, setMyUserName] = useState(Math.floor(Math.random() * 100))
    const [token, setToken] = useState('')
    const [subscribers, setSubscribers] = useState([])
    const [publisher, setPublisher] = useState()
    const [session, setSession] = useState()

    const APPLICATION_SERVER_URL =
      process.env.NODE_ENV === 'production' ? '' : 'https://i8a107.p.ssafy.io/'

    useEffect(()=>{
        connect()
        
        return() => disconnect()
    },[])

//////////////////////////////////////////////////////////////////////////////////////
//대기방에서 필요한 일이다. 웹소켓 연결, 구독...
    // connect: 웹소켓(stomp)연결
    const connect = () =>{
      console.log('나를 이 미팅방-유저 테이블에 추가합니다.')
      axios.post("http://localhost:8080/api/multi-meetings/"+multiMeetingRoomSeq+'/'+userid).then((response)=>{
          console.log(response.status)
      })

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
          subscribeMulti() // 메세지(채팅)을 받을 주소를 구독합니다.
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
  const subscribeMulti = () => {

    // 구독한 주소로 메세지 받을 시 이벤트 발생
    // (/sub: 웹소켓 공통 구독 주소), (/chat: 기능별(1:1, 3:3, 친구 추가후) 구독 주소), (/chatRoomSeq: 하위 구독 주소(채팅방))    
    client.current.subscribe('/sub/multi/'+multiMeetingRoomSeq, (body) => {

      // 받아온 제이슨 파싱
      const json_body = JSON.parse(body.body) 

      console.log('메세지 받았당') // 확인용 출력 (이처럼 메세지 수령 시 특정 이벤트를 발생 시킬 수 있습니다.)
      console.log(json_body)

      const type = json_body.flag//JOIN, CHAT, EXIT, SYSTEM, GAME

      // 받아온 채팅 채팅 리스트에 넣기 (이부분은 임시로 한 거고 이후 프론트에서 필요에 따라 받아온 메서지를 렌더링 하면 됩니다.)
      setChatList((_chat_list) => [
        ..._chat_list,
        json_body.message,
        json_body.createdAt,
      ])
      
      if(type==='JOIN'){
        var maleNum = json_body.maleNum
        var femaleNum = json_body.femaleNum
        console.log('malenum: '+maleNum)
        console.log('femalenum: '+femaleNum)
        if(maleNum+femaleNum==6){
          setCompleteFlag(true)
        }
      }

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
  
  const disconnect = () => {
    console.log('disconnect(): 대기방 연결을 해제합니다.')
    client.current.deactivate()
    console.log('나를 이 미팅방-유저 테이블에서 삭제합니다.')
    axios.delete("http://localhost:8080/api/multi-meetings/"+multiMeetingRoomSeq+'/'+userid).then((response)=>{
        console.log(response.status)
    })
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

/////////////////////////////////////////////////////////////////////////////////////////////////
//미팅방 기능: openvidu, filtered-video...
    useEffect(()=>{
    //미팅이 시작될 때 일어나야 하는 일들이 일어난다.
      tf.env().set('WEBGL_CPU_FORWARD', false)
    }, [completeFlag])

  // 세션 시작
  const joinSession = () => {
    const openVidu = new OpenVidu()
    let session = openVidu.initSession()

    // On every new Stream received...
    session.on('streamCreated', (event) => {
      const subscriber = session.subscribe(event.stream, '')
      const data = JSON.parse(event.stream.connection.data)
      setSubscribers((prev) => {
        return [
          ...prev.filter((it) => it.userSeq !== +data.userSeq),
          {
            streamManager: subscriber,
            userSeq: +data.userSeq,
            gender: data.gender,
          },
        ]
      })
    })
  }



  return(
    completeFlag?(
        <div>
          <h1>미팅 진행중입니다</h1>
        </div>
    ):(
        <div>
          <h1>단체 미팅방 {multiMeetingRoomSeq}의 대기방입니다.</h1>
          <p>내 유저 시퀀스는 {userid}입니다.</p>
          <p>내 토큰은 {usertoken}입니다.</p>
          <p>내 화면</p>
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
    
  )

}
export default Docking3WaitingMeeting;
