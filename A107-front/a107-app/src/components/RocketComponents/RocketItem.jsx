import { useState } from 'react'
import ProfileImageUploader from './ProfileImageUploader'
import RocketBtn from './RocketBtn'
import userprofile from '../../Images/user-profile.png'
import '../../Styles/Join3RocketItemStyle.css'

const RocketItem = (props) => {
  const Me = props.Me
  const Inquire = props.Inquire
  const ver = props.ver

  const MeAndYou = {
    Me: Me,
    Inquire: Inquire,
    ver: ver,
    BadgeRep: 1,
  }

  const [profileimage, setprofileimage] = useState(userprofile)
  const changeImage = (image) => {
    if (image !== null) {
      console.log('새 이미지를 가져와서 setProfileImage : ' + image)
      setprofileimage(image)
      props.setTrue(true)
    }
  }
  console.log('Btn :' + ver)
  return (
    <div>
      {props.ver === 'Start' && (
        <div className="Join3_profileimage">About Me</div>
      )}
      <div className="Join3_uploader">
        <div className="Join3_uiverse">
          <div className="Join3_uiverse-image-card"></div>
          <div className="Join3_default-image-frame">
            <img className="Join3_default-image" src={profileimage} />
          </div>
        </div>
        {props.ver === 'Start' && (
          <div className="Join3_uploader-button">
            <ProfileImageUploader
              profileImagePath={props.profileImagePath}
              profileimage={changeImage}
            />
          </div>
        )}
      </div>

      <RocketBtn mbti={props.mbti} {...MeAndYou} />
    </div>
  )
}

export default RocketItem
