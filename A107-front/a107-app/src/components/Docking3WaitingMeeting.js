import { useRef, useState, useEffect, useCallback } from 'react'
import { useSelector } from 'react-redux'
import * as StompJs from '@stomp/stompjs'
import axios from 'axios'
import { useNavigate, useLocation } from 'react-router-dom'
import { OpenVidu } from 'openvidu-browser'
import { FilteredVideo } from '../components/DockingComponents/FilteredVideo'
import * as tf from '@tensorflow/tfjs'
import '../Styles/Docking3ing.css'
import { getHeader } from '../modules/Auth/Jwt'
import Game from './DockingComponents/Game'
import Docking3Chat from './DockingComponents/Docking3Chat'

const Docking3WaitingMeeting = ({ multiMeetingRoomSeq }) => {
  const APPLICATION_SERVER_URL = 'https://i8a107.p.ssafy.io'
  // process.env.NODE_ENV === 'production'
  //   ? 'https://i8a107.p.ssafy.io'
  //   : 'http://localhost:8080'

  const WEBSOCKET_SERVER_URL = 'wss://i8a107.p.ssafy.io'
  // process.env.NODE_ENV === 'production'
  //   ? 'wss://i8a107.p.ssafy .io'
  //   : 'ws://localhost:8080'

  const navigate = useNavigate()
  const CLOUD_FRONT_URL = 'https://d156wamfkmlo3m.cloudfront.net/'

  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)
  const userGender = useSelector((state) => state.userInfoReducer.userGender)
  const userName = useSelector((state) => state.userInfoReducer.userName)

  // 웹소켓 관련 기능
  const client = useRef({}) //Client.current에 StompJs객체 저장
  const [chatList, setChatList] = useState([])

  //openvidu관련
  const [session, setSession] = useState() //OpenVidu 세션
  const [token, setToken] = useState('') //OpenVidu토큰
  const [subscribers, setSubscribers] = useState([])
  const [publisher, setPublisher] = useState()

  //6명이 다 모였으면 completeFlag === true
  const [completeFlag, setCompleteFlag] = useState(false)

  //게임 관련
  const [gameFlag, setGameFlag] = useState(false)
  const [gameType, setGameType] = useState()
  const [gameCount, setGameCount] = useState(0)

  const [br31MyTurnFlag, setBr31MyTurnFlag] = useState(false)
  const [br31Point, setBr31Point] = useState(0)

  const [gameofdeathBody, setGameofdeathBody] = useState(false)

  //마스크 씌우기
  const [myMaskPath, setMyMaskPath] = useState(
    CLOUD_FRONT_URL + 'images/glass-1-mask-1.png'
  )

  const maskPath = CLOUD_FRONT_URL + 'images/glass-1-mask-1.png'
  const maskPathMask = CLOUD_FRONT_URL + 'images/glass-0-mask-1.png'
  const maskPathNone = CLOUD_FRONT_URL + 'images/glass-0-mask-0.png'

  let abortController = null

  useEffect(() => {
    window.history.pushState(null, null, window.location.pathname)
    window.addEventListener('popstate', onMove)
    window.addEventListener('beforeunload', onMove)
    abortController = new AbortController()

    connect().then(() => {
      //1. 웹소켓 열기
      alert('connect완료')
      axios //2. 서버로 미팅방에 들어가기 요청(웹소켓으로 들어왔다는 공지 날아옴)
        .post(
          `${APPLICATION_SERVER_URL}/api/multi-meetings/${multiMeetingRoomSeq}/${userSeq}`
        )
        .then((response) => {
          alert('서버에 추가완료')
          console.log(response.data)
          const openVidu = new OpenVidu() //3. openvidu 초기화
          let session = openVidu.initSession()
          // On every new Stream received...
          session.on('streamCreated', (event) => {
            //4. streamCreate이벤트 리스너: subscribers useState에 구독자들 등록한다
            const subscriber = session.subscribe(event.stream, '')
            const data = JSON.parse(event.stream.connection.data)
            setSubscribers((prev) => {
              return [
                ...prev.filter((it) => it.userSeq !== data.userSeq),
                {
                  streamManager: subscriber,
                  userSeq: data.userSeq, //다음과 같이 subscrber에 userSeq, gender가 포함되어 있다
                  userGender: data.userGender,
                  userName: data.userName,
                  maskPath: data.maskPath,
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

          getDocking3Token(userSeq).then((data) => {
            //5. 서버에 요청보내 토큰 받아온다
            session //6. 토큰을 가지고 세션에 연결한다
              .connect(
                data.token,
                JSON.stringify({ userSeq, userGender, userName, maskPath })
              )
              .then(async () => {
                alert('토큰으로 세션에 연결완료')
                await navigator.mediaDevices.getUserMedia({
                  audio: true,
                  video: true,
                })
                const devices = await openVidu.getDevices()
                const videoDevices = devices.filter(
                  (device) => device.kind === 'videoinput'
                )

                const publisher = openVidu.initPublisher('', {
                  //7. publisher 초기화를 하고
                  audioSource: undefined,
                  videoSource: videoDevices[0].deviceId,
                  publishAudio: true,
                  publishVideo: true,
                  resolution: '640x480',
                  frameRate: 30,
                  insertMode: 'APPEND',
                  mirror: false,
                })
                session.publish(publisher) //8. 세션에 publisher등록한다.
                setPublisher(publisher)
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
    return () => {
      disconnect()
      window.removeEventListener('popstate', onMove)
      window.removeEventListener('unload', onMove)
    }
  }, [])

  // connect: 웹소켓(stomp)연결
  const connect = async () => {
    //connect함수 정의: client.current에 StompJs객체
    // subscribe: 메세지 받을 주소 구독
    const subscribeMulti = () => {
      //subscribe함수 정의
      client.current.subscribe(
        //웹소켓 구독주소 등록, 해당주소로 받을시 발생하는 이벤트 등록
        '/sub/multi/' + multiMeetingRoomSeq,
        (body) => {
          const json_body = JSON.parse(body.body)
          console.log(json_body)

          const type = json_body.flag //JOIN, CHAT, EXIT, SYSTEM, GAME

          // 받아온 채팅 채팅 리스트에 넣기 (이부분은 임시로 한 거고 이후 프론트에서 필요에 따라 받아온 메서지를 렌더링 하면 됩니다.)
          setChatList((_chat_list) => [
            {
              flag: json_body.flag,
              message: json_body.message,
              createdAt: json_body.createdAt,
              senderSeq: json_body.senderSeq,
            },
            ..._chat_list,
          ])

          if (type === 'JOIN') {
            var maleNum = json_body.maleNum
            var femaleNum = json_body.femaleNum
            console.log('malenum: ' + maleNum)
            console.log('femalenum: ' + femaleNum)
            if (maleNum >= 1 && femaleNum >= 1) {
              setCompleteFlag(true)
            }
          }
          ///////////////////////////////////////GAME: START, GAME, FIN
          else if (type === 'START') {
            setGameType(json_body.gameType)
            setGameCount((prev) => prev + 1)
            if (gameFlag === false) {
              setGameFlag(true)
            }
          } else if (type === 'END') {
            if(json_body.gameType === 'BR31'){
              changeLoseUserMaskPath(json_body.nextUser)
            }
            else{
              changeLoseUserMaskPath(json_body.loseUserSeq)
            }
          }
          if (type === 'GAME' || type === 'START') {
            if (json_body.gameType === 'BR31') {
              if (json_body.nextUser == userSeq) {
                setBr31MyTurnFlag(true)
                setBr31Point(json_body.point)
              }
            } else if (json_body.gameType === 'GAMEOFDEATH') {
              setGameofdeathBody(json_body)
            } else if (json_body.gameType === 'FASTCLICK') {
            }
          }
          if (type === 'FIN') {
            if (json_body.gameType === 'BR31') {
              changeLoseUserMaskPath(json_body.nextUser)
            } else if (json_body.gameType === 'GAMEOFDEATH') {
              setGameofdeathBody(json_body)
              changeLoseUserMaskPath(json_body.loseUserSeq)
            } else if (json_body.gameType === 'FASTCLICK') {
              changeLoseUserMaskPath(json_body.loseUserSeq)
            }
          }
        }
      )
    }

    client.current = new StompJs.Client({
      //웹소켓 열기
      brokerURL: `${WEBSOCKET_SERVER_URL}/ws-stomp`,

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
    }) //client.current에 new StompJs.Client객체 생성 완료

    // client 객체 활성화
    await client.current.activate()
  } //end of connect            //하고 활성화

  const getDocking3Token = async (userSeq) => {
    const response = await axios.post(
      `${APPLICATION_SERVER_URL}/api/multi-meetings/join`,
      { userSeq: userSeq, multiMeetingRoomSeq: multiMeetingRoomSeq },
      {}
    )
    setToken(response.data.token)
    return response.data
  }

  //뒤로가기 버튼 처리
  const onMove = (e) => {
    e.preventDefault()
    e.returnValue = ''
    navigate('/rendezboo')
  }

  const disconnect = useCallback(async () => {
    alert('웹소켓 디액티브, openvidu세션 disconnect,방에서 나간다.')
    client.current.deactivate()
    if (session) {
      session.disconnect()
    }
    await axios.delete(
      `${APPLICATION_SERVER_URL}/api/multi-meetings/${multiMeetingRoomSeq}/${userSeq}`
    )
    setSession(null)
    setPublisher(null)
    setSubscribers([])
    abortController.abort()
  }, [session])

  //미팅방 기능: openvidu, filtered-video...
  useEffect(() => {
    tf.env().set('WEBGL_CPU_FORWARD', false)
  }, [completeFlag])

  const changeLoseUserMaskPath = useCallback(
    (loseUserSeq) => {
      if (loseUserSeq == userSeq) {
        if (myMaskPath == maskPath) {
          setMyMaskPath(maskPathMask)
        } else if (myMaskPath == maskPathMask) {
          setMyMaskPath(maskPathNone)
        }
      } else {
        setSubscribers((_subscribers) =>
          _subscribers.map((sub) => {
            if (sub.userSeq == loseUserSeq) {
              if (sub.maskPath == maskPath) {
                sub.maskPath = maskPathMask
              } else if (sub.maskPath == maskPathMask) {
                sub.maskPath = maskPathNone
              }
              return sub
            } else {
              return sub
            }
          })
        )
      }
      setTimeout(() => {
        setGameType(null)
        setGameFlag(false)
      }, 3000)
    },
    [myMaskPath]
  )

  const onLoveStickStart = (e) => {}

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
                  maskPath={sub.maskPath}
                  // userSeq={2}
                  startFaceAPI={() => {}}
                />
                <div style={{ color: '#FFFFFF' }}>이름 : {sub.userName}</div>
              </div>
            ))}

            <div className="cam">
              {publisher !== undefined ? (
                <FilteredVideo
                  streamManager={publisher}
                  maskPath={myMaskPath}
                  // userSeq={userSeq}
                  startFaceAPI={() => {}}
                />
              ) : null}
            </div>
            {/* pub-container, 밑에: video-container*/}
          </div>
        ) : null}
        <div className="chat-multi">
          <Docking3Chat
            client={client}
            multiMeetingRoomSeq={multiMeetingRoomSeq}
            userSeq={userSeq}
            chatList={chatList}
          />
        </div>
      </div>
      {/*main */}
      <div className="side-multi">
        {/*게임 모달*/}
        {gameCount > 3 ? (
          <div>
            <button onClick={onLoveStickStart}>사랑의 작대기</button>
          </div>
        ) : null}
        {gameFlag ? (
          <div className={'game-btn-containter'}>게임 진행중입니다.</div>
        ) : (
          <div className={'game-btn-containter'}>
            <button
              className="game-start-btn"
              onClick={() => {
                setGameFlag(true)
              }}
            >
              게임하기
            </button>
          </div>
        )}
        <div className="btn-group-d3"></div>
        {gameFlag ? (
          <Game
            client={client}
            setGameFlag={setGameFlag}
            gameType={gameType}
            setGameType={setGameType}
            subscribers={subscribers}
            multiMeetingRoomSeq={multiMeetingRoomSeq}
            br31MyTurnFlag={br31MyTurnFlag}
            setBr31MyTurnFlag={setBr31MyTurnFlag}
            br31Point={br31Point}
            gameofdeathBody={gameofdeathBody}
          />
        ) : null}
      </div>
    </div>
  ) : (
    <div className="docking-modal" style={{ padding: '30px' }}>
      <h1>Now Docking...</h1>
      <p style={{ fontSize: '1.5rem' }}>3:3 미팅 대기중입니다.</p>
      <Docking3Chat
        client={client}
        multiMeetingRoomSeq={multiMeetingRoomSeq}
        userSeq={userSeq}
        chatList={chatList}
      />
    </div>
  )
}
export default Docking3WaitingMeeting
