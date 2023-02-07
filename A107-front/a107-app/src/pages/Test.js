import { useCallback, useEffect, useRef, useState } from 'react'
import { FilteredVideo } from '../components/FilteredVideo'

async function getCamera() {
  const constraints = { video: true, audio: false }
  const stream = await navigator.mediaDevices.getUserMedia(constraints)
  const video = document.querySelector('#webcam')
  video.srcObject = stream
}

const Test = () => {
  let [streamManager, setStreamManager] = useState('')
  let [maskPath, setMaskPath] = useState(
    'https://d156wamfkmlo3m.cloudfront.net/images/1675671334613cherial-mask.jpg'
  )
  let [userSeq, setUserSeq] = useState(2)

  return (
    <div>
      <h1>테스트용</h1>
      <video id="webcam" className="visible" autoPlay></video>
      <button onClick={getCamera}>테스트용 캠</button>
      <FilteredVideo
        streamManager={streamManager}
        maskPath={maskPath}
        userSeq={userSeq}
      />
    </div>
  )
}

export default Test
