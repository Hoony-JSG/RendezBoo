import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

const LoginForm = () => {
  console.log('로그인 폼')

  const realId = 'wjdgnsxhsl@naver.com'
  const realPw = '123123'

  let [id, setId] = useState('')
  let [pw, setPw] = useState('')

  const [button, setButton] = useState(false)

  function changeButton() {
    id.length <= 40 &&
    id.includes('@') &&
    (id.includes('.com') || id.includes('.net')) &&
    pw.length >= 5 &&
    pw.length <= 20
      ? setButton(true)
      : setButton(false)
  }

  const navigate = useNavigate()

  const goToAfterLogin = (props) => {
    console.log(props)
    const idString = props.split('@', 1)
    console.log(idString)
    navigate('/rocket/' + idString)
  }
  const loginButtonStyle = {
    margin: '10px',
    padding: '0',
    width: '100px',
    height: '30px',
    fontsize: '17px',
    borderradius: '5px',
    opacity: button ? 1 : 0.3,
  }

  return (
    <div>
      로그인폼
      <div>
        <input
          placeholder="Eneter ID..."
          id="id"
          onChange={(e) => {
            setId(e.target.value)
          }}
          onKeyUp={changeButton}
        />
      </div>
      <div>
        <input
          type="password"
          placeholder="Enter PW..."
          id="password"
          onChange={(e) => {
            setPw(e.target.value)
          }}
          onKeyUp={changeButton}
        />
      </div>
      <div>
        <button
          style={loginButtonStyle}
          type="button"
          onClick={(e) => {
            if (id === realId) {
              if (pw === realPw) {
                e.stopPropagation()
                goToAfterLogin(id)
              }
            } else {
              alert('Wrong ID or PW')
            }
          }}
        >
          Login
        </button>
      </div>
    </div>
  )
}

export default LoginForm
