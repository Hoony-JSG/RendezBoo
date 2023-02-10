const SignalListItem = ({ chat, userSeq, you }) => {
  const { message, createdAt, senderSeq } = chat



  return (
    <div>
      { senderSeq !== userSeq ? (
        <div className="your-signal">
          <p>{you.yourname}</p>
          <p>{message}</p>
          { createdAt[3] > 12 ? (
            <p id='time'>{createdAt[3]-12}:{createdAt[4]} PM</p>
          ) : (
          <p id='time'>{createdAt[3]}:{createdAt[4]} AM</p>
          )}
        </div>
      ) : (
      <div className="my-signal">
          { createdAt[3] > 12 ? (
            <p id='time'>{createdAt[3]-12}:{createdAt[4]} PM</p>
          ) : (
          <p id='time'>{createdAt[3]}:{createdAt[4]} AM</p>
          )}
        <p>{message}</p>
      </div>
      )}
    </div>
  )
}

export default SignalListItem
