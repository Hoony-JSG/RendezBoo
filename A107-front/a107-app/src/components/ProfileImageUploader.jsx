import React, { useEffect, useState } from 'react'

const ProfileImageUploader = ({ profileimage }) => {
  const [newImage, setNewImage] = useState(null)

  const handleImageUpload = (event) => {
    setNewImage(URL.createObjectURL(event.target.files[0]))
  }

  useEffect(() => {
    profileimage(newImage)
  }, [newImage, profileimage])

  return (
    <div className="ProfileImageUploader">
      <label htmlFor="file-input"></label>

      <input type="file" onChange={handleImageUpload} />
    </div>
  )
}

export default ProfileImageUploader
