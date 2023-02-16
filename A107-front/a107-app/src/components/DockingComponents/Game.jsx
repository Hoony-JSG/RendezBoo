import { useSelector } from 'react-redux'
import GameBR31 from './GameComponents/GameBR31'
import GameTheGameOfDeath from './GameComponents/GameTheGameOfDeath'
import GameFastClick from './GameComponents/GameFastClick'
import { SlSpeedometer } from 'react-icons/sl'
import { IoIosIceCream } from 'react-icons/io'
import { GiDeathZone } from 'react-icons/gi'
import '../../Styles/GameStyle.css'
const Game = (props) => {
  const {
    client,
    setGameFlag,
    gameType,
    setGameType,
    subscribers,
    multiMeetingRoomSeq,
    br31MyTurnFlag,
    setBr31MyTurnFlag,
    br31Point,
    gameofdeathBody,
    fastclickBody,
    br31Body,
  } = props

  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)
  const userName = useSelector((state) => state.userInfoReducer.userName)
  const userGender = useSelector((state) => state.userInfoReducer.userGender)

  const pubGame = (gameName, body) => {
    if (!client || !client.current.connected) {
      alert('연결 상태를 확인해주세요.')
      return
    }
    client.current.publish({
      destination: `/pub/${gameName}/start`,
      body: body,
    })
  }

  return (
    <div className="Game_whole-container-wholewhole">
      <div className="Game_whole-container">
        <div className="Game_button-X-container">
          <button
            className="Game_button-X"
            onClick={() => {
              setGameType(false)
              setGameFlag(false)
            }}
          >
            <span>X</span>
          </button>
        </div>

        {!gameType ? (
          <div className="Game_choose-game-box">
            <span>게임을 선택하세요</span>
            <div className="Game_choose-game-list">
              <div className="Game_choose-game-list-left">
                <div
                  className="Game_scene"
                  onClick={() => {
                    pubGame(
                      'br31',
                      JSON.stringify({
                        multiMeetingRoomSeq: multiMeetingRoomSeq,
                      })
                    )
                  }}
                >
                  <div className="Game_cube">
                    <span className="Game_side Game_top">Start</span>
                    <span className="Game_side Game_front">
                      <IoIosIceCream size="40px" />
                    </span>
                  </div>
                </div>
              </div>
              <div className="Game_choose-game-list-right">
                <div id="Game_choose-game-title">BR31</div>
                <div>
                  참가자 한 명씩 돌아가며 1~3 사이의 숫자를 제출합니다.
                  <br />총 합이 31이 될 때까지 반복하며, 최종적으로 31에
                  닿게되는 참가자가 패배합니다.
                </div>
              </div>
            </div>
            <div className="Game_choose-game-list">
              <div className="Game_choose-game-list-left">
                <div
                  className="Game_scene"
                  onClick={() => {
                    pubGame(
                      'gameofdeath',
                      JSON.stringify({
                        multiMeetingRoomSeq: multiMeetingRoomSeq,
                        startUserSeq: userSeq,
                      })
                    )
                  }}
                >
                  <div className="Game_cube">
                    <span className="Game_side Game_top">Start</span>
                    <span className="Game_side Game_front">
                      <GiDeathZone size="40px" />
                    </span>
                  </div>
                </div>
              </div>
              <div className="Game_choose-game-list-right">
                <div id="Game_choose-game-title">The Game Of Death</div>
                <div>
                  각자 한 명씩 지목합니다.
                  <br />
                  게임 주최자가 세팅한 횟수만큼의 지목을 거쳐갑니다. 마지막으로
                  지목된 참가자가 패배합니다
                </div>
              </div>
            </div>
            <div className="Game_choose-game-list">
              <div className="Game_choose-game-list-left">
                <div
                  className="Game_scene"
                  onClick={() => {
                    pubGame(
                      'gameofdeath',
                      JSON.stringify({
                        multiMeetingRoomSeq: multiMeetingRoomSeq,
                        startUserSeq: userSeq,
                      })
                    )
                  }}
                >
                  <div className="Game_cube">
                    <span className="Game_side Game_top">Start</span>
                    <span className="Game_side Game_front">
                      <SlSpeedometer size="40px" />
                    </span>
                  </div>
                </div>
              </div>
              <div className="Game_choose-game-list-right">
                <div id="Game_choose-game-title">Faster Clicker</div>
                <div>
                  제한시간 내에 최대한 많은 클릭을 해야합니다.
                  <br />
                  모든 참가자들 중, 가장 적은 클릭 수를 기록한 참가자가
                  패배합니다.
                </div>
              </div>
            </div>
          </div>
        ) : null}
        {gameType === 'BR31' ? (
          <div className="Game_started-game-container">
            <GameBR31
              client={client}
              multiMeetingRoomSeq={multiMeetingRoomSeq}
              br31MyTurnFlag={br31MyTurnFlag}
              setBr31MyTurnFlag={setBr31MyTurnFlag}
              br31Point={br31Point}
              br31Body={br31Body}
            />
          </div>
        ) : null}
        {gameType === 'GAMEOFDEATH' ? (
          <div className="Game_started-game-container">
            <GameTheGameOfDeath
              client={client}
              subscribers={subscribers}
              userSeq={userSeq}
              multiMeetingRoomSeq={multiMeetingRoomSeq}
              gameofdeathBody={gameofdeathBody}
              userName={userName}
            />
          </div>
        ) : null}
        {gameType === 'FAST CLICK' ? (
          <div className="Game_started-game-container">
            <GameFastClick
              client={client}
              subscribers={subscribers}
              userSeq={userSeq}
              multiMeetingRoomSeq={multiMeetingRoomSeq}
              userName={userName}
              fastclickBody={fastclickBody}
            />
          </div>
        ) : null}
      </div>
    </div>
  )
}
export default Game
