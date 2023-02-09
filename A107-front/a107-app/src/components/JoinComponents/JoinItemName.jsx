import { useState } from 'react'

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
    <div>
      이름 [2~5자]
      <br />
      <input
        type="text"
        placeholder="Enter your name"
        value={name}
        onChange={(e) => {
          setChange(e.target.value)
        }}
      />
    </div>
  )
}

export default JoinItemName
