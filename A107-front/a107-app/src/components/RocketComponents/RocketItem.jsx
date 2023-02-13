import { useState } from 'react'
import ProfileImageUploader from './ProfileImageUploader'
import RocketBtn from './RocketBtn'
import MangleGom from '../../Images/MangleGom.png'
import '../../Styles/Join3RocketItemStyle.css'

const RocketItem = (props) => {
  const Me = props.Me
  const Inquire = props.Inquire
  const ver = props.ver
  const setTempBorder = {
    border: '1px solid black',
  }
  const [profileimage, setprofileimage] = useState(MangleGom)
  const changeImage = (image) => {
    if (image !== null) {
      console.log('새 이미지를 가져와서 setProfileImage : ' + image)
      setprofileimage(image)
      props.setTrue(true)
    }
  }
  console.log('Btn :' + ver)
  return (
    <div style={setTempBorder}>
      <div className="Join3_profileimage">프로필 이미지 업로드</div>
      <div>
        <img src={profileimage} style={{ width: '200px', height: '200px' }} />
        {props.ver === 'Start' && (
          <div>
            <ProfileImageUploader profileimage={changeImage} />
          </div>
        )}
      </div>
      <h3>Me = {Me}</h3>
      <h5>Inquire = {Inquire}</h5>

      <RocketBtn ver={ver} />
    </div>
  )
}

export default RocketItem
