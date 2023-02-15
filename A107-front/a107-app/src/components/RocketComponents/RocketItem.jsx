import { useState } from 'react'
import ProfileImageUploader from './ProfileImageUploader'
import RocketBtn from './RocketBtn'
import userprofile from '../../Images/user-profile.png'
import '../../Styles/Join3RocketItemStyle.css'
import { useEffect } from 'react'

const RocketItem = (props) => {
  const { Me, Inquire, ver, setProfileImage, profileImagePath } = props
  const SRC_URL = 'https://d156wamfkmlo3m.cloudfront.net/'

  // const MeAndYou = {
  //   Me: Me,
  //   Inquire: Inquire,
  //   ver: ver,
  //   BadgeRep: 1,
  // }

  const [image, setimage] = useState(userprofile)
  useEffect(() => {
    if (profileImagePath) {
      console.log('프사있음')
      setimage(SRC_URL + profileImagePath)
    }
  }, [profileImagePath])
  const changeImage = (image) => {
    if (image !== null) {
      console.log('새 이미지를 가져와서 setProfileImage : ' + image)
      setimage(image)
      props.setTrue(true)
    }
  }
  console.log('Btn :' + ver)
  return (
    <div
      className="Join3_whole-container"
      // style={ver === '' ? { height: 'fitContent' } : ''}
    >
      {props.ver === 'Start' && (
        <div className="Join3_profileimage">About Me</div>
      )}
      <div
        className={
          ver === ''
            ? 'Join3_uploader_mainpage'
            : ver === 'Start'
            ? 'Join3_uploader'
            : 'Rocket_uploader'
        }
      >
        <div className="Join3_uiverse">
          <div className="Join3_uiverse-image-card"></div>
          <div className="Join3_default-image-frame">
            <img className="Join3_default-image" src={image} alt={''} />
          </div>
        </div>
        {props.ver !== 'Other' && (
          <div
            className={`${
              props.ver === 'Me'
                ? 'Rocket_me-upload-btn'
                : props.ver === 'Start'
                ? 'Join3_uploader-button'
                : 'Rendezboo_uploader-button'
            }`}
          >
            <ProfileImageUploader
              profileImagePath={props.profileImagePath}
              changeImage={changeImage}
              setProfileImage={setProfileImage}
            />
          </div>
        )}
      </div>

      <RocketBtn mbti={props.mbti} ver={ver} rocketUser={props.rocketUser} />
    </div>
  )
}

export default RocketItem
