import { useState, useEffect } from 'react'
import { useSelector } from 'react-redux'
import axios from 'axios'
import '../../Styles/SignalSelected.css'

const Docking3ChatItem = ({ chat }) => {
  const { flag, message, createdAt, senderSeq } = chat
  const me = useSelector((state) => state.userInfoReducer.userSeq)
  const [you, setYou] = useState([])
  const time = new Date(createdAt)

  useEffect(() => {
    if (senderSeq !== me && flag === 'CHAT') {
      axios
        .get('https://i8a107.p.ssafy.io/api/user/' + senderSeq)
        .then((response) => {
          setYou({
            yourSeq: senderSeq,
            yourName: response.data.name,
          })
        })
    }
  }, [chat])

  return (
    <div>
      {flag !== 'CHAT' ? (
        <p className="system-signal">{message}</p>
      ) : senderSeq !== me ? (
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
            {parseInt(time.getHours() / 12) ? (
              <p id="time">
                {time.getHours() % 12 || 12}:{time.getMinutes()} PM
              </p>
            ) : (
              <p id="time">
                {time.getHours()}:{time.getMinutes()} AM
              </p>
            )}
          </div>
        </div>
      ) : (
        <div className="my-signal">
          {parseInt(time.getHours() / 12) ? (
            <p id="time">
              {time.getHours() % 12 || 12}:{time.getMinutes()} PM
            </p>
          ) : (
            <p id="time">
              {time.getHours() || 12}:{time.getMinutes()} AM
            </p>
          )}
          <p>{message}</p>
        </div>
      )}
    </div>
  )
}

export default Docking3ChatItem
