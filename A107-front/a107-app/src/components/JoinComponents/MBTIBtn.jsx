import { useState } from 'react'
import '../../Styles/MBTIBtnStyle.css'

const MBTIBtn = (props) => {
  const data1 = props.data1
  const data2 = props.data2
  const [showedData, setShowedData] = useState()
  const [hidedData, setHidedData] = useState()
  const changeData = () => {
    handlePop()
    if (showedData === data1) {
      setShowedData(data2)
      setHidedData(data1)
      props.changeData(data2)
    } else {
      setShowedData(data1)
      setHidedData(data2)
      props.changeData(data1)
    }
  }

  const [pop, setPop] = useState(true)
  const handlePop = () => {
    setPop(false)
    setTimeout(() => setPop(true), 1000)
  }

  useState(changeData, [])
  return (
    <div className="MBTIBtn_container">
      <button
        className={'MBTIBtn_button ' + (pop ? '' : 'MBTIBtn_prevent-hover')}
        onClick={changeData}
      >
        <div className="MBTIBtn_cover">{showedData}</div>
        <div className="MBTIBtn_pop">{hidedData}</div>
      </button>
    </div>
  )
}

export default MBTIBtn
