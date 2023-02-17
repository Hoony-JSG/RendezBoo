import React, { useEffect, useState } from 'react'
import Xarrow from 'react-xarrows'

const ArrowComponent = (props) => {
  const { subscribers, userSeq, userName, userGender, stickBody } = props
  const [manList, setManList] = useState([])
  const [womanList, setWomanList] = useState([])
  const [targets, setTargets] = useState({})
  const [matched, setMatched] = useState(false)

  useEffect(() => {
    let _manList = subscribers.filter((sub) => sub.userGender)
    let _womanList = subscribers.filter((sub) => !sub.userGender)
    if (userGender) {
      _manList = [
        ..._manList,
        {
          userSeq: userSeq,
          userName: userName,
          userGender: userGender,
        },
      ]
    } else {
      _womanList = [
        ..._womanList,
        {
          userSeq: userSeq,
          userName: userName,
          userGender: userGender,
        },
      ]
    }
    setManList(_manList)
    setWomanList(_womanList)
    setTargets(stickBody.targets)
    setMatched(userSeq == stickBody.targets[stickBody.targets[userSeq]])
  }, [stickBody])

  const coverStyle = {
    position: 'relative',
    width: '860px',
    height: '620px',
    borderRadius: '50px',
    margin: 'auto',
  }
  const boxStyle = {
    position: 'absolute',
    width: '200px',
    height: '150px',
    border: 'grey solid 2px',
    borderRadius: '10px',
    padding: '5px',
    fontSize: '2rem',
  }
  const pointStyle = {
    position: 'absolute',
    width: '1px',
    height: '1px',
    padding: '0px',
    fontSize: '2rem',
  }
  return (
    <div>
      <div style={coverStyle}>
        <h1>{matched ? '매칭에 성공하였습니다.' : '매칭에 실패하였습니다.'}</h1>
        {manList.map((m, idx) => (
          <div
            key={idx}
            id={'result' + m.userSeq}
            style={{ ...boxStyle, left: '50px', top: 180 * idx + 80 + 'px' }}
          >
            <p>{m.userName}</p>
          </div>
        ))}
        {manList.map((m, idx) => (
          <div
            key={idx}
            id={'result-s' + m.userSeq}
            style={{
              ...pointStyle,
              left: '270px',
              top: 180 * idx + 170 + 'px',
            }}
          ></div>
        ))}
        {manList.map((m, idx) => (
          <div
            key={idx}
            id={'result-e' + m.userSeq}
            style={{
              ...pointStyle,
              left: '270px',
              top: 180 * idx + 140 + 'px',
            }}
          ></div>
        ))}
        {womanList.map((m, idx) => (
          <div
            key={idx}
            id={'result' + m.userSeq}
            style={{ ...boxStyle, right: '50px', top: 180 * idx + 80 + 'px' }}
          >
            <p>{m.userName}</p>
          </div>
        ))}
        {womanList.map((m, idx) => (
          <div
            key={idx}
            id={'result-s' + m.userSeq}
            style={{
              ...pointStyle,
              right: '270px',
              top: 180 * idx + 140 + 'px',
            }}
          ></div>
        ))}
        {womanList.map((m, idx) => (
          <div
            key={idx}
            id={'result-e' + m.userSeq}
            style={{
              ...pointStyle,
              right: '270px',
              top: 180 * idx + 170 + 'px',
            }}
          ></div>
        ))}
        {manList.map((m, idx) => (
          <Xarrow
            key={idx}
            start={'result-s' + m.userSeq}
            end={'result-e' + targets[m.userSeq]}
            color={'blue'}
            curveness={0}
          />
        ))}
        {womanList.map((m, idx) => (
          <Xarrow
            key={idx}
            start={'result-s' + m.userSeq}
            end={'result-e' + targets[m.userSeq]}
            color={'red'}
            curveness={0}
            style={{ transform: 'translate(10px, 0px)' }}
          />
        ))}
      </div>
    </div>
  )
}

export default ArrowComponent
