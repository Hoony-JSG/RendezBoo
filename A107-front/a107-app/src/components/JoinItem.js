<<<<<<< Updated upstream
const JoinItem = () => {
  console.log('조인아이템')
  return <div> 조인 아이템 </div>
=======
import { useState } from 'react'
import JoinItem_1st_SocialJoin from './JoinItem_1st_SocialJoin'
import JoinItem_2nd_PhoneNumber from './JoinItem_2nd_PhoneNumber'
import JoinItem_3rd_RocketItem from './JoinItem_3rd_RocketItem'
import JoinItem_4th_Likes from './JoinItem_4th_Likes'
import { BiPlanet } from 'react-icons/bi'

const JoinItem = () => {
  const ItemList = [
    '',
    <JoinItem_1st_SocialJoin />,
    <JoinItem_2nd_PhoneNumber />,
    <JoinItem_3rd_RocketItem />,
    <JoinItem_4th_Likes />,
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
>>>>>>> Stashed changes
}

export default JoinItem
