import React from 'react'
import FacePaint from '../modules/FacePaint'
import * as tf from '@tensorflow/tfjs'
import * as facemesh from '@tensorflow-models/facemesh'

function filteredCam() {
  const videoFormats = ['mov', 'm4v', 'mp4']
  // var imageFormats = ['png', 'jpg'];
  let assets = []

  const webcam = document.querySelector('#webcam2')
  let model, faceCanvas, w, h
  const loaderMsg = document.querySelector('#loaderMsg')

  let obj = {
    handle: 'Chhau Mask',
    url: 'https://en.wikipedia.org/wiki/Chhau_mask',
    entry: '../../public/img/chhau-mask.jpg',
    background: 'hsl(35, 60%, 50%)',
  }
  let el = document.createElement('video')
  el.setAttribute('playsinline', true)
  el.setAttribute('loop', true)
  el.setAttribute('muted', true)
  el.setAttribute('autoplay', true)
  el.setAttribute('preload', 'auto')
  // assets.push(
  //   new Promise((res) => {
  //     el.onloadeddata = res
  //   })
  // )
  el.src = obj.entry
  el.classList.add('texture')
  el.setAttribute('id', obj.handle)
  alert('hello')
  document.querySelector('#filtered').appendChild(el)

  function updateTexture(index) {
    var url = obj.entry
    var isVideo = videoFormats.indexOf(url.split('.')[2]) > -1
    faceCanvas.updateTexture(url, isVideo)
  }

  async function renderPredictions(t) {
    requestAnimationFrame(renderPredictions)
    loaderMsg.textContent = 'Search face'
    const predictions = await model.estimateFaces(webcam)

    if (predictions.length > 0) {
      const positionBufferData = predictions[0].scaledMesh.reduce(
        (acc, pos) => acc.concat(pos),
        []
      )
      if (!faceCanvas) {
        const props = {
          id: 'faceCanvas',
          textureFilePath: obj.entry,
          w,
          h,
        }
        faceCanvas = new FacePaint(props)
        updateTexture(0)
        document.querySelector('#loader').style.display = 'none'
        alert('hello3')
        return
      }
      faceCanvas.render(positionBufferData)
    }
  }

  async function main() {
    try {
      loaderMsg.textContent = 'Load webcam'
      const stream = await navigator.mediaDevices.getUserMedia({
        video: true,
        audio: false,
      })
      webcam.srcObject = stream
      await new Promise(function (res) {
        webcam.onloadedmetadata = function () {
          w = webcam.videoWidth
          h = webcam.videoHeight
          res()
        }
      })

      webcam.height = h
      webcam.width = w
      webcam.setAttribute('autoplay', true)
      webcam.setAttribute('muted', true)
      webcam.setAttribute('playsinline', true)
      webcam.play()
      loaderMsg.textContent = 'Load model'
      // Load the MediaPipe facemesh model.
      model = await facemesh.load({
        maxContinuousChecks: 5,
        detectionConfidence: 0.9,
        maxFaces: 1,
        iouThreshold: 0.3,
        scoreThreshold: 0.75,
      })
      loaderMsg.textContent = 'Load media'
      // await Promise.all(assets)
      alert('hello2')
      renderPredictions()
      alert('hello4')
    } catch (e) {
      alert(e)
      console.error(e)
    }
  }

  tf.env().set('WEBGL_CPU_FORWARD', false)
  main()
}

async function getCamera() {
  const constraints = { video: true, audio: false }
  const stream = await navigator.mediaDevices.getUserMedia(constraints)
  const video = document.querySelector('#webcam')
  video.srcObject = stream
}

const Test = () => {
  return (
    <div>
      <h1>테스트용</h1>
      <video id="webcam" className="visible" autoPlay></video>
      <button onClick={getCamera}>테스트용 캠</button>
      <div id="filtered">
        <canvas id="faceCanvas" tabindex="1"></canvas>
        <video muted id="webcam2"></video>
        <p id="loaderMsg"></p>
      </div>
      <button onClick={filteredCam}>테스트용 필터 캠</button>
    </div>
  )
}

export default Test
