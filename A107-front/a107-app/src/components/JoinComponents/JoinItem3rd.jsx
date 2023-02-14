import RocketItem from '../RocketComponents/RocketItem'
import JoinItemBirth from './JoinItemBirth'
import '../../Styles/temp_border_style.css'
import '../../Styles/JoinItem3Style.css'
import NextPageButton from './NextPageButton'
import { useState } from 'react'

const JoinItem3rd = (props) => {
  const [hasProfileImage, setHasProfileImage] = useState(false)
  const [hasBirth, setHasBirth] = useState(false)
  const wholeHas = [
    [hasProfileImage, setHasProfileImage],
    [hasBirth, setHasBirth],
  ]

  const setWholeHas = (index, value) => {
    wholeHas[index][1](value)
  }

  return (
    <div className="Join3_display">
      <div className="Join3_whole-block">
        <div
          className={
            'Join3_left-box ' + (hasProfileImage ? 'Join3_border-glow' : '')
          }
        >
          <RocketItem
            ver="Start"
            profileImagePath={props.profileImagePath}
            mbti={props.mbti}
            setTrue={setHasProfileImage}
          />
        </div>
        <div className="Join3_right-box">
          <div
            className={
              'Join3_right-top-box ' + (hasBirth ? 'Join3_border-glow' : '')
            }
          >
            <JoinItemBirth birthday={props.birthday} setHas={setHasBirth} />
          </div>
          <div className="Join3_right-bottom-box">
            <div className="Join3_next-button">
              <NextPageButton
                hasProfileImage={hasProfileImage}
                hasBirth={hasBirth}
                setNext={props.setNext}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default JoinItem3rd
