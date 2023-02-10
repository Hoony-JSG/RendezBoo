import React, { useEffect, useRef } from 'react'
import { useStream } from '../../modules/useStream'
import { VideoFilter } from '../../modules/VideoFilter'

export const FilteredVideo = ({
  streamManager,
  maskPath,
  userSeq,
  startFaceAPI,
}) => {
  const canvasRef = useRef(null)
  // const videoRef = useRef(null)
  const { videoRef, speaking, micStatus, videoStatus } =
    useStream(streamManager)

  VideoFilter(videoRef.current, canvasRef.current, maskPath)

  const videoStyle = {
    left: '0px',
    width: '100%',
    height: '100%',
    position: 'absolute',
    objectFit: 'cover',
    zIndex: 10,
  }

  const canvasStyle = {
    left: '0px',
    width: '100%',
    height: '100%',
    position: 'absolute',
    objectFit: 'cover',
    // transform: 'rotateY(180deg)',
    zIndex: 12,
  }

  useEffect(() => {
    startFaceAPI(document.querySelector('#streamVideo'))
  }, [startFaceAPI])

  return (
    <div
      style={{
        position: 'relative',
        width: '100%',
        height: '100%',
      }}
    >
      <video
        id="streamVideo"
        ref={videoRef}
        style={videoStyle}
        autoPlay={true}
      />
      <canvas id="faceCanvas" ref={canvasRef} style={canvasStyle} />
    </div>
  )
}

export default FilteredVideo
