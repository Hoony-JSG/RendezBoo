import { useState } from 'react'
import '../../Styles/JoinNameInputStyle.css'

const JoinItemName = (props) => {
  const [name, setName] = useState('')
  const setChange = (newName) => {
    console.log('새 이름 : ' + newName)
    setName(newName)
    if (newName.length >= 2 && newName.length <= 5) {
      props.setHas(0, true)
    } else {
      props.setHas(0, false)
    }
  }

  return (
    <div className="JoinName_container">
      <div className="JoinName_box-discribe">이름을 입력하세요.</div>
      <div className="JoinName_box-input">
        <input
          className="JoinName_name-input"
          type="text"
          placeholder="한글 [2~5자]"
          value={name}
          onChange={(e) => {
            setChange(e.target.value)
          }}
        />
      </div>
    </div>
  )
}

export default JoinItemName
