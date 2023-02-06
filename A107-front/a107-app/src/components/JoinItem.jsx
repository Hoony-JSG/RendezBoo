import { useState } from 'react'
import JoinItem1st from './JoinItem1st'
import JoinItem2nd from './JoinItem2nd'
import JoinItem3rd from './JoinItem3rd'
import JoinItem4rd from './JoinItem4rd'
import { BiPlanet } from 'react-icons/bi'

const JoinItem = () => {
  const ItemList = [
    '',
    <JoinItem1st />,
    <JoinItem2nd />,
    <JoinItem3rd />,
    <JoinItem4rd />,
  ]
  const [order, setOrder] = useState(1, '')

  const handleSetNextNumber = () => {
    setOrder(order + 1)
  }

  const handleSetPreviousNumber = () => {
    setOrder(order - 1)
  }
  const renderThatComponent = () => {
    for (let i = 1; i <= 4; i++) {
      if (i === order) {
        return <div>{ItemList[i]}</div>
      }
    }
  }

  return (
    <div>
      <div>
        <div>{renderThatComponent()}</div>
        <button
          type="button"
          disabled={order === 1}
          onClick={handleSetPreviousNumber}
        >
          이전
        </button>
        <button
          type="button"
          disabled={order === 4}
          onClick={handleSetNextNumber}
        >
          다음
        </button>
      </div>

      <button
        type="button"
        onClick={() => {
          setOrder(1)
        }}
      >
        <BiPlanet />
      </button>
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
