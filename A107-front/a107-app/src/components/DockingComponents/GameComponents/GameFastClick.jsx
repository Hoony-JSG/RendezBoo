import { useState, useEffect, useCallback } from 'react'

const GameFastClick = (props) => {
  const {
    client,
    multiMeetingRoomSeq,
    userSeq,
    userName,
    fastclickBody,
    subscribers,
  } = props
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

  const getUserName = useCallback(
    (seq) => {
      if (userSeq === seq) {
        return userName
      } else {
        return subscribers.filter((sub) => sub.userSeq === seq)[0].userName
      }
    },
    [subscribers]
  )

  return (
    <div>
      <h1>FastClick</h1>
      <div>
        {pubbed ? (
          fastclickBody ? (
            <div>패배자는 {getUserName(fastclickBody.loseUserSeq)}</div>
          ) : (
            <div>시간이 다 되었습니다.</div>
          )
        ) : !ready ? (
          <div>
            <button onClick={onReadyGame}>준비</button>
          </div>
        ) : (
          <div>
            <button onClick={handleClick}>빠르게 누르세요!</button>
          </div>
        )}
      </div>
    </div>
  )
}
export default GameFastClick
