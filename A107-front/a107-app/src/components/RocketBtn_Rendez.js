import { useNavigate } from 'react-router-dom'

const RocketBtn_Rendez = (props) => {
  const navigate = useNavigate()
  const id = props.id
  function toMyRocket() {
    console.log('아이템 상자')
    navigate('/rocket/' + id)
  }
  return (
    <div>
      <button
        onClick={(e) => {
          toMyRocket()
        }}
      >
        MyRocket
      </button>
    </div>
  )
}

export default RocketBtn_Rendez
