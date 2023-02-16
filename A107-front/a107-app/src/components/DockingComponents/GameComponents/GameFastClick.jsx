import { useState, useEffect } from 'react'

const GameFastClick = (props) =>{
  const {client} = props
  const [clickCnt, setClickCnt] = useState(0)
  const [pubbed, setPubbed] = useState(true)
  setTimeout(()=>{
    setClickCnt(0)
    setPubbed(false)
  }, 1000)

  setTimeout(()=>{
    
  })

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