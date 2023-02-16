import { useCallback, useState } from 'react'

const Stick = (props) => {
  const {
    client,
    subscribers,
    userSeq,
    multiMeetingRoomSeq,
    userName,
    userGender,
    stickBody,
  } = props

  const [selected, setSelected] = useState(false)

  const pubStick = (e) => {
    // 연결이 안되어있을 경우
    if (!client || !client.current.connected) {
      alert('연결 상태를 확인해주세요.')
      return
    }

    const body = {
      multiMeetingRoomSeq: multiMeetingRoomSeq,
      userSeq: userSeq,
      targetSeq: e.target.id,
    }
    const json_body = JSON.stringify(body)

    client.current.publish({
      destination: '/pub/stick',
      body: json_body,
    })
    setSelected(true)
  }

  const getUserName = useCallback(
    (seq) => {
      if (userSeq == seq) {
        return userName
      } else {
        return subscribers.filter((sub) => sub.userSeq == seq)[0].userName
      }
    },
    [subscribers]
  )

  return (
    <div className={'game-modal'}>
      <h1>사랑의 작대기</h1>
      {selected ? (
        stickBody ? (
          userSeq == stickBody.targets[stickBody.targets[userSeq]] ? (
            <div> 매칭에 성공하셨습니다. </div>
          ) : (
            <div> 매칭에 실패하셨습니다. </div>
          )
        ) : (
          <div>선택하셨습니다.</div>
        )
      ) : (
        <div>
          <p>상대방을 선택하세요</p>
          <div>
            <div>
              {subscribers
                .filter((sub) => sub.userGender !== userGender)
                .map((sub, idx) => (
                  <div key={idx}>
                    <button onClick={pubStick} id={sub.userSeq}>
                      {sub.userName}
                    </button>
                  </div>
                ))}
            </div>
          </div>
        </div>
      )}
    </div>
  )
}
export default Stick
