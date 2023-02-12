import { useNavigate } from 'react-router-dom'
import '../../Styles/RocketItemButtonStyle.css'
const RocketBtn_Diff = (props) => {
  const navigate = useNavigate()
  function doSignal() {
    console.log('메세지창')
    navigate('/signal')
  }
  function deleteFriend() {
    console.log('친구 삭제')
  }
  function report() {
    console.log('신고하기')
    navigate('/report')
  }
  return (
    <div>
      <button className="RocketItemButton" onClick={doSignal}>
        Message
      </button>
      <button className="RocketItemButton" onClick={deleteFriend}>
        친구 삭제
      </button>
      <button className="RocketItemButton" onClick={report}>
        신고하기
      </button>
    </div>
  )
}

export default RocketBtn_Diff
