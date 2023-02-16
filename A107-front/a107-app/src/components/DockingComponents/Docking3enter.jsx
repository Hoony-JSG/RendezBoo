import { useState, useEffect } from 'react'
import axios from 'axios'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import Docking3List from './Docking3List'
import { ImEnter, ImExit } from 'react-icons/im'

const Docking3Enter = () => {
  const APPLICATION_SERVER_URL = 'https://i8a107.p.ssafy.io'

  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)
  const navigate = useNavigate()
  const [docking3List, setDocking3List] = useState([])
  const [title, setTitle] = useState('')

  useEffect(() => {
    axios
      .get(`${APPLICATION_SERVER_URL}/api/multi-meetings/`)
      .then((response) => {
        setDocking3List(response.data)
        console.log(response.data)
      })
  }, [])

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
    <div
      style={{
        height: '90vh',
        background: 'linear-gradient(180deg, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 100%)',
        display: 'flex',
        gap: '30px',
        padding: '30px',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <div className="docking-box">
        <h1 style={{ margin: '30px' }}>3:3 Docking...</h1>
        <p style={{fontSize: '1.5rem'}}>여럿이 함께 미팅을 즐겨보세요!</p>
        <form onSubmit={makeRoom}>
          <input
            placeholder="방 제목을 입력하세요"
            value={title}
            onChange={titleInput}
            style={{
              width: '600px',
              boxSizing: 'border-box',
              color: 'white',
              background:
                'linear-gradient(180deg, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 100%)',
              border: '2px solid #FFFFFF',
              filter:
                'drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25)) drop-shadow(0px 0px 5px rgba(0, 0, 0, 0.25))',
              borderRadius: '30px',
              fontFamily: 'Gmarket_light',
              margin: '10px',
              padding: '15px',
              fontSize: '150%',
              textAlign: 'center',
            }}
          />
          <div
            style={{
              display: 'flex',
              flexDirection: 'row',
              justifyContent: 'center',
              margin: '20px',
            }}
          >
            <button className="ready-enter-btn" type="submit">
              <ImEnter />
              &nbsp;방만들기
            </button>
            <button
              className="ready-exit-btn"
              onClick={() => {
                navigate('/rendezboo')
              }}
            >
              <ImExit />
              &nbsp;돌아가기
            </button>
          </div>
        </form>
      </div>
        {/* <h1>바로 입장해보세요!</h1> */}
      <div className='docking-list'>
        {docking3List.map((docking3room) => (
          <Docking3List
            docking3room={docking3room}
            key={docking3room.multiMeetingRoomSeq}
          />
        ))}
      </div>
    </div>
  )
}

export default Docking3Enter
