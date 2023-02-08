import { OpenVidu } from 'openvidu-browser'

import axios from 'axios'
import React, { useCallback, useEffect, useMemo, useState } from 'react'
import { FilteredVideo } from '../components/FilteredVideo'
import * as faceapi from 'face-api.js'

const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === 'production' ? '' : 'https://i8a107.p.ssafy.io/'

const Docking1 = (props) => {
  const [userSeq, setUserSeq] = useState(-1)
  const [myUserName, setMyUserName] = useState(Math.floor(Math.random() * 100))
  const [token, setToken] = useState('')
  const [meetingRoomSeq, setMeetingRoomSeq] = useState(-1)
  const [subscribers, setSubscribers] = useState([])
  const [publisher, setPublisher] = useState()
  const [session, setSession] = useState()

  // 세션 나가기
  const leaveSession = useCallback(() => {
    if (session) {
      session.disconnect()
    }
    setSession(null)
    setPublisher(null)
    setSubscribers([])
  }, [session])

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
    getDocking1Token(userSeq).then((data) => {
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

  async function useFaceAPI() {
    const videoEl = document.querySelector('#hidden-cam')
    const stream = await navigator.mediaDevices.getUserMedia({ video: true })
    videoEl.srcObject = stream
    await faceapi.nets.tinyFaceDetector.load()
    await faceapi.loadFaceExpressionModel('/')
    await onplay(videoEl)
  }

  async function onPlay(videoRef) {
    const videoEl = videoRef

    if (videoEl.paused || videoEl.ended) return setTimeout(() => onPlay())

    const result = await faceapi.detectSingleFace(videoEl).withFaceExpressions()

    console.log(result)

    setTimeout(() => onPlay(videoRef))
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

  const handleUserSeq = (e) => {
    setUserSeq(e.target.value)
  }

  return (
    <div className="container">
      <h1>일대일 매칭 테스트 중</h1>
      {session === undefined ? (
        <div>
          <p>
            <label>UserSeq: </label>
            <input
              type="number"
              id="userSeq"
              value={userSeq}
              onChange={handleUserSeq}
              required
            />
          </p>
          <button onClick={joinSession}>준비 완료</button>
        </div>
      ) : null}
      {session !== undefined ? (
        <div
          className="video-container"
          style={{
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'center',
            alignItems: 'center',
          }}
        >
          <div
            className="sub-container"
            style={{
              width: '960px',
              height: '720px',
            }}
          >
            {subscribers.map((sub, idx) => (
              <div key={idx}>
                <FilteredVideo
                  streamManager={sub.streamManager}
                  maskPath={
                    'https://d156wamfkmlo3m.cloudfront.net/images/1675671334613cherial-mask.jpg'
                  }
                  userSeq={2}
                  width={'960px'}
                  height={'720px'}
                />
              </div>
            ))}
          </div>
          <div
            className="pub-container"
            style={{
              width: '400px',
              height: '720px',
            }}
          >
            {publisher !== undefined ? (
              <div>
                <FilteredVideo
                  streamManager={publisher}
                  maskPath={
                    'https://d156wamfkmlo3m.cloudfront.net/images/1675671334613cherial-mask.jpg'
                  }
                  userSeq={userSeq}
                  width={'320px'}
                  height={'240px'}
                />
              </div>
            ) : null}
          </div>
        </div>
      ) : null}
      <div style={{ display: 'none' }}>
        <video id="hidden-cam" />
      </div>
      <button onClick={useFaceAPI}>expressions</button>
    </div>
  )
}

export default Docking1
