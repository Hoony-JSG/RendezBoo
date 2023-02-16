import {useState, useCallback} from 'react'
import { useSelector } from 'react-redux'
import GameBR31 from './GameComponents/GameBR31'
import GameTheGameOfDeath from './GameComponents/GameTheGameOfDeath'
import GameFastClick from './GameComponents/GameFastClick'

const Game = (props) =>{
  const {client, subscribers, multiMeetingRoomSeq, gameType} = props

  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)

  const WEBSOCKET_SERVER_URL =
    process.env.NODE_ENV === 'production'
      ? 'wss://i8a107.p.ssafy.io/'
      : 'ws://localhost:8080/'
  const pubGame = (gameName, body) => {
    // 연결이 안되어있을 경우
    if (!client || !client.current.connected) {
      alert('연결 상태를 확인해주세요.')
      return
    }
    client.current.publish({
      destination: `/pub/${gameName}/start`,
      body: body
      })
    }

  return (
    <div className={'game-modal'}>
      {gameType === undefined?(
          <div>
            <button onClick={()=>{
                pubGame("br31", JSON.stringify({
                  multiMeetingRoomSeq: multiMeetingRoomSeq
                }))
              }
            }>베스킨라빈스31게임
            </button>
            <button onClick={()=>{
                pubGame("gameofdeath", JSON.stringify({
                  multiMeetingRoomSeq: multiMeetingRoomSeq,
                  startUserSeq: userSeq
                }))
              }
            }>더게임오브데스
            </button>
            <button onClick={()=>{
                pubGame("fastclick", JSON.stringify({
                  multiMeetingRoomSeq: multiMeetingRoomSeq
                }))
              }
            }>누가 게임의 신인가?
            </button>
          </div>
        ):(null)
      }
      {gameType === "BR31"?(<GameBR31 client={client} />):(null)}
      {gameType === "GAMEOFDEATH"?(<GameTheGameOfDeath client={client} subscribers={subscribers} />):(null)}
      {gameType === "FASTCLICK"?(<GameFastClick client={client}/>):(null)}
    </div>
  )
}
export default Game;