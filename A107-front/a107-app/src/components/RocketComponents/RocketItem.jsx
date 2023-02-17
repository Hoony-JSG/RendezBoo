import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router'
import { useSelector } from 'react-redux'
import ProfileImageUploader from './ProfileImageUploader'
import RocketBtn from './RocketBtn'
import userprofile from '../../Images/user-profile.png'
import '../../Styles/Join3RocketItemStyle.css'

const RocketItem = (props) => {
  const navigate = useNavigate()
  const userSeq = useSelector((state) => state.userInfoReducer.userSeq)

  const { Me, Inquire, ver, setProfileImage, rocketUser } = props
  const SRC_URL = 'https://d156wamfkmlo3m.cloudfront.net/'

  const [image, setImage] = useState(userprofile)
  useEffect(() => {
    if (props.profileImagePath && ver !== 'Start') {
      setImage(SRC_URL + props.profileImagePath)
      console.log(props.profileImagePath)
    }
  }, [props.profileImagePath])


  const changeImage = (image) => {
    const blobUrl = URL.createObjectURL(image)
    setImage(blobUrl)
    props.setTrue(true)
    if (props.checkFirst !== undefined) props.checkFirst(true)
    if (props.checkRocket !== undefined) {
      // props.setProfileImage(image)
    }
  }

  return (
    <div className="Join3_whole-container">
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
            <img
              className="Join3_default-image"
              src={image}
              alt={''}
              onClick={() => navigate(`/rocket/${userSeq}`)}
            />
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

      <RocketBtn mbti={props.mbti} ver={ver} rocketUser={rocketUser} />
    </div>
  )
}

export default RocketItem
