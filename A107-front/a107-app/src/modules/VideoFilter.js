import { useCallback, useEffect, useRef, useState } from 'react'
import FacePaint from './FacePaint'
import * as facemesh from '@tensorflow-models/facemesh'

export const VideoFilter = (videoElement, canvasElement, maskPath) => {
  const [model, setModel] = useState()

  useEffect(() => {
    if (model) {
      return
    }

    facemesh
      .load({
        maxContinuousChecks: 5,
        detectionConfidence: 0.9,
        maxFaces: 1,
        iouThreshold: 0.3,
        scoreThreshold: 0.75,
      })
      .then((it) => setModel(it))
      .then(() => console.log('Model Loaded!!'))
  }, [model])

  const requestRef = useRef(0)
  const [faceCanvas, setFaceCanvas] = useState()

  const animate = useCallback(async () => {
    if (!model) {
      return
    }

    try {
      const predictions = await model.estimateFaces(videoElement)
      if (!predictions.length) {
        requestRef.current = requestAnimationFrame(animate)
        return
      }

      if (!faceCanvas) {
        setFaceCanvas(
          new FacePaint({
            canvas: canvasElement,
            textureFilePath: maskPath,
            w: videoElement.clientWidth,
            h: videoElement.clientHeight,
          })
        )
        return
      }

      let positionBufferData = predictions[0].scaledMesh.reduce(
        (acc, pos) => acc.concat(pos),
        []
      )

      faceCanvas.render(positionBufferData)
      requestRef.current = requestAnimationFrame(animate)
    } catch (e) {
      requestRef.current = requestAnimationFrame(animate)
    }
  }, [model, videoElement, faceCanvas, canvasElement, maskPath])

  useEffect(() => {
    requestRef.current = requestAnimationFrame(animate)

    return () => cancelAnimationFrame(requestRef.current)
  }, [animate])

  useEffect(() => {
    if (faceCanvas) {
      faceCanvas.updateTexture(maskPath)
    }
  }, [maskPath, faceCanvas])
}
