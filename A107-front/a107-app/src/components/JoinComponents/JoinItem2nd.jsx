import '../../Styles/JoinItem2Style.css'

import JoinItemName from './JoinItemName'
import JoinItemPhoneNumber from './JoinItemPhoneNumber'
import JoinItemGender from './JoinItemGender'
import { useEffect, useState } from 'react'
import NextPageButton from './NextPageButton'
import JoinItemSocialEmail from './JoinItemSocialEmail'
import JoinPassword from './JoinPassword'

const JoinItem2nd = (props) => {
  const [hasName, setHasName] = useState(false)
  const [hasGender, setHasGender] = useState(false)
  const [hasPhoneNumber, setHasPhoneNumber] = useState(false)
  const [hasPW, setHasPW] = useState(true)
  const wholeHas = [
    [hasName, setHasName],
    [hasGender, setHasGender],
    [hasPhoneNumber, setHasPhoneNumber],
    [hasPW, setHasPW],
  ]
  const [emailType, setEmailType] = useState()

  const setWholeHas = (index, value) => {
    console.log(index, value)
    wholeHas[index][1](value)
  }

  useEffect(() => {
    setEmailType(props.emailType)
    console.log('헉 type : ' + props.emailType)
  }, [])

  return (
    <div className="display">
      <div className="whole-block">
        <div className="Join2Title">Ready For Rendez-BOO</div>
        <div className="whole-items">
          <div className="left-items">
            {/* <div
              className={'each-items pw-item ' + (hasPW ? 'border-glow' : '')}
              >
              <JoinPassword setHas={setWholeHas} />
            </div> */}
            <div
              className={
                'each-items email-item ' +
                (emailType === '0'
                  ? 'border-glow-naver'
                  : emailType === '1'
                  ? 'border-glow-kakao'
                  : '')
              }
            >
              <JoinItemSocialEmail email={props.email} />
            </div>
            <div
              className={
                'each-items phonenumber-item ' +
                (hasPhoneNumber ? 'border-glow' : '')
              }
            >
              <JoinItemPhoneNumber
                fixedPhoneNumber={props.phoneNumber}
                setHas={setWholeHas}
              />
            </div>
          </div>
          <div className="right-items">
            <div
              className={
                'each-items name-item ' + (hasName ? 'border-glow' : '')
              }
            >
              <JoinItemName fixedName={props.name} setHas={setWholeHas} />
            </div>
            <div
              className={
                'each-items gender-item ' + (hasGender ? 'border-glow' : '')
              }
            >
              <JoinItemGender fixedGender={props.gender} setHas={setWholeHas} />
            </div>
          </div>
        </div>
        <div className="next-button">
          <NextPageButton
            hasName={hasName}
            hasGender={hasGender}
            hasPhoneNumber={hasPhoneNumber}
            hasPW={hasPW}
            setNext={props.setNext}
          />
        </div>
      </div>
    </div>
  )
}

export default JoinItem2nd
