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
    <div
      className={`${
        props.setBtn === !undefined ? 'mbti-selector-edit' : 'mbti-selector'
      }`}
    >
      {props.setBtn !== undefined ? (
        ''
      ) : (
        <div className="MBTISelector_head">
          <div className="MBTISelector_text">MBTI Selector</div>
        </div>
      )}
      <div
        className={
          props.setBtn === !undefined
            ? 'mbti-selector-item-edit'
            : 'mbti-selector-item'
        }
      >
        <div
          className={
            props.setBtn === !undefined
              ? 'MBTISelector_btn-edit'
              : 'MBTISelector_btn'
          }
        >
          <MBTIBtn changeData={setIE} data1="I" data2="E" />
        </div>
        <div
          className={
            props.setBtn === !undefined
              ? 'MBTISelector_btn-edit'
              : 'MBTISelector_btn'
          }
        >
          <MBTIBtn changeData={setSN} data1="S" data2="N" />
        </div>
        <div
          className={
            props.setBtn === !undefined
              ? 'MBTISelector_btn-edit'
              : 'MBTISelector_btn'
          }
        >
          <MBTIBtn changeData={setTF} data1="T" data2="F" />
        </div>
        <div
          className={
            props.setBtn === !undefined
              ? 'MBTISelector_btn-edit'
              : 'MBTISelector_btn'
          }
        >
          <MBTIBtn changeData={setJP} data1="J" data2="P" />
        </div>
      </div>
    </div>
  )
}

export default MBTISelector
