import { useSelector } from 'react-redux'

const SignalListItem = ({ chat, you }) => {
  const { message, createdAt, senderSeq } = chat
  const me = useSelector((state) => state.userInfoReducer.userSeq)
  const time = typeof(createdAt) === "string" ? new Date(createdAt) : new Date(...createdAt)

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
          <p className="your-name">{you.yourname}</p>
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

export default SignalListItem
