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
        <p>{createdAt[3]}-{createdAt[4]}</p>
      </div>
    </div>
  )
}

export default SignalListItem
