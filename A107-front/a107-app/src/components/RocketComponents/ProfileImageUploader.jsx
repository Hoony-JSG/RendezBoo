import { useEffect, useState } from 'react'

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
    <div className="ProfileImageUploader">
      <label htmlFor="file-input"></label>

      <input type="file" onChange={handleImageUpload} />
    </div>
  )
}

export default ProfileImageUploader
