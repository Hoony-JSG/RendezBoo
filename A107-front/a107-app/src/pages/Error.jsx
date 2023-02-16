import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'

const Error = ({islogin}) => {
  const navigate = useNavigate()

  useEffect(() => {
    alert('잘못된 접근입니다.')
    if (islogin) navigate(-1)
    else navigate('/')
  }, [])

  return <div></div>
}

export default Error
