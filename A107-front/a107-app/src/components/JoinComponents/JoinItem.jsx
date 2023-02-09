import { useState } from 'react'
import { BiPlanet } from 'react-icons/bi'
import JoinItem2nd from './JoinItem2nd'
import JoinItem3rd from './JoinItem3rd'
import JoinItem4rd from './JoinItem4rd'

const JoinItem = () => {
  const ItemList = ['', '', <JoinItem2nd />, <JoinItem3rd />, <JoinItem4rd />]
  const [order, setOrder] = useState(2, '')

  return (
    <div>
      <div>
        <div>{ItemList[order]}</div>
      </div>
    </div>
  )
}

export default JoinItem
