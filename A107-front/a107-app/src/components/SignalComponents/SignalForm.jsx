import { useState } from 'react'
import axios from 'axios'
import { SiRocketdotchat } from 'react-icons/si'

const SignalForm = () => {

  const [signal, setSignal] = useState('')
  const inputSignal = (e) => {
    setSignal(e.target.value)
  }
  const sendSignal = (e) => {
    e.preventDefault()
    if (signal.trim()) {
      axios.post().then((response) => {
        console.log(response.data)
      })
    }
    setSignal('')
  }

  return (
    <form onSubmit={sendSignal} className={"signal-form"}>
      <input
        placeholder={"메시지를 입력하세요"}
        value={signal}
        onChange={inputSignal}
      />
      <button type={"submit"}>
        <SiRocketdotchat />
      </button>
    </form>
  )
}

export default SignalForm
