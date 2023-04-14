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
    <div>
      {/* <button
        className="next-page-button"
        disabled={!checkAllTrue}
        onClick={props.setNext}
      >
        <i className="fas fa-arrow-right next-page-icon"></i>
        Next Page
      </button> */}
      <button
        className={`NextPage_button ${!checkAllTrue ? 'NextPage_dis' : ''}`}
        data-text="Awesome"
        disabled={!checkAllTrue}
        onClick={props.setNext}
      >
        <span className="NextPage_actual-text">&nbsp;Next&nbsp;</span>
        <span
          className={
            !checkAllTrue ? 'NextPage_dis-text' : 'NextPage_hover-text'
          }
          aria-hidden="true"
        >
          &nbsp;Next&nbsp;
        </span>
      </button>
    </div>
  )
}

export default NextPageButton
