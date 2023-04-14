import { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import axios from 'axios'
import '../../Styles/SignalSelected.css'

const Docking1ChatItem = ({ chat }) => {
  const { message, createdAt, senderSeq } = chat
  const me = useSelector((state) => state.userInfoReducer.userSeq)
  const [you, setYou] = useState({
    yourSeq: null,
    yourName: null,
  })
  const time = new Date(createdAt)

  useEffect(() => {
    if (senderSeq !== me) {
      axios
        .get('https://i8a107.p.ssafy.io/api/user/' + senderSeq)
        .then((response) => {
          setYou({
            yourSeq: senderSeq,
            yourName: response.data.name,
          })
        })
    }
  }, [senderSeq])

  return (
    <div>
      {senderSeq !== me ? (
        <div
          style={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'flex-start',
            margin: '10px',
          }}
        >
          <p className="your-name">{you.yourName}</p>
          <div className="your-signal">
            <p>{message}</p>
            <p id="time">
              {time.getHours()}:{time.getMinutes()}
            </p>
          </div>
        </div>
      ) : (
        <div className="my-signal">
          <p id="time">
            {time.getHours()}:{time.getMinutes()}
          </p>
          <p>{message}</p>
        </div>
      )}
    </div>
  )
}

export default Docking1ChatItem
