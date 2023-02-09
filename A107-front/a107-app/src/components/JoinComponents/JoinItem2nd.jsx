import '../../Styles/temp_border_style.css'
import JoinItemBirth from './JoinItemBirth'
import JoinItemName from './JoinItemName'
import JoinItemPhoneNumber from './JoinItemPhoneNumber'
import JoinItemGender from './JoinItemGender'
import { useState } from 'react'

const JoinItem2nd = () => {
  const [hasName, setHasName] = useState(false)
  const [hasGender, setHasGender] = useState(false)
  const [hasBirth, setHasBirth] = useState(false)
  const [hasPhoneNumber, setHasPhoneNumber] = useState(false)
  const wholeHas = [
    [hasName, setHasName],
    [hasGender, setHasGender],
    [hasBirth, setHasBirth],
    [hasPhoneNumber, setHasPhoneNumber],
  ]
  const setWholeHas = (index, value) => {
    console.log(index, value)
    wholeHas[index][1](value)
  }

  return (
    <div>
      J_2_휴대폰 인증 및 정보 가져오기
      <br />
      {`hasName : ${hasName} // hasGender : ${hasGender} // hasBirht : ${hasBirth} // hasPhoneNumber : ${hasPhoneNumber}`}
      <div>
        <div className="temp_border_style">
          <JoinItemName setHas={setWholeHas} />
        </div>
        <div className="temp_border_style">
          <JoinItemGender index={1} setHas={setWholeHas} />
        </div>
        <div className="temp_border_style">
          <JoinItemBirth index={2} setHas={setWholeHas} />
        </div>
        <div className="temp_border_style">
          <JoinItemPhoneNumber index={3} setHas={setWholeHas} />
        </div>
      </div>
    </div>
  )
}

export default JoinItem2nd
