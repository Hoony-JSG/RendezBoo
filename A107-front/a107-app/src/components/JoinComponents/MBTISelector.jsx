import { useEffect, useState } from 'react'
import '../../Styles/MBTISelectorStyle.css'
import MBTIBtn from './MBTIBtn'
const MBTISelector = (props) => {
  const [MBTI, setMBTI] = useState({
    I: 'I',
    S: 'S',
    F: 'F',
    J: 'J',
  })

  const updateMBTI = (key) => {
    const newMBTI = { ...MBTI }
    console.log(key)
    switch (key) {
      case 'IE':
        newMBTI.I = newMBTI.I === 'I' ? 'E' : 'I'
        break
      case 'SN':
        newMBTI.S = newMBTI.S === 'S' ? 'N' : 'S'
        break
      case 'TF':
        newMBTI.F = newMBTI.F === 'F' ? 'T' : 'F'
        break
      case 'PJ':
        newMBTI.J = newMBTI.J === 'J' ? 'P' : 'J'
        break
      default:
        break
    }
    setMBTI(newMBTI)
    props.mbti(newMBTI.I + newMBTI.S + newMBTI.F + newMBTI.J)
  }

  return (
    <div className="mbti-selector">
      <div className="mbti-selector-item">
        <div className="MBTISelector_btn">
          <MBTIBtn />
        </div>
        {/* 
        <div>I/E</div>
        <button
          className="mbti-selector-item-button"
          onClick={() => updateMBTI('IE')}
        >
          <div className="mbti-selector-item-div">{MBTI.I}</div>
        </button>
      </div>
      <div className="mbti-selector-item">
        <div>S/N</div>
        <button
          className="mbti-selector-item-button"
          onClick={() => updateMBTI('SN')}
        >
          <div className="mbti-selector-item-div">{MBTI.S}</div>
        </button>
      </div>
      <div className="mbti-selector-item">
        <div>F/T</div>
        <button
          className="mbti-selector-item-button"
          onClick={() => updateMBTI('TF')}
        >
          <div className="mbti-selector-item-div">{MBTI.F}</div>
        </button>
      </div>
      <div className="mbti-selector-item">
        <div>J/P</div>
        <button
          className="mbti-selector-item-button"
          onClick={() => updateMBTI('PJ')}
        >
          <div className="mbti-selector-item-div">{MBTI.J}</div>
        </button> */}
      </div>
    </div>
  )
}

export default MBTISelector
