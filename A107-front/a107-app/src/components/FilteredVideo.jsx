import React, { useEffect, useRef, useState } from 'react'
import { useStream } from '../modules/useStream'
import { VideoFilter } from '../modules/VideoFilter'

export const FilteredVideo = ({
  streamManager,
  maskPath,
  userSeq,
  width,
  height,
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

  VideoFilter(videoRef.current, canvasRef.current, maskPath, width, height)

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
