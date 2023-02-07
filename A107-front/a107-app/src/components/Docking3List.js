import { useNavigate } from 'react-router-dom'

const Docking3List = ({ docking3room }) => {
  const { title, maleNum, femaleNum } = docking3room
  const navigate = useNavigate()
  const goToPage = (props) => {
    navigate('/' + props)
  }
  const multiMeetingRoomListStyle = {
    boxSizing: 'border-box',
    width: '100%',
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
      onClick={() => goToPage('docking3/' + docking3room.multiMeetingRoomSeq)}
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