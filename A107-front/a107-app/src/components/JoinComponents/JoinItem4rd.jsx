import { useState } from 'react'
import JoinItemInterests from './JoinItemInterests'
import NextPageButton from './NextPageButton'

const JoinItem4rd = (props) => {
  const [hasInterests, setHasInterests] = useState(false)
  return (
    <div>
      <div>
        J_4_관심사
        <br />
        <div>
          <JoinItemInterests setTrue={setHasInterests} />
        </div>
      </div>
      <NextPageButton setTrue={hasInterests} setNext={props.setNext} />
    </div>
  )
}

export default JoinItem4rd
