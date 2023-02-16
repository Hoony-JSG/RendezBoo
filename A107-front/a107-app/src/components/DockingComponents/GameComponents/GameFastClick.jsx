import { useState, useEffect } from 'react'

const GameFastClick = (props) =>{
  const {client, multiMeetingRoomSeq, userSeq} = props
  const [clickCnt, setClickCnt] = useState(0)
  const [pubbed, setPubbed] = useState(true)
  
  setTimeout(()=>{
    setClickCnt(0)
    setPubbed(false)
  }, 1000)

  setTimeout(()=>{
    if (!client || !client.current.connected) {
      alert('연결 상태를 확인해주세요.')
      return
    }

    const body = {
      multiMeetingRoomSeq: multiMeetingRoomSeq,
      userSeq: userSeq,
      score : clickCnt
    }
    const json_body = JSON.stringify(body)

    client.current.publish({
      destination: '/pub/fastclick',
      body: json_body,
    })
    setPubbed(true)
  }, 11000)

  const handleClick = (e) => {
    setClickCnt((prev)=>prev+1)
  }

  return (
    <div>
      <h1>

      FastClick
      </h1>
      <div>
        {pubbed ? null:<div>
            <button onClick={handleClick} >빠르게 누르세요!</button>
          </div>}
      </div>
    </div>
  )
}
export default GameFastClick;