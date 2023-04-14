import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import axios from 'axios'
import Docking3List from '../components/DockingComponents/Docking3List'
import Docking3WaitingMeeting from '../components/Docking3WaitingMeeting'
import { ImEnter, ImExit } from 'react-icons/im'
import Docking3Enter from '../components/DockingComponents/Docking3enter'

const Docking3 = () => {
  const APPLICATION_SERVER_URL = 'https://i8a107.p.ssafy.io'
  // process.env.NODE_ENV === 'production'
  //   ? 'https://i8a107.p.ssafy.io'
  //   : 'http://localhost:8080'

  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)
  const multiMeetingRoomSeq = useParams().roomid
  const [docking3List, setDocking3List] = useState([])

  const navigate = useNavigate()

  useEffect(() => {
    axios
      .get(`${APPLICATION_SERVER_URL}/api/multi-meetings/`)
      .then((response) => {
        setDocking3List(response.data)
        console.log(response.data)
      })
  }, [])

  const [title, setTitle] = useState('')

  const titleInput = (e) => {
    setTitle(e.target.value)
  }
  const makeRoom = (e) => {
    e.preventDefault()
    if (title.trim()) {
      axios
        .post(`${APPLICATION_SERVER_URL}/api/multi-meetings/`, {
          userSeq: userSeq,
          title: title,
        })
        .then((response) => {
          const multiMeetingRoomSeq = response.data
          navigate(`/docking3/${multiMeetingRoomSeq}`)
        })
    }
    setTitle('')
  }

  return (
    <div>
      {multiMeetingRoomSeq ? (
        <Docking3WaitingMeeting multiMeetingRoomSeq={multiMeetingRoomSeq} />
      ) : (
        <Docking3Enter />
      )}
    </div>
  )
}
export default Docking3
