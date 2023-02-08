import React, { useRef } from 'react'
import { useStream } from '../../modules/useStream'
import { VideoFilter } from '../../modules/VideoFilter'

export const FilteredVideo = ({
  streamManager,
  maskPath,
  userSeq,
}) => {
  const canvasRef = useRef(null)
  // const videoRef = useRef(null)
  const { videoRef, speaking, micStatus, videoStatus } =
    useStream(streamManager)
  // useEffect(() => {
  //   const getCamera = async () => {
  //     const newStream = await navigator.mediaDevices.getUserMedia({
  //       video: true,
  //       audio: false,
  //     })
  //     if (videoRef && videoRef.current && !videoRef.current.srcObject) {
  //       videoRef.current.srcObject = newStream
  //     }
  //   }
  //   getCamera()
  // }, [])

  VideoFilter(videoRef.current, canvasRef.current, maskPath)

  const videoStyle = {
    width: '100%',
    height: 'inherit',
    position: 'absolute',
    // top: 0,
    // right: 0,
    // left: 0,
    // bottom: 0,
    // objectFit: 'cover',
    zIndex: 10,
  }
  
  const canvasStyle = {
    width: '100%',
    height: 'inherit',
    // position: 'absolute',
    // top: 0,
    // right: 0,
    // left: 0,
    // bottom: 0,
    // objectFit: 'cover',
    zIndex: 10,
  }
  

  return (
    <div
      style={{
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
      <canvas
        id="faceCanvas"
        ref={canvasRef}
        style={canvasStyle}
      />
    </div>
  )
}

export default FilteredVideo