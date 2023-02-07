import { useEffect, useRef, useState } from 'react'

export const useStream = (streamManager) => {
  const videoRef = useRef(null)
  const [speaking, setSpeaking] = useState(false)
  // const [micStatus, setMicStatus] = useState(streamManager.stream.audioActive)
  const [micStatus, setMicStatus] = useState(false)
  // const [videoStatus, setVideoStatus] = useState(streamManager.stream.videoActive)
  const [videoStatus, setVideoStatus] = useState(true)

  useEffect(() => {
    streamManager.addVideoElement(videoRef.current)

    streamManager.on('publisherStartSpeaking', (event) => {
      if (event.streamId !== streamManager.stream.streamId) {
        return
      }
      setSpeaking(true)
    })

    streamManager.on('publisherStopSpeaking', (event) => {
      if (event.streamId !== streamManager.stream.streamId) {
        return
      }
      setSpeaking(false)
    })

    streamManager.on('streamPropertyChanged', (event) => {
      if (event.stream.streamId !== streamManager.stream.streamId) {
        return
      }

      if (event.changedProperty === 'videoActive') {
        setVideoStatus(event.newValue)
      } else if (event.changedProperty === 'audioActive') {
        setMicStatus(event.newValue)
      }
    })
  })

  return {
    speaking,
    micStatus,
    videoStatus,
    videoRef,
  }
}
