import { useEffect, useState } from 'react'
import '../../Styles/ProfileImageUploaderStyle.css'
const ProfileImageUploader = (props) => {
  const [newImage, setNewImage] = useState(null)
  const handleImageUpload = (e) => {
    console.log('새로운 이미지를 input함')
    setNewImage(URL.createObjectURL(e.target.files[0]))
  }

  useEffect(() => {
    props.profileimage(newImage)
  }, [newImage])

  return (
    <div>
      <input
        className="Rocket_profile-iamge-upload-button"
        type="file"
        onChange={handleImageUpload}
      />
    </div>
  )
}

export default ProfileImageUploader
