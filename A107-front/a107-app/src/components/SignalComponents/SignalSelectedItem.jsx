import { useSelector } from 'react-redux'

const SignalListItem = ({ chat, you }) => {
  const { message, createdAt, senderSeq } = chat
  const me = useSelector((state) => state.userInfoReducer.userSeq)
  const time =
    typeof createdAt === 'string'
      ? new Date(createdAt)
      : new Date(...createdAt.slice(0, 5))

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

export default SignalListItem
