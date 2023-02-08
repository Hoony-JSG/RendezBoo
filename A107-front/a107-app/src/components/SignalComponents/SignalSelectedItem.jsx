const SignalListItem = ({ chat }) => {
  const { message, createdAt, senderSeq } = chat
  return (
    <div>
      <p>{senderSeq}</p>
      <div>
        <p>{message}</p>
        <p>{createdAt}</p>
      </div>
    </div>
  )
}

export default SignalListItem
