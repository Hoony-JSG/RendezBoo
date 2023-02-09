import RocketItem from '../RocketComponents/RocketItem'
import JoinItemBirth from './JoinItemBirth'
import '../../Styles/temp_border_style.css'
import NextPageButton from './NextPageButton'
import { useState } from 'react'
const JoinItem3rd = (props) => {
  const [hasProfileImage, setHasProfileImage] = useState(false)
  const [hasBirth, setHasBirth] = useState(true)
  return (
    <div>
      <div className="temp_border_style">
        <br />
        J_3_로켓아이템 재활용
        <div>
          <RocketItem ver="Start" setTrue={setHasProfileImage} />
        </div>
      </div>
      <div className="temp_border_style">
        <JoinItemBirth setHas={setHasBirth} />
      </div>
      <NextPageButton
        hasProfileImage={hasProfileImage}
        hasBirth={hasBirth}
        setNext={props.setNext}
      />
    </div>
  )
}

export default JoinItem3rd
