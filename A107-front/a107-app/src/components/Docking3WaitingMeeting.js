import { useRef, useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import * as StompJs from '@stomp/stompjs'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import { OpenVidu } from 'openvidu-browser'
import { FilteredVideo } from '../components/DockingComponents/FilteredVideo'
import * as tf from '@tensorflow/tfjs'
import '../Styles/Docking3ing.css'
import { getHeader } from '../modules/Auth/Jwt'
import Game from './DockingComponents/Game'
import DockingChatSelectedItem from './DockingComponents/DockingChatSelectedItem'
import { SiRocketdotchat } from 'react-icons/si'

const Docking3WaitingMeeting = ({ multiMeetingRoomSeq }) => {
  const APPLICATION_SERVER_URL = 'https://i8a107.p.ssafy.io/'
  // process.env.NODE_ENV === 'production'
  //   ? 'https://i8a107.p.ssafy.io'
  //   : 'http://localhost:8080'

  const WEBSOCKET_SERVER_URL = 'ws://i8a107.p.ssafy.io/'
  // process.env.NODE_ENV === 'production'
  //   ? 'wss://i8a107.p.ssafy.io'
  //   : 'ws://localhost:8080'

  const navigate = useNavigate()
  const CLOUD_FRONT_URL = 'https://d156wamfkmlo3m.cloudfront.net/'

  //gotFirstWebSocketMessageFlag
  const gotFirstWebSocketMessageFlag = useRef(false)

  // 웹소켓 관련 기능
  const client = useRef({})
  const [chatList, setChatList] = useState([])
  const [message, setMessage] = useState('')

  //6명이 다 모였으면 completeFlag === true
  const [completeFlag, setCompleteFlag] = useState(false)

  //게임 관련
  const [gameFlag, setGameFlag] = useState(false)
  const [gameType, setGameType] = useState()

  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)

  //openvidu관련
  const [subscribers, setSubscribers] = useState([])
  const [publisher, setPublisher] = useState()
  const [session, setSession] = useState()
  const [token, setToken] = useState('')

  //마스크 씌우기
  const [maskPath, setMaskPath] = useState(
    CLOUD_FRONT_URL + 'images/glass-1-mask-1.png'
  )

  useEffect(() => {
    //////////////////////////////////////////////////////////////////////////////////////
    //대기방에서 필요한 일이다. 웹소켓 연결, 구독...
    // connect: 웹소켓(stomp)연결
    const connect = async () => {
      // subscribe: 메세지 받을 주소 구독
      const subscribeMulti = async () => {
        // 구독한 주소로 메세지 받을 시 이벤트 발생
        // (/sub: 웹소켓 공통 구독 주소), (/chat: 기능별(1:1, 3:3, 친구 추가후) 구독 주소), (/chatRoomSeq: 하위 구독 주소(채팅방))
        await client.current.subscribe(
          '/sub/multi/' + multiMeetingRoomSeq,
          (body) => {
            // 받아온 제이슨 파싱
            const json_body = JSON.parse(body.body)

            console.log('메세지 받았당') // 확인용 출력 (이처럼 메세지 수령 시 특정 이벤트를 발생 시킬 수 있습니다.)
            console.log(json_body)

            const type = json_body.flag //JOIN, CHAT, EXIT, SYSTEM, GAME

            // 받아온 채팅 채팅 리스트에 넣기 (이부분은 임시로 한 거고 이후 프론트에서 필요에 따라 받아온 메서지를 렌더링 하면 됩니다.)
            setChatList((_chat_list) => [
              // {
              //   message: json_body.message,
              //   createdAt: json_body.createdAt,
              //   senderSeq: json_body.senderSeq,
              // },
              ..._chat_list,
                json_body.message,
                json_body.createdAt,
                json_body.senderSeq,

            ])

            if (type === 'JOIN') {
              var maleNum = json_body.maleNum
              var femaleNum = json_body.femaleNum
              console.log('malenum: ' + maleNum)
              console.log('femalenum: ' + femaleNum)
              if (maleNum === 3 && femaleNum === 3) {
                setCompleteFlag(true)
              }
            }
            ///////////////////////////////////////GAME: START, GAME, FIN
            if (type === 'START') {
              setGameType(json_body.gameType)
              if (gameFlag === false) {
                setGameFlag(true)
              }
            }
          }
        )
      }

      client.current = new StompJs.Client({
        brokerURL: WEBSOCKET_SERVER_URL + 'ws-stomp', // 연결할 url(이후에 localhost는 배포 도메인으로 바꿔주세요)

        // 연결 확인용 출력 문구
        debug: function (str) {
          console.log(str)
        },

        // 에러 발생 시 재연결 시도 딜레이
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,

        // 연결 시
        onConnect: async () => {
          console.log('success')
          await subscribeMulti() // 메세지(채팅)을 받을 주소를 구독합니다.
        },

        // 에러 발생 시 로그 출력
        onStompError: (frame) => {
          console.log(frame)
        },
      })

      // client 객체 활성화
      await client.current.activate()
      // alert("connect()가 되었다")
    }

    connect().then(() => {
      // alert('나를 이 미팅방-유저 테이블에 추가하고 웹소켓으로 보낸다.')
      axios
        .post(
          APPLICATION_SERVER_URL +
            'api/multi-meetings/' +
            multiMeetingRoomSeq +
            '/' +
            userSeq
        )
        .then((response) => {
          console.log(response.data)
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

          // On every Stream destroyed...
          session.on('streamDestroyed', (event) => {
            event.preventDefault()

            const data = JSON.parse(event.stream.connection.data)
            setSubscribers((prev) =>
              prev.filter((it) => it.userSeq !== +data.userSeq)
            )
          })

          // On every asynchronous exception...
          session.on('exception', (exception) => {
            console.warn(exception)
          })

          // 위에서 주입받은 토큰 사용 하여 세션에 연결
          getDocking3Token(userSeq).then((data) => {
            session
              .connect(data.token, JSON.stringify({ clientData: userSeq }))
              .then(async () => {
                await navigator.mediaDevices.getUserMedia({
                  audio: true,
                  video: true,
                })
                const devices = await openVidu.getDevices()
                const videoDevices = devices.filter(
                  (device) => device.kind === 'videoinput'
                )

                const publisher = openVidu.initPublisher('', {
                  audioSource: undefined,
                  videoSource: videoDevices[0].deviceId,
                  publishAudio: true,
                  publishVideo: true,
                  resolution: '640x480',
                  frameRate: 30,
                  insertMode: 'APPEND',
                  mirror: false,
                })

                setPublisher(publisher)
                session.publish(publisher)
              })
              .catch((error) => {
                console.log(
                  'There was an error connecting to the session:',
                  error.code,
                  error.message
                )
              })
          })
          setSession(session)
        })
        .catch((e) => {
          console.log(e.message)
          navigate('/docking3')
        })
    })
    return () => disconnect()
  }, [])

  //뒤로가기 버튼 처리
  const onBackButtonEvent = (e) => {
    e.preventDefault()
    navigate('/docking3')
  }
  useEffect(() => {
    window.history.pushState(null, null, window.location.pathname)
    window.addEventListener('popstate', onBackButtonEvent)
    return () => {
      window.removeEventListener('popstate', onBackButtonEvent)
    }
  }, [])

  // publish: 메세지 보내기
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

  const disconnect = () => {
    console.log('연결이 끊어졌습니다.')
    client.current.deactivate()
    if (session) {
      session.disconnect()
    }
    axios.delete(
      APPLICATION_SERVER_URL +
        'api/multi-meetings/' +
        multiMeetingRoomSeq +
        '/' +
        userSeq
    )
    setSession(null)
    setPublisher(null)
    setSubscribers([])
  }

  const inputChat = (e) => {
    setMessage(e.target.value)
  }

  const sendChat = (e, message) => {
    e.preventDefault()
    if (message.trim()) publish(message)
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////
  //미팅방 기능: openvidu, filtered-video...
  useEffect(() => {
    //미팅이 시작될 때 일어나야 하는 일들이 일어난다.
    tf.env().set('WEBGL_CPU_FORWARD', false)
  }, [completeFlag])

  // userSeq 기반으로 오픈비두 토큰 가져옴
  async function getDocking3Token(userSeq) {
    const response = await axios.post(
      APPLICATION_SERVER_URL + 'api/multi-meetings/join',
      { userSeq: userSeq, multiMeetingRoomSeq: multiMeetingRoomSeq },
      {}
    )
    setToken(response.data.token)
    // alert('Get Token!!!' + token)
    return response.data
  }

  return completeFlag ? (
    <div
      style={{
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        overflowX: 'scroll',
        padding: '20px',
      }}
    >
      <div className="main-multi">
        {session !== undefined ? (
          <div className="cam-group">
            {subscribers.map((sub, idx) => (
              <div key={idx} className="cam">
                <FilteredVideo
                  streamManager={sub.streamManager}
                  maskPath={maskPath}
                  // userSeq={2}
                  startFaceAPI={() => {}}
                />
              </div>
            ))}

            <div className="cam">
              {publisher !== undefined ? (
                <FilteredVideo
                  streamManager={publisher}
                  maskPath={maskPath}
                  // userSeq={userSeq}
                  startFaceAPI={() => {}}
                />
              ) : null}
            </div>
            {/* pub-container, 밑에: video-container*/}
          </div>
        ) : null}
        <div className="chat-multi">
          {/* <Docking3Chat
              multiMeetingRoomSeq={multiMeetingRoomSeq}
              userSeq={userSeq}
            /> */}
          {chatList.map((chat, index) => {
            console.log(chat)
            ;<DockingChatSelectedItem chat={chat} key={index} />
          })}
          <form
            className={'signal-form'}
            onSubmit={(e) => sendChat(e, message)}
          >
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
      </div>
      {/*main */}
      <div className="side-multi">
        {/*게임 모달*/}
        {gameFlag ? (
          <Game className={'game'} gameType={gameType} />
        ) : (
          <div
            className="game"
            onclick={() => {
              setGameFlag(true)
            }}
          >
            게임하기
          </div>
        )}
        <div className="btn-group-d3"></div>
      </div>
    </div>
  ) : (
    <div className="chat-multi">
      <h1>단체 미팅방 {multiMeetingRoomSeq}의 대기방입니다.</h1>
      <p>내 유저 시퀀스는 {userSeq}입니다.</p>
      <div>
        {chatList.map((item, index) => {
          return <div key={index}>{item}</div>
        })}
      </div>
      <form onSubmit={(event) => sendChat(event, message)}>
        <div>
          <input
            type={'text'}
            name={'chatInput'}
            onChange={inputChat}
            value={message}
          />
        </div>
        <input type={'submit'} value={'메세지 보내기'} />
      </form>
    </div>
  )
}
export default Docking3WaitingMeeting
