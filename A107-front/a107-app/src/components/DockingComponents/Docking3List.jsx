import axios from 'axios'
import { useSelector} from 'react-redux'
import { useNavigate } from 'react-router-dom'

const Docking3List = ({ docking3room, setMultiMeetingRoomSeq }) => {
  const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === 'production' ? 'https://i8a107.p.ssafy.io/' : 'http://localhost:8080/'
  const userSeq = useSelector(
    (state) => state.userInfoReducer.userSeq
  )
  const { title, maleNum, femaleNum } = docking3room
  const navigate = useNavigate()
  const enterMeetingRoom = (multiMeetingRoomSeq) => {
    console.log(typeof(userSeq))
    console.log('나를 이 미팅방-유저 테이블에 추가합니다.')
    axios.post(APPLICATION_SERVER_URL + "api/multi-meetings/"+multiMeetingRoomSeq+'/'+userSeq)
    .then((response)=>{
      console.log(response.data)
      setMultiMeetingRoomSeq(multiMeetingRoomSeq)  
      navigate('/docking3/' + multiMeetingRoomSeq)
    })
    .catch((e)=>{
        console.log(e.message)
        navigate('/error')
    })
    
  }
  const multiMeetingRoomListStyle = {
    boxSizing: 'border-box',
    width: '25%',
    background:
      'linear-gradient(180deg, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 100%)',
    border: '2px solid #FFFFFF',
    filter:
      'drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25)) drop-shadow(0px 0px 5px rgba(0, 0, 0, 0.25))',
    borderRadius: '30px',
  }
  return (
    <div
      style={multiMeetingRoomListStyle}
      onClick={() => enterMeetingRoom(docking3room.multiMeetingRoomSeq)}
    >
      <div>
        <h3>제목: {title}</h3>
        <p>남자 수: {maleNum}</p>
        <p>여자 수: {femaleNum}</p>
      </div>
    </div>
  )
}

export default Docking3List