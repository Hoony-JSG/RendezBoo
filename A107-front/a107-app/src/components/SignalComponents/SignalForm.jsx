import { useState } from 'react'
import { SiRocketdotchat } from 'react-icons/si'
import { useDispatch } from 'react-redux'
import addSignal from '../../modules/signal'

const SignalForm = () => {
  const dispatch = useDispatch()
  // const inputs = useSelector((state) => state.input);

  const [signal, setSignal] = useState('')
  const inputSignal = (e) => {
    setSignal(e.target.value)
    console.log(e.target.value)
  }
  const sendSignal = (e) => {
    e.preventDefault()
    dispatch(addSignal(signal))
    setSignal('')
  }

  const formStyle = {
    boxSizing: 'border-box',
    width: '100%',
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    background:
      'linear-gradient(180deg, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 100%)',
    border: '2px solid #FFFFFF',
    filter:
      'drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25)) drop-shadow(0px 0px 5px rgba(0, 0, 0, 0.25))',
    borderRadius: '50px',
  }
  const inputStyle = {
    width: '90%',
    background: 'none',
    color: 'white',
    fontFamily: 'Gmarket_light',
    border: 'none',
    margin: '10px',
  }
  const btnStyle = {
    background: 'none',
    color: 'white',
    border: 'none',
  }

  return (
    <div>
      <form onSubmit={sendSignal} style={formStyle}>
        <input
          placeholder="메시지를 입력하세요"
          value={signal}
          onChange={inputSignal}
          style={inputStyle}
        />
        <button
          type="submit"
          style={btnStyle}
          // onClick
        >
          <SiRocketdotchat />
        </button>
      </form>
    </div>
  )
}

export default SignalForm
