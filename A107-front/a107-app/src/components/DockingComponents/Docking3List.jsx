import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { IoMdMale, IoMdFemale } from 'react-icons/io'

const Docking3List = ({ docking3room, setMultiMeetingRoomSeq }) => {
  const { title, maleNum, femaleNum, multiMeetingRoomSeq } = docking3room
  const userGender = useSelector((state) => state.userInfoReducer.userGender)
  const navigate = useNavigate()
  const enterMeetingRoom = () => {
    if (
      (userGender === true && maleNum >= 3) ||
      (userGender === false && femaleNum >= 3)
    ) {
      alert('빈 자리가 없습니다. 다른 미팅방을 이용해주세요.')
    } else {
      //navigate('/docking3/' + multiMeetingRoomSeq)
      setMultiMeetingRoomSeq(multiMeetingRoomSeq)
    }
  }

  return (
    <div
      className='docking-item'
      onClick={() => enterMeetingRoom(multiMeetingRoomSeq)}
    >
        <h2>{title}</h2>
        <p><IoMdMale/> {maleNum}</p>
        <p><IoMdFemale/> {femaleNum}</p>
    </div>
  )
}

export default Docking3List
