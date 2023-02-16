import { useCallback, useEffect, useState } from 'react'
import { useSelector } from 'react-redux'
import axios from 'axios'
import { OpenVidu } from 'openvidu-browser'
import * as faceapi from 'face-api.js'
import * as tf from '@tensorflow/tfjs'
import { FilteredVideo } from '../components/DockingComponents/FilteredVideo'
import Docking1Chat from '../components/DockingComponents/Docking1Chat'
import Docking3enter from '../components/DockingComponents/Docking3enter'
import '../Styles/Docking1.css'
import { useMemo } from 'react'
import { useParams, useNavigate } from 'react-router'
import { getHeader } from '../modules/Auth/Jwt'
import {
  BsCameraVideoOff,
  BsCameraVideo,
  BsMic,
  BsMicMute,
} from 'react-icons/bs'
import { ImEnter, ImExit } from 'react-icons/im'
import { FaHeart, FaHeartBroken } from 'react-icons/fa'

const APPLICATION_SERVER_URL = 'https://i8a107.p.ssafy.io/'

const CLOUD_FRONT_URL = 'https://d156wamfkmlo3m.cloudfront.net/'

const Docking3copy = () => {
  const minute = 30000
  const REQUEST_HEADER = getHeader()

  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)
  const userGender = useSelector((state) => state.userInfoReducer.userGender)
  const navigate = useNavigate()

  const multiMeetingRoomSeq = useParams().roomid
  const [docking3List, setDocking3List] = useState([])

  const [myUserName, setMyUserName] = useState(Math.floor(Math.random() * 100))
  const [token, setToken] = useState('')
  const [meetingRoomSeq, setMeetingRoomSeq] = useState(-1)
  const [subscribers, setSubscribers] = useState([])
  const [publisher, setPublisher] = useState()
  const [session, setSession] = useState()
  const [audioStatus, setAudioStatus] = useState(true)
  const [videoStatus, setVideoStatus] = useState(true)

  const [phase, setPhase] = useState(-1)

 
  const [apiStarted, setApiStarted] = useState(false)

  const [finished, setFinished] = useState(false)

  const [maskPath, setMaskPath] = useState(
    CLOUD_FRONT_URL + 'images/glass-1-mask-1.png'
  )

  // tf 세팅 및 모델 불러오기
  useEffect(() => {
    tf.env().set('WEBGL_CPU_FORWARD', false)
    loadModels()

    axios
    .get(`${APPLICATION_SERVER_URL}/api/multi-meetings/`)
    .then((response) => {
      setDocking3List(response.data)
      console.log(response.data)
    })
  }, [])

  // faceapi 모델 불러오기
  async function loadModels() {
    const MODEL_URL = 'https://d156wamfkmlo3m.cloudfront.net/models'
    await faceapi.nets.tinyFaceDetector.loadFromUri(MODEL_URL)
    await faceapi.nets.ssdMobilenetv1.loadFromUri(MODEL_URL)
    console.log('tinyFaceDetector loaded')
    await faceapi.nets.faceExpressionNet.loadFromUri(MODEL_URL)
    console.log('faceEx loaded')
  }

  // 세션 나가기
  const leaveSession = useCallback(async () => {
    if (session) {
      session.disconnect()
    }
    const response = await axios.delete(
      APPLICATION_SERVER_URL + 'api/onetoone/' + meetingRoomSeq,
      {},
      REQUEST_HEADER
    )
    console.log(response.status)
    setSession(null)
    setPublisher(null)
    setSubscribers([])
  }, [session, meetingRoomSeq])

  const leaveSessionWithAlert = () => {
    if (!finished) {
      setFinished(true)
      axios
        .delete(
          APPLICATION_SERVER_URL + 'api/onetoone/' + meetingRoomSeq,
          {},
          REQUEST_HEADER
        )
        .then(() => {
          window.location.href = '/'
        })
        .catch(() => {
          window.location.href = '/'
        })
    }
  }
  // 마운트 시 창 종료하면 세션 나가게 훅 걸기
  useEffect(() => {
    window.addEventListener('beforeunload', () => leaveSession())
    return () => {
      window.removeEventListener('beforeunload', () => leaveSession())
    }
  }, [leaveSession])

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
          ...prev.filter((it) => it.userSeq !== data.userSeq),
          {
            streamManager: subscriber,
            userSeq: data.userSeq,
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
    getDocking1Token(userSeq).then((data) => {
      session
        .connect(data.token, JSON.stringify({ clientData: myUserName }))
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
  }

  // 카메라 상태
  const onChangeCameraStatus = useCallback(() => {
    publisher.publishVideo(!videoStatus)
    setVideoStatus((prev) => !prev)
  }, [publisher, videoStatus])

  // 마이크 상태
  const onChangeMicStatus = useCallback(() => {
    publisher.publishAudio(!audioStatus)
    setAudioStatus((prev) => !prev)
  }, [publisher, audioStatus])

  // 감정 분석 시작
  async function startFaceAPI(videoEl) {
    if (!apiStarted) {
      // 시작되었는지의 플래그
      setApiStarted(true)
      console.log('video loaded')
    }
  }

  // userSeq 기반으로 오픈비두 토큰 가져옴
  async function getDocking1Token(userSeq) {
    const response = await axios.post(
      APPLICATION_SERVER_URL + 'api/onetoone',
      { userSeq: userSeq },
      REQUEST_HEADER
    )

    setMeetingRoomSeq(response.data.meetingRoomSeq)
    setToken(response.data.token)
    console.log('Get Token!!! ' + token)
    if (response.status !== 200) {
      alert('매칭 실패!')
    }
    return response.data
  }

  useEffect(() => {
    if (phase < 0 && subscribers.length > 0) {
      setPhase(0)
      if (userGender) {
        setTimeout(() => {
          axios.post(
            APPLICATION_SERVER_URL +
              'api/onetoone/one/' +
              meetingRoomSeq +
              '/start',
            {},
            REQUEST_HEADER
          )
        }, 10000)
      }
    }
  }, [subscribers])

  async function handleSystem(json_body) {}
  async function handleExit(json_body) {
    // flag EXIT 이면 내보내기
    setFinished(true)
    if (!finished) {
      leaveSessionWithAlert()
    }
  }
  async function handlePhase1(json_body) {
    // 미팅 시작 - 타이머 돌릴것
    if (phase < 1) {
      setPhase(1)
      if (userGender) {
        setTimeout(() => {
          axios.post(
            APPLICATION_SERVER_URL +
              'api/onetoone/one/' +
              meetingRoomSeq +
              '/phase2',
            {},
            REQUEST_HEADER
          )
        }, minute)
      }
    }
  }
  async function handlePhase2(json_body) {
    // 선글라스 벗기고 타이머 돌릴것
    if (phase < 2) {
      setPhase(2)
      setMaskPath(CLOUD_FRONT_URL + 'images/glass-0-mask-1.png')
      if (userGender) {
        setTimeout(() => {
          axios.post(
            APPLICATION_SERVER_URL +
              'api/onetoone/one/' +
              meetingRoomSeq +
              '/phase3',
            {},
            REQUEST_HEADER
          )
        }, minute)
      }
    }
  }
  async function handlePhase3(json_body) {
    // 마스크도 벗기고 타이머 돌릴것
    if (phase < 3) {
      setPhase(3)
      setMaskPath(CLOUD_FRONT_URL + 'images/glass-0-mask-0.png')
      if (userGender) {
        setTimeout(() => {
          axios.post(
            APPLICATION_SERVER_URL +
              'api/onetoone/one/' +
              meetingRoomSeq +
              '/final',
            {},
            REQUEST_HEADER
          )
        }, minute)
      }
    }
  }
  const handleFinal = useCallback(async () => {
    if (phase < 4) {
      setPhase(4)
    }
    // 최종선택 - 모달창 등을 띄울것
  }, [phase])

  async function choiceYes() {
    setPhase(5)
    await axios.post(
      APPLICATION_SERVER_URL + 'api/emotion/',
      REQUEST_HEADER
    )
    await axios.post(
      APPLICATION_SERVER_URL + 'api/badges/onetoone',
      REQUEST_HEADER
    )
    await axios
      .post(
        APPLICATION_SERVER_URL + 'api/onetoone/one/choice',
        {
          meetingRoomSeq: meetingRoomSeq,
          userSeq: userSeq,
          wantDocking: true,
        },
        REQUEST_HEADER
      )
      .then((res) => {
        console.log(res)
      })
  }

  async function choiceNo() {
    setPhase(5)


    await axios.post(
      APPLICATION_SERVER_URL + 'api/emotion/',
      REQUEST_HEADER
    )
    await axios.post(
      APPLICATION_SERVER_URL + 'api/badges/onetoone',
      REQUEST_HEADER
    )
    await axios
      .post(
        APPLICATION_SERVER_URL + 'api/onetoone/one/choice',
        {
          meetingRoomSeq: meetingRoomSeq,
          userSeq: userSeq,
          wantDocking: false,
        },
        REQUEST_HEADER
      )
      .then((res) => {
        console.log(res)
      })
  }

  const videoBackStyle = useMemo(() => {
    return {
      width: '100%',
      height: '100%',
      top: 0,
      left: 0,
      position: 'absolute',
      zIndex: 16,
      display: phase >= 1 ? 'none' : 'box',
      backgroundColor: 'rgb(23, 49, 71)',
      color: 'rgb(123,159,181)',
      textAlign: 'center',
      paddingTop: '45%',
      fontSize: '2rem',
    }
  }, [phase])

  return (
    <div>
      {multiMeetingRoomSeq ? (
        <div className="docking1-modal">
          <h1>1:1 Docking...</h1>
          <p style={{ fontSize: '1.5rem' }}>1:1 미팅에 입장하시겠습니까?</p>
          <div style={{ display: 'flex', flexDirection: 'row', marginTop: '60px' }}>
            <button className="ready-enter-btn" onClick={joinSession}>
              <ImEnter />
              &nbsp;입장하기
            </button>
            <button className="ready-exit-btn" onClick={() => {navigate('/rendezboo')}}>
              <ImExit />
              &nbsp;돌아가기
            </button>
          </div>
        </div>
      ) : null}
      {session !== undefined && !finished ? (
        <div className="video-container">
          <div className="sub-container">
            <div className="video-back" style={videoBackStyle}>
              <h1>Waiting for Docking...</h1>
            </div>
            {subscribers.map((sub, idx) => (
              <div key={idx} id="subscriber">
                <FilteredVideo
                  streamManager={sub.streamManager}
                  maskPath={maskPath}
                  userSeq={sub.userSeq}
                  startFaceAPI={startFaceAPI}
                />
              </div>
            ))}
          </div>
          <div className="pub-container">
            {publisher !== undefined ? (
              <div className="me">
                <FilteredVideo
                  streamManager={publisher}
                  maskPath={maskPath}
                  // userSeq={userSeq}
                  startFaceAPI={() => {}}
                />
                <div className="btn-group">
                  <button
                    className="btn-group-btn"
                    onClick={onChangeCameraStatus}
                  >
                    {videoStatus ? <BsCameraVideo /> : <BsCameraVideoOff />}
                  </button>
                  <button className="btn-group-btn" onClick={onChangeMicStatus}>
                    {audioStatus ? <BsMic /> : <BsMicMute />}
                  </button>
                  <button
                    className="btn-group-btn-exit"
                    onClick={leaveSessionWithAlert}
                  >
                    <ImExit style={{position: 'relative', top: '5px', left: '4px'}}/>
                  </button>
                </div>
              </div>
            ) : (
              <div className="me">
              </div>
            )}
            <Docking1Chat
              meetingRoomSeq={meetingRoomSeq}
              userSeq={userSeq}
              handleSystem={handleSystem}
              handleExit={handleExit}
              handlePhase1={handlePhase1}
              handlePhase2={handlePhase2}
              handlePhase3={handlePhase3}
              handleFinal={handleFinal}
            />
          </div>
        </div>
      ) : (
        <Docking3enter />
      )}
      {finished ? (
        // 미팅 종료 문구
        <h1 style={{marginTop: '300px'}}>Docking Ended!</h1>
      ) : null}
      {phase === 4 && !finished ? (
        // 친구 추가 모달
        <div
          className="docking1-modal"
        >
          <h1>Docking Complete!</h1>
          <p style={{ fontSize: '1.5rem' }}>상대방과 친구를 맺으시겠습니까?</p>
          <div
            style={{ display: 'flex', flexDirection: 'row', marginTop: '60px' }}
          >
            <button className="ready-exit-btn" onClick={choiceYes}>
              <FaHeart style={{fontSize: '3rem'}}/>
            </button>
            <button className="ready-enter-btn" onClick={choiceNo}>
              <FaHeartBroken style={{fontSize: '3rem'}}/>
            </button>
          </div>
        </div>
      ) : null}
    </div>
  )
}

export default Docking3copy