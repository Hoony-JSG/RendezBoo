import { useState } from 'react'
import { useLocation } from 'react-router-dom'
import JoinItem2nd from './JoinItem2nd'
import JoinItem3rd from './JoinItem3rd'
import JoinItem4rd from './JoinItem4rd'

const JoinItem = () => {
  const location = useLocation()
  const params = new URLSearchParams(location.search)
  const email = params.get('email')
  const emailType = params.get('type')

  const setNext = () => {
    if (order === 4) {
      window.location.href = '/'
    } else {
      setOrder(order + 1)
    }
  }
  const ItemList = [
    '',
    '',
    <JoinItem2nd setNext={setNext} />,
    <JoinItem3rd setNext={setNext} />,
    <JoinItem4rd setNext={setNext} />,
  ]
  const [order, setOrder] = useState(2, '')
  return <div style={{ marginTop: '20px' }}>{ItemList[order]}</div>
}

export default JoinItem
