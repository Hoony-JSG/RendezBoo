import RocketItem from '../RocketComponents/RocketItem'
import JoinItemBirth from './JoinItemBirth'
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
  const [profileImagePath, setProfileImagePath] = useState()

  const checkProfileImage = (e) => {
    const blob = new Blob([e], { type: e.type })
    const blobUrl = URL.createObjectURL(blob)
    props.setProfileImage(blobUrl)
    setProfileImagePath('images/' + e.name)
    console.log('Join3의 프로필 이미지 데이터')
    console.log(e)
    console.log(blobUrl)
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
            setProfileImage={checkProfileImage}
            mbti={props.mbti}
            setTrue={setHasProfileImage}
            profileImagePath={profileImagePath}
          />
        </div>
        <div className="Join3_right-box">
          <div
            className={
              'Join3_right-top-box ' + (hasBirth ? 'Join3_border-glow' : '')
            }
          >
            <div className="Join3_birth-title">Birth</div>
            <div className="Join3_birth-container">
              <JoinItemBirth birthday={props.birthday} setHas={setHasBirth} />
            </div>
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
