import React, { useEffect, useRef } from 'react'
import { useStream } from '../../modules/useStream'
import { VideoFilter } from '../../modules/VideoFilter'

export const FilteredVideo = ({
  streamManager,
  maskPath,
  userSeq,
  width,
  height,
  startFaceAPI,
}) => {
  const canvasRef = useRef(null)
  // const videoRef = useRef(null)
  const { videoRef, speaking, micStatus, videoStatus } =
    useStream(streamManager)

  VideoFilter(videoRef.current, canvasRef.current, maskPath)
  useEffect(() => {
    startFaceAPI(document.querySelector('#streamVideo'))
  }, [startFaceAPI])

  return (
    <div
      style={{
        width: width,
        height: height,
        border: 'solid white 1px',
        position: 'relative',
      }}
    >
      <video
        id="streamVideo"
        ref={videoRef}
        style={{
          width: '100%',
          height: '100%',
          position: 'absolute',
          top: 0,
          right: 0,
          left: 0,
          bottom: 0,
          objectFit: 'cover',
          zIndex: 10,
        }}
        autoPlay={true}
      />
      <canvas
        id="faceCanvas"
        ref={canvasRef}
        style={{
          width: '100%',
          height: '100%',
          position: 'absolute',
          top: 0,
          right: 0,
          left: 0,
          bottom: 0,
          objectFit: 'cover',
          zIndex: 12,
        }}
      />
    </div>
  )
}
