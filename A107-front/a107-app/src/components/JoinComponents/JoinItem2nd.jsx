import '../../Styles/temp_border_style.css'

import JoinItemName from './JoinItemName'
import JoinItemPhoneNumber from './JoinItemPhoneNumber'
import JoinItemGender from './JoinItemGender'
import { useState } from 'react'
import NextPageButton from './NextPageButton'
import JoinPassword from './JoinPassword'

const JoinItem2nd = (props) => {
  const [hasName, setHasName] = useState(true)
  const [hasGender, setHasGender] = useState(false)
  const [hasPhoneNumber, setHasPhoneNumber] = useState(true)
  const [hasPW, setHasPW] = useState(false)
  const wholeHas = [
    [hasName, setHasName],
    [hasGender, setHasGender],
    [hasPhoneNumber, setHasPhoneNumber],
    [hasPW, setHasPW],
  ]

  const setWholeHas = (index, value) => {
    console.log(index, value)
    wholeHas[index][1](value)
  }
  return (
    <div>
      J_2_휴대폰 인증 및 정보 가져오기
      <br />
      {`hasName : ${hasName} // hasGender : ${hasGender} // hasPhoneNumber : ${hasPhoneNumber}`}
      <div>
        <div>
          <div className="temp_border_style">
            <JoinItemName setHas={setWholeHas} />
          </div>
          <div className="temp_border_style">
            <JoinPassword setHas={setWholeHas} />
          </div>
          <div className="temp_border_style">
            <JoinItemGender setHas={setWholeHas} />
          </div>
          <div className="temp_border_style">
            <JoinItemPhoneNumber setHas={setWholeHas} />
          </div>
        </div>
        <div>
          <NextPageButton
            hasName={hasName}
            hasGender={hasGender}
            hasPhoneNumber={hasPhoneNumber}
            setNext={props.setNext}
          />
        </div>
      </div>
    </div>
  )
}

export default JoinItem2nd
