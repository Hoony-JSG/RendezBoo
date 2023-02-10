import RocketItem from '../RocketComponents/RocketItem'

import '../../Styles/temp_border_style.css'
import NextPageButton from './NextPageButton'
import { useState } from 'react'
const JoinItem3rd = (props) => {
  const [hasProfileImage, setHasProfileImage] = useState(false)

  return (
    <div>
      <div className="temp_border_style">
        <br />
        J_3_로켓아이템 재활용
        <div>
          <RocketItem ver="Start" setTrue={setHasProfileImage} />
        </div>
      </div>
      <NextPageButton
        hasProfileImage={hasProfileImage}
        setNext={props.setNext}
      />
    </div>
  )
}

export default JoinItem3rd
