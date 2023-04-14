import { useEffect, useState } from 'react'
import axios from 'axios'
import { useParams } from 'react-router-dom'
const Userinfo = () => {
  const APPLICATION_SERVER_URL = 'https://i8a107.p.ssafy.io/'
  // process.env.NODE_ENV === 'production'
  //   ? 'https://i8a107.p.ssafy.io/'
  //   : 'http://localhost:8080/'

  const userSeq = useParams().userid
  const [user, setUser] = useState({
    seq: 0,
    email: '',
    city: '',
    gender: false,
    phoneNumber: '',
    name: '',
    profileImagePath: '',
    mbti: '',
    point: 0,
    createdAt: [2023, 1, 1, 0, 0, 0],
    updatedAt: [2023, 1, 1, 0, 0, 0],
    badge: null,
    userInterests: [],
  })

  useEffect(() => {
    axios
      .get(APPLICATION_SERVER_URL + 'api/user/' + userSeq)
      .then((response) => {
        setUser(response.data)
        console.log(response.data)
      })
  }, [])
  return (
    <div>
      <div>이름: {user.name}</div>
      <div>이메일: {user.email}</div>
      <div>지역: {user.city}</div>
      <div>mbti: {user.mbti}</div>
      <div>가입일: {user.createdAt}</div>
    </div>
  )
}
export default Userinfo
