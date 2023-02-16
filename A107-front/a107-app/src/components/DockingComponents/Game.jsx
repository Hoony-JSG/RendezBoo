import {useState} from 'react'
import GameBR31 from './GameComponents/GameBR31'
import GameTheGameOfDeath from './GameComponents/GameTheGameOfDeath'
import GameFastClick from './GameComponents/GameFastClick'
import axios from 'axios'

const Game = (props) =>{
  const {client, subscribers} = props
  const {gameType, setGameType} = useState()
  const APPLICATION_SERVER_URL =
    process.env.NODE_ENV === 'production'
      ? 'https://i8a107.p.ssafy.io'
      : 'http://localhost:8080'
  const WEBSOCKET_SERVER_URL =
    process.env.NODE_ENV === 'production'
      ? 'wss://i8a107.p.ssafy.io/'
      : 'ws://localhost:8080/'

  return (
    <div className={'game'}>
      {gameType === undefined?(
          <div>
            <div onClick={
              function(){
                axios.post(`${APPLICATION_SERVER_URL}/api/game/br31/start`).then(
                  setGameType("BR31")
                )
              }
            }>베스킨라빈스31게임
            </div>
            <div onClick={
              function(){
                return setGameType("GAMEOFDEATH")
              }
            }>더게임오브데스
            </div>
            <div onClick={
              function(){
                return setGameType("FASTCLICK")
              }
            }>누가 게임의 신인가?
            </div>
          </div>
        ):(null)
      }
      {gameType === "BR31"?(<GameBR31 client={client} />):(null)}
      {gameType === "THEGAMEOFDEATH"?(<GameTheGameOfDeath client={client} subscribers={subscribers} />):(null)}
      {gameType === "FASTCLICK"?(<GameFastClick client={client}/>):(null)}
    </div>
  )
}
export default Game;