import { useEffect, useState } from 'react'
import '../../Styles/MBTISelectorStyle.css'
import MBTIBtn from './MBTIBtn'
const MBTISelector = (props) => {
  // const [MBTI, setMBTI] = useState({
  //   I: 'I',
  //   S: 'S',
  //   F: 'F',
  //   J: 'J',
  // })

  const [IE, setIE] = useState('I')
  const [SN, setSN] = useState('S')
  const [TF, setTF] = useState('T')
  const [JP, setJP] = useState('J')
  const updateMBTI = () => {
    console.log('MBTI 수ㅜ정 : ' + IE + SN + TF + JP)
    props.mbti(IE + SN + TF + JP)
  }

  useEffect(updateMBTI, [IE, SN, TF, JP])

  return (
    <div className="mbti-selector">
      <div className="MBTISelector_head">
        <div className="MBTISelector_text">MBTI Selector</div>
      </div>
      <div className="mbti-selector-item">
        <div className="MBTISelector_btn">
          <MBTIBtn changeData={setIE} data1="I" data2="E" />
        </div>
        <div className="MBTISelector_btn">
          <MBTIBtn changeData={setSN} data1="S" data2="N" />
        </div>
        <div className="MBTISelector_btn">
          <MBTIBtn changeData={setTF} data1="T" data2="F" />
        </div>
        <div className="MBTISelector_btn">
          <MBTIBtn changeData={setJP} data1="J" data2="P" />
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
