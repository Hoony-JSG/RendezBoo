import { useState } from 'react'

const GameTheGameOfDeath = (props) => {
  const { client, subscribers, userSeq, multiMeetingRoomSeq, gameofdeathBody } =
    props
  const [turn, setTurn] = useState(5)
  const [pubbed, setPubbed] = useState(false)

  const pubGOD = (e) => {
    // 연결이 안되어있을 경우
    if (!client || !client.current.connected) {
      alert('연결 상태를 확인해주세요.')
      return
    }

    const body = {
      multiMeetingRoomSeq: multiMeetingRoomSeq,
      userSeq: userSeq,
      targetSeq: e.target.id,
      turn: e.target.value,
    }
    const json_body = JSON.stringify(body)

    client.current.publish({
      destination: '/pub/gameofdeath',
      body: json_body,
    })
    setPubbed(true)
  }

  const handleTurnChange = (e) => {
    setTurn(e.target.value)
  }

  return (
    <div>
      <h1>더게임오브데스</h1>
      {pubbed ? (
        <div>
          {gameofdeathBody.loseUserSeq ? (<div></div>): null}
        </div>
      ) : (
        <div>
          <p>상대방을 선택하세요</p>
          <div>
            {gameofdeathBody.startUserSeq == userSeq ? (
              <div>
                <p>횟수를 선택하세요</p>
                <input type="number" value={turn} onChange={handleTurnChange} />
              </div>
            ) : null}
            <div>
              {subscribers.map((sub, idx) => {
                <div key={idx}>
                  <button onClick={pubGOD} id={sub.userSeq} value={turn}>
                    {sub.userName}
                  </button>
                </div>
              })}
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
export default GameTheGameOfDeath
