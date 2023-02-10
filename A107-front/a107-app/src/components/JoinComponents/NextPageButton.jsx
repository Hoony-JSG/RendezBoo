import { useState } from 'react'
import '../../Styles/NextPageButton.css'

const NextPageButton = (props) => {
  const propsWithoutSetNext = Object.entries(props).reduce(
    (obj, [key, value]) => {
      if (key !== 'setNext') {
        obj[key] = value
      }
      return obj
    },
    {}
  )
  const checkAllTrue = Object.values(propsWithoutSetNext).every(
    (val) => val === true
  )

  return (
    <button
      className="next-page-button"
      disabled={!checkAllTrue}
      onClick={props.setNext}
    >
      <i className="fas fa-arrow-right next-page-icon"></i>
      Next Page
    </button>
  )
}

export default NextPageButton
