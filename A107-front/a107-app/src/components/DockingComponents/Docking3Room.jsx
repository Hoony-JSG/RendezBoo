import { useState } from "react"
import axios from "axios"
import { useSelector } from "react-redux"

const Docking3Room = () => {
    const userSeq = useSelector(
        (state) => state.userInfoReducer.userSeq
    )
    const [title, setTitle] = useState('')

    const titleInput = (e) => {
        setTitle(e.target.value)
    }
    const makeRoom = (e) => {
        e.preventDefault()
        if (title.trim()) {
            axios.post('https://i8a107.p.ssafy.io/api/multi-meetings/', {
                userSeq: userSeq,
                title: title,
            }).then((response) => {
              console.log(response.data)
              axios.post('https://i8a107.p.ssafy.io/api/multi-meetings/'+response.data+'/'+userSeq)
            }).then(() => {
                
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