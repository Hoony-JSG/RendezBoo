import { useState } from 'react'
import MBTISelector from './MBTISelector'
import ProfileImageUploader from './ProfileImageUploader'

const RocketBtn_Start = () => {
  const [NickName, setNickName] = useState('')
  return (
    <div>
      <span>
        닉네임 입력 : <br />
      </span>
      <input
        type="text"
        placeholder="New NickName"
        value={NickName}
        onChange={(e) => setNickName(e.target.value)}
      />
      <br />
      <div>
        <MBTISelector />
      </div>
    </div>
  )
}

export default RocketBtn_Start
