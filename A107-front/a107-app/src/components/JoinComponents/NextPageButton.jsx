import { useState } from 'react'
import '../../Styles/NextPageButton.css'

const NextPageButton = (props) => {
  const setNext = () => {
    console.log('next')
  }
  const checkAllTrue = Object.values(props).every((val) => val === true)

  return (
    <button
      className="next-page-button"
      disabled={!checkAllTrue}
      onClick={setNext}
    >
      <i className="fas fa-arrow-right next-page-icon"></i>
      Next Page
    </button>
  )
}

export default NextPageButton
