import { OpenVidu } from 'openvidu-browser'

import axios from 'axios'
import React, { useCallback, useEffect, useState } from 'react'
import { FilteredVideo } from '../components/DockingComponents/FilteredVideo'
import Docking1Chat from '../components/DockingComponents/Docking1Chat'
import * as faceapi from 'face-api.js'
import * as tf from '@tensorflow/tfjs'
import { EmotionComponent } from '../components/DockingComponents/EmotionComponent'
import '../Styles/Docking1.css'
import { useSelector } from 'react-redux'
import { redirect, useNavigate } from 'react-router-dom'

const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === 'production' ? '' : 'https://i8a107.p.ssafy.io/'

const CLOUD_FRONT_URL = 'https://d156wamfkmlo3m.cloudfront.net/'

const Docking1 = (props) => {
  const navigate = useNavigate()

  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)

  const [myUserName, setMyUserName] = useState(Math.floor(Math.random() * 100))
  const [token, setToken] = useState('')
  const [meetingRoomSeq, setMeetingRoomSeq] = useState(-1)
  const [subscribers, setSubscribers] = useState([])
  const [publisher, setPublisher] = useState()
  const [session, setSession] = useState()

  const [phase, setPhase] = useState(-1)

  const [angryCnt, setAngryCnt] = useState(0)
  const [disgustedCnt, setDisgustedCnt] = useState(0)
  const [fearfulCnt, setFearfulCnt] = useState(0)
  const [happyCnt, setHappyCnt] = useState(0)
  const [sadCnt, setSadCnt] = useState(0)
  const [surprisedCnt, setSurprisedCnt] = useState(0)

  const [angry, setAngry] = useState(0)
  const [disgusted, setDisgusted] = useState(0)
  const [fearful, setFearful] = useState(0)
  const [happy, setHappy] = useState(0)
  const [sad, setSad] = useState(0)
  const [surprised, setSurprised] = useState(0)

  const [apiStarted, setApiStarted] = useState(false)

  // tf 세팅 및 모델 불러오기
  useEffect(() => {
    tf.env().set('WEBGL_CPU_FORWARD', false)
    loadModels()
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
    setSession(null)
    setPublisher(null)
    setSubscribers([])
    const response = await axios.delete(
      APPLICATION_SERVER_URL + 'api/onetoone/' + meetingRoomSeq,
      {},
      {}
    )
    console.log(response.status)
    // navigate('/')
    alert('미팅이 종료되었습니다!!!!')
    window.location.href = '/'
  }, [session, meetingRoomSeq])

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
      console.log('here',subscriber)
      console.log(data)
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
  const onChangeCameraStatus = useCallback(
    (status) => {
      publisher?.publishVideo(status)
    },
    [publisher]
  )

  // 마이크 상태
  const onChangeMicStatus = useCallback(
    (status) => {
      publisher?.publishAudio(status)
    },
    [publisher]
  )

  // 감정 분석 시작
  async function startFaceAPI(videoEl) {
    if (!apiStarted) {
      // 시작되었는지의 플래그
      setApiStarted(true)
      console.log('video loaded')
      await onPlay(videoEl)
    }
  }

  // 감정 분석 1초에 2번
  async function onPlay(videoEl) {
    if (!videoEl || videoEl.paused || videoEl.ended)
      return setTimeout(() => onPlay(videoEl), 500)

    const predict = await faceapi
      .detectSingleFace(videoEl)
      .withFaceExpressions()

    // console.log(predict)
    if (predict) {
      setAngry(predict.expressions.angry)
      if (angry > 0.25) {
        setAngryCnt(angryCnt + 1)
      }
      setDisgusted(predict.expressions.disgusted)
      if (disgusted > 0.25) {
        setDisgustedCnt(disgustedCnt + 1)
      }
      setFearful(predict.expressions.fearful)
      if (fearful > 0.25) {
        setFearfulCnt(fearfulCnt + 1)
      }
      setHappy(predict.expressions.happy)
      if (happy > 0.25) {
        setHappyCnt(happyCnt + 1)
      }
      setSad(predict.expressions.sad)
      if (sad > 0.25) {
        setSadCnt(sadCnt + 1)
      }
      setSurprised(predict.expressions.surprised)
      if (surprised > 0.25) {
        setSurprisedCnt(surprisedCnt + 1)
      }
    }

    setTimeout(() => onPlay(videoEl), 500)
  }

  // userSeq 기반으로 오픈비두 토큰 가져옴
  async function getDocking1Token(userSeq) {
    const response = await axios.post(
      APPLICATION_SERVER_URL + 'api/onetoone',
      { userSeq: userSeq },
      {}
    )
    setMeetingRoomSeq(response.data.meetingRoomSeq)
    setToken(response.data.token)
    alert('Get Token!!!' + token)
    return response.data
  }

  const [maskPath, setMaskPath] = useState(
    CLOUD_FRONT_URL + 'images/glass-1-mask-1.png'
  )

  useEffect(() => {
    if (phase < 0 && subscribers.length > 0) {
      setPhase(0)
      setTimeout(() => {
        axios.post(
          APPLICATION_SERVER_URL +
            'api/onetoone/one/' +
            meetingRoomSeq +
            '/start'
        )
      }, 10000)
    }
  }, [subscribers])

  async function handleSystem(json_body) {}
  async function handleExit(json_body) {
    // flag EXIT 이면 내보내기
    leaveSession()
  }
  async function handlePhase1(json_body) {
    // 미팅 시작 - 타이머 돌릴것
    if (phase < 1) {
      setPhase(1)
      setTimeout(() => {
        axios.post(
          APPLICATION_SERVER_URL +
            'api/onetoone/one/' +
            meetingRoomSeq +
            '/phase2',
          {},
          {}
        )
      }, 60000)
    }
  }
  async function handlePhase2(json_body) {
    // 선글라스 벗기고 타이머 돌릴것
    if (phase < 2) {
      setPhase(2)
      setMaskPath(CLOUD_FRONT_URL + 'images/glass-0-mask-1.png')
      setTimeout(() => {
        axios.post(
          APPLICATION_SERVER_URL +
            'api/onetoone/one/' +
            meetingRoomSeq +
            '/phase3',
          {},
          {}
        )
      }, 60000)
    }
  }
  async function handlePhase3(json_body) {
    // 마스크도 벗기고 타이머 돌릴것
    if (phase < 3) {
      setPhase(3)
      setMaskPath(CLOUD_FRONT_URL + 'images/glass-0-mask-0.png')
      setTimeout(() => {
        axios.post(
          APPLICATION_SERVER_URL +
            'api/onetoone/one/' +
            meetingRoomSeq +
            '/final',
          {},
          {}
        )
      }, 60000)
    }
  }
  async function handleFinal(json_body) {
    // 최종선택 - 모달창 등을 띄울것
  }

  return (
    <div className="container">
      {/* <h1>일대일 매칭 테스트 중</h1> */}
      {session === undefined ? (
        <div>
          <button onClick={joinSession}>준비 완료</button>
        </div>
      ) : null}
      {session !== undefined ? (
        <div className="video-container">
          <div className="sub-container">
            <EmotionComponent
              imgSrc={'../img/emo-angry.png'}
              data={angry}
              top={'40px'}
              left={'40px'}
            />
            <EmotionComponent
              imgSrc={'../img/emo-disgusted.png'}
              data={disgusted}
              top={'290px'}
              left={'40px'}
            />
            <EmotionComponent
              imgSrc={'../img/emo-fearful.png'}
              data={fearful}
              top={'540px'}
              left={'40px'}
            />
            <EmotionComponent
              imgSrc={'../img/emo-happy.png'}
              data={happy}
              top={'40px'}
              left={'700px'}
            />
            <EmotionComponent
              imgSrc={'../img/emo-sad.png'}
              data={sad}
              top={'290px'}
              left={'700px'}
            />
            <EmotionComponent
              imgSrc={'../img/emo-surprised.png'}
              data={surprised}
              top={'540px'}
              left={'700px'}
            />
            {subscribers.map((sub, idx) => (
              <div key={idx} id="subscriber">
                <FilteredVideo
                  streamManager={sub.streamManager}
                  maskPath={maskPath}
                  userSeq={2}
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
                  userSeq={userSeq}
                  startFaceAPI={() => {}}
                />
              </div>
            ) : null}
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
            <div className="btn-group">
              <button onClick={leaveSession}>나가기</button>
            </div>
          </div>
        </div>
      ) : null}
    </div>
  )
}

export default Docking1
