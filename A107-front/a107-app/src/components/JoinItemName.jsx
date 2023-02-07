import { useState } from 'react'

const JoinItemName = () => {
  const [name, setName] = useState('')
  return (
    <div>
      이름
      <br />
      <input
        type="text"
        placeholder="Enter your name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
    </div>
  )
}

export default JoinItemName
