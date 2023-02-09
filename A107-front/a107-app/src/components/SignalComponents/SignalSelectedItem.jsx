const SignalListItem = ({ chat, userSeq, you }) => {
  const { message, createdAt, senderSeq } = chat

  return (
    <div>
      { senderSeq !== userSeq ? (
        <div>
          <p>{you.yourname}</p>
        </div>
      ) : null }
      <div>
        <p>{message}</p>
        { createdAt[3] > 12 ? (
          <p>{createdAt[3]-12}:{createdAt[4]} PM</p>
        ) : (
          <p>{createdAt[3]}:{createdAt[4]} AM</p>
        )}
      </div>
    </div>
  )
}

export default SignalListItem
