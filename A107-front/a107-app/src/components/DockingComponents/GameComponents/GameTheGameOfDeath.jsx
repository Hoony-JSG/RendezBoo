import { useCallback, useEffect, useState } from 'react'
import { FaBookDead } from 'react-icons/fa'
import '../../../Styles/TGDStyle.css'

const GameTheGameOfDeath = (props) => {
  const {
    client,
    subscribers,
    userSeq,
    multiMeetingRoomSeq,
    gameofdeathBody,
    userName,
  } = props
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

  const getLoseUserName = useCallback(
    (loseUserSeq) => {
      if (userSeq === loseUserSeq) {
        return userName
      } else {
        return subscribers.filter((sub) => sub.userSeq == loseUserSeq)[0]
          .userName
      }
    },
    [subscribers]
  )

  ///////////////////////////////
  useEffect(() => {
    setPubbed(true)
  }, [])

  return (
    <div className="TGD_whole-container">
      <h1>더게임오브데스</h1>
      {pubbed ? (
        <div className="TGD_result-box">
          {gameofdeathBody.loseUserSeq ? (
            <div className="TGD_loser">
              {getLoseUserName(gameofdeathBody.loseUserSeq)}
              has dead...
            </div>
          ) : null}
        </div>
      ) : (
        <div className="TGD_game-box">
          <div className="TGD_game-choose-options">
            {gameofdeathBody && gameofdeathBody.startUserSeq == userSeq ? (
              <div className="TGD_game-choose-count-container">
                <p className="TGD_game-choose-count-text">
                  <div>
                    set Count
                    <br />[ 3 ~ 20 ]
                  </div>
                  <FaBookDead
                    className="TGD_game-choose-count-text-icon"
                    size="50px"
                  />
                </p>
                <select
                  className="TGD_game-choose-count-select"
                  value={turn}
                  onChange={handleTurnChange}
                >
                  {Array.from({ length: 18 }, (_, i) => i + 3).map((number) => (
                    <option key={number} value={number}>
                      {number}
                    </option>
                  ))}
                </select>
              </div>
            ) : null}
            <div className="TGD_game-choose-whole-person-box">
              <div className="TGD_card">
                <span className="TGD_card-span">💀 Choose One 💀</span>
                <div class="TGD_card-inner-select">
                  {subscribers.map((sub, idx) => (
                    <div className="TGD_game-choose-one-person-box" key={idx}>
                      <button
                        className="TGD_game-choose-one-person-button"
                        onClick={pubGOD}
                        /* id={sub.userSeq} */ value={turn}
                      >
                        {/* {sub.userName} */}
                      </button>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
export default GameTheGameOfDeath
