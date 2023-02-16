import { useState } from 'react'
import ProfileImageUploader from './ProfileImageUploader'
import RocketBtn from './RocketBtn'
import userprofile from '../../Images/user-profile.png'
import '../../Styles/Join3RocketItemStyle.css'
import { useEffect } from 'react'

const RocketItem = (props) => {
  const { Me, Inquire, ver, setProfileImage, rocketUser } = props
  const SRC_URL = 'https://d156wamfkmlo3m.cloudfront.net/'

  // const MeAndYou = {
  //   Me: Me,
  //   Inquire: Inquire,
  //   ver: ver,
  //   BadgeRep: 1,
  // }

  const [image, setImage] = useState(userprofile)
  useEffect(() => {
    if (props.profileImagePath) {
      console.log('프사있음')
      setImage(SRC_URL + props.profileImagePath)
    }
  }, [props.profileImagePath])

  //사진 업로드하면 일로 들어ㅓ옴 (image)
  const changeImage = (image) => {
    console.log('ProfileImageUploader에서 넘어오는 input에 넣은 : ' + image)
    console.log(props.checkRocket + '?')
    setImage(image)
    props.setTrue(true)
    props.checkFirst(true)
    if (props.checkRocket !== undefined) {
      console.log('checkRocket 트루')
      props.setProfileImage(image)
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
              setProfileImage={props.setProfileImage}
            />
          </div>
        )}
      </div>

      <RocketBtn mbti={props.mbti} ver={ver} rocketUser={props.rocketUser} />
    </div>
  )
}

export default RocketItem
