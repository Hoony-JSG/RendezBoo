import { useEffect } from 'react'
import { useSelector } from 'react-redux'

const SignalListItem = ({ chat, userSeq, you }) => {
  const { message, createdAt, senderSeq } = chat
  const me = useSelector((state) => state.userInfoReducer.userSeq)

  useEffect(() => {
    console.log('me', me)
    console.log('senderSeq', senderSeq)
    console.log('you', you)
  }, [])

  return (
    <div>
      {senderSeq !== me ? (
        <div style={{display:'flex', flexDirection:'column', alignItems:'flex-start', margin: '10px',}}>
          <p className='your-name'>{you.yourname}</p>
          <div className="your-signal">
            <p>{message}</p>
            {createdAt[3] > 12 ? (
              <p id="time">
                {createdAt[3] - 12}:{createdAt[4]} PM
              </p>
            ) : (
              <p id="time">
                {createdAt[3]}:{createdAt[4]} AM
              </p>
            )}
          </div>
        </div>
      ) : (
        <div className="my-signal">
          {createdAt[3] > 12 ? (
            <p id="time">
              {createdAt[3] - 12}:{createdAt[4]} PM
            </p>
          ) : (
            <p id="time">
              {createdAt[3]}:{createdAt[4]} AM
            </p>
          )}
          <p>{message}</p>
        </div>
      )}
    </div>
  )
}

export default SignalListItem
