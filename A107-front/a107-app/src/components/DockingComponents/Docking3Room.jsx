import { useState } from "react"
import axios from "axios"
import { useSelector } from "react-redux"
import { useNavigate} from 'react-router-dom'

const Docking3Room = () => {
    const APPLICATION_SERVER_URL =
    process.env.NODE_ENV === 'production'
      ? 'https://i8a107.p.ssafy.io'
      : 'http://localhost:8080'
    const userSeq = useSelector(
        (state) => state.userInfoReducer.userSeq
    )
    const navigate = useNavigate()
    const [title, setTitle] = useState('')

    const titleInput = (e) => {
        setTitle(e.target.value)
    }
    const makeRoom = (e) => {
        e.preventDefault()
        if (title.trim()) {
            axios.post(`${APPLICATION_SERVER_URL}/api/multi-meetings/`, {
                userSeq: userSeq,
                title: title,
            }).then((response) => {
                const multiMeetingRoomSeq = response.data
                navigate(`/docking3/${multiMeetingRoomSeq}`)
            })
          }
        setTitle('')
    }

    return (
        <div>
            <form action="" onClick={makeRoom}>
        <input
            placeholder="방 제목을 입력하세요"
            value={title}
            onChange={titleInput}
        />
            <button type="submit">
                미팅방만들기
            </button>
            </form>
        </div>
    )
}

export default Docking3Room