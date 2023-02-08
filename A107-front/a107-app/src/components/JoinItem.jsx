import { useEffect, useState } from 'react'
import JoinItem1st from './JoinItem1st'
import JoinItem2nd from './JoinItem2nd'
import JoinItem3rd from './JoinItem3rd'
import JoinItem4rd from './JoinItem4rd'
import { BiPlanet } from 'react-icons/bi'
import { NavLink } from 'react-router-dom'

const JoinItem = () => {
  const ItemList = ['', '', <JoinItem2nd />, <JoinItem3rd />, <JoinItem4rd />]
  const [order, setOrder] = useState(2, '')

  const handleSetNextNumber = () => {
    if (order === 4) {
      window.location.href = '/'
    } else {
      setOrder(order + 1)
    }
  }
  const handleSetPreviousNumber = () => {
    setOrder(order - 1)
  }

  return (
    <div>
      <div>
        <div>{ItemList[order]}</div>
        <button
          type="button"
          disabled={order === 2}
          onClick={handleSetPreviousNumber}
        >
          이전
        </button>
        <button type="button" onClick={handleSetNextNumber}>
          다음
        </button>
      </div>

      <button
        type="button"
        onClick={() => {
          setOrder(2)
        }}
      >
        <BiPlanet />
      </button>
      <button
        type="button"
        onClick={() => {
          setOrder(3)
        }}
      >
        <BiPlanet />
      </button>
      <button
        type="button"
        onClick={() => {
          setOrder(4)
        }}
      >
        <BiPlanet />
      </button>
    </div>
  )
}

export default JoinItem
