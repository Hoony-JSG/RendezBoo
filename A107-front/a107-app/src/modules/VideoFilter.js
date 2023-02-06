import FacePaint from './FacePaint'
import facemesh from 'facemesh'
import tf from 'tfjs'

export default VideoFilter

function VideoFilter() {
  var carousel = document.querySelector('.carousel')
  var background = document.querySelector('#background')
  var entries = [
    {
      handle: 'Chhau Mask',
      url: 'https://en.wikipedia.org/wiki/Chhau_mask',
      entry: './assets/chhau-mask.jpg',
      background: 'hsl(35, 60%, 50%)',
    },
  ]

  var videoFormats = ['mov', 'm4v', 'mp4']
  // var imageFormats = ['png', 'jpg'];
  var assets = []

  for (var i = 0; i < entries.length; i++) {
    var obj = entries[i]
    var el
    if (videoFormats.indexOf(obj.entry.split('.')[2]) > -1) {
      el = document.createElement('video')
      el.setAttribute('playsinline', true)
      el.setAttribute('loop', true)
      el.setAttribute('muted', true)
      el.setAttribute('autoplay', true)
      el.setAttribute('preload', 'auto')
      assets.push(
        new Promise((res) => {
          el.onloadeddata = res
        })
      )
    } else {
      el = document.createElement('img')
      assets.push(
        new Promise((res) => {
          el.onload = res
        })
      )
    }
    el.src = obj.entry
    el.classList.add('texture')
    el.setAttribute('id', obj.handle)
    carousel.appendChild(el)
  }

  function updateTexture(index) {
    var url = entries[index].entry
    var isVideo = videoFormats.indexOf(url.split('.')[2]) > -1
    faceCanvas.updateTexture(url, isVideo)
    background.style.background = entries[index].background
    if ('url' in entries[index]) {
      artist.style.pointerEvents = 'all'
      artist.href = entries[index].url
    } else {
      artist.style.pointerEvents = 'none'
      artist.href = '#'
    }
    artist.textContent = entries[index].handle
  }

  const webcam = document.querySelector('#webcam')
  let model, faceCanvas, w, h
  const loaderMsg = document.querySelector('#loaderMsg')

  var artist = document.querySelector('#artist')

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
          textureFilePath: entries[0].entry,
          w,
          h,
        }
        faceCanvas = new FacePaint(props)
        updateTexture(0)
        document.querySelector('#loader').style.display = 'none'
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
      await Promise.all(assets)
      renderPredictions()
    } catch (e) {
      alert(e)
      console.error(e)
    }
  }
  tf.env().set('WEBGL_CPU_FORWARD', false)
  main()
}
