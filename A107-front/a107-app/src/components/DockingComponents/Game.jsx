import {useState} from 'react'
import GameBR31 from './GameBR31'
import GameTheGameOfDeath from './GameTheGameOfDeath'
import GameFastClick from './GameFastClick'

const Game = () =>{
  const {gameType, setGameType} = useState()

  return (
    <div className="game_container">
      {gameType === undefined?(
          <div>
            <div onClick={
              function(){
                return setGameType("BR31")
              }
            }>베스킨라빈스31게임
            </div>
            <div onClick={
              function(){
                return setGameType("THEGAMEOFDEATH")
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
      {gameType === "BR31"?(<GameBR31/>):(null)}
      {gameType === "THEGAMEOFDEATH"?(<GameTheGameOfDeath/>):(null)}
      {gameType === "FASTCLICK"?(<GameFastClick/>):(null)}
    </div>
  )
}
export default Game;