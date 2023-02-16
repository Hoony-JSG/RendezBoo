import { useState, useEffect } from 'react'

const GameFastClick = (props) => {
  const { client, multiMeetingRoomSeq, userSeq, userName } = props
  const [clickCnt, setClickCnt] = useState(0)
  const [ready, setReady] = useState(false)
  const [pubbed, setPubbed] = useState(false)

  const handleClick = (e) => {
    setClickCnt((prev) => prev + 1)
  }

  const onReadyGame = (e) => {
    setClickCnt(0)
    setReady(true)
    setTimeout(() => {
      if (!pubbed) {
        if (!client || !client.current.connected) {
          alert('연결 상태를 확인해주세요.')
          return
        }

        const body = {
          multiMeetingRoomSeq: multiMeetingRoomSeq,
          userSeq: userSeq,
          score: clickCnt,
        }
        const json_body = JSON.stringify(body)

        client.current.publish({
          destination: '/pub/fastclick',
          body: json_body,
        })
        setPubbed(true)
      }
    }, 10000)
  }

  return (
    <div>
      <h1>FastClick</h1>
      <div>
        {!ready ? (
          <div>
            <button onClick={onReadyGame}>준비</button>
          </div>
        ) : (
          <div>
            <button onClick={handleClick}>빠르게 누르세요!</button>
          </div>
        )}
        {pubbed ? <div>끝</div> : null}
      </div>
    </div>
  )
}
export default GameFastClick
