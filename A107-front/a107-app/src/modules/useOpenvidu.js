import { OpenVidu } from 'openvidu-browser'
import { useCallback, useEffect, useMemo, useState } from 'react'

export const useOpenvidu = (userSeq, meetingRoomSeq, token) => {
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
  useEffect(() => {
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
    session
      .connect(token, JSON.stringify({ userSeq }))
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
          mirror: true,
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

    setSession(session)
    return () => {
      if (session) {
        session.disconnect()
      }
      setSession(null)
      setPublisher(null)
      setSubscribers([])
    }
  }, [token, userSeq])

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

  const streamList = useMemo(
    () => [{ streamManager: publisher, userSeq }, ...subscribers],
    [publisher, subscribers, userSeq]
  )

  return {
    publisher,
    streamList,
    onChangeCameraStatus,
    onChangeMicStatus,
  }
}
