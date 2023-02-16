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
    if (props.profileImagePath && ver !== 'Start') {
      console.log('프사있음')
      setImage(SRC_URL + props.profileImagePath)
      console.log(props.profileImagePath)
    }
  }, [props.profileImagePath])
  useEffect(() => {
    console.log('처음 로딩 이미지 형식 : ')
    console.log(image)
    if (props.checkRocket) {
      // setImage(SRC_URL + props.profileImagePath)
    }
  }, [])

  //profileImageUploader에서 업로드하면 사진 바꾸는 함수 하면 일로 들어ㅓ옴 (image)
  const changeImage = (image) => {
    console.log('ProfileImageUploader에서 넘어오는 input에 넣은 : ')
    console.log(image)
    const blobUrl = URL.createObjectURL(image)
    setImage(blobUrl)
    props.setTrue(true)
    if (props.checkFirst !== undefined) props.checkFirst(true)
    if (props.checkRocket !== undefined) {
      console.log('checkRocket 트루')
      // props.setProfileImage(image)
    }
  }

  useEffect(() => {
    console.log('이미지 바뀜')
    console.log(image)
  }, [image])

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
            : ver === 'Other'
            ? 'Rocket_uploader-other'
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
