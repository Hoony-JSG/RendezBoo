import { useState, useEffect, useCallback } from 'react'
import '../../../Styles/GameFastClickStyle.css'
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
  ////////////////////////
  const [count, setCount] = useState('Start!')
  const fastClick = () => {
    if (count === 'Start!') {
      setCount(0)
    } else {
      setCount((previousCount) => {
        setCount(previousCount + 1)
        console.log(previousCount)
        const randomColor = `rgba(${Math.floor(
          Math.random() * 256
        )}, ${Math.floor(Math.random() * 256)}, ${Math.floor(
          Math.random() * 256
        )}, 0.3)`
        document.querySelector(
          '.FastClick_fast-click-button-rainbow'
        ).style.backgroundColor = randomColor
      })
    }
  }

  return (
    <div className="FastClick_container">
      <h1>FastClick</h1>
      {pubbed ? (
        fastclickBody ? (
          <div className="FastClick_loser">
            패배 : {getUserName(fastclickBody.loseUserSeq)}
          </div>
        ) : (
          <div className="FastClick_time-out">시간이 다 되었습니다.</div>
        )
      ) : !ready ? (
        <div>
          <button className="FastClick_ready-button" onClick={onReadyGame}>
            준비
          </button>
        </div>
      ) : (
        <div className="FastClick_fast-click-button-container">
          <button
            className="FastClick_fast-click-button-rainbow"
            onClick={() => {
              handleClick()
              fastClick()
            }}
          >
            <span className="FastClick_count">{count}</span>
          </button>
        </div>
      )}
    </div>
  )
}
export default GameFastClick
