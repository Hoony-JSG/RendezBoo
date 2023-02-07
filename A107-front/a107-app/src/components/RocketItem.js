import { useState } from 'react'
import ProfileImageUploader from './ProfileImageUploader'
import RocketBtn from './RocketBtn'

const RocketItem = (props) => {
  const Me = props.Me
  const Inquire = props.Inquire
  const ver = props.ver
  const setTempBorder = {
    border: '1px solid black',
  }
  const [profileimage, setprofileimage] = useState()
  const changeImage = (image) => {
    setprofileimage(image)
    console.log(image)
  }
  console.log('Btn :' + ver)
  return (
    <div style={setTempBorder}>
      <h1> 로켓아이템 </h1>
      <div>
        <img
          src={profileimage || '../img/RocketItemProfileImg.png'}
          style={{ width: '200px', height: '200px' }}
        />
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
