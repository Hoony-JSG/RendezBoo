import React from 'react'
import { useNavigate } from 'react-router-dom'
import RocketBtn from '../components/RocketBtn'
import RocketItem from '../components/RocketItem'
import { BsFillDoorClosedFill, BsFillDoorOpenFill } from 'react-icons/bs'
const Rendezboo = () => {
  console.log('랑데부 페이지')
  const id = 'wjdgnsxhsl'
  const navigate = useNavigate()
  const goToPage = (props) => {
    navigate('/' + props)
  }
  const Contents = {
    border: '2px solid red',
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '0px',
  }
  const MeAndYou = {
    Me: id,
    Inquire: id,
    Rendez: true,
    BadgeRep: 1,
  }
  const doorIconStyle = {
    height: '100px',
    width: '100px',
    display: 'flex',
  }
  const doorIconLayoutStyle = {
    width: '400px',
    height: '200px',
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '0px',
  }
  const doorTripleIconLayoutStyle = {
    width: '400px',
    height: '200px',
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '0px',
  }
  const rocketItemSizeStyle = {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '0px',
    width: '200px',
    height: '400px',
  }
  return (
    <div style={Contents}>
      <div>
        <button
          type="button"
          onClick={(e) => {
            goToPage('Docking1')
          }}
        >
          <div style={doorIconLayoutStyle}>
            <BsFillDoorOpenFill style={doorIconStyle} />
          </div>
          Doking1
        </button>
      </div>
      <div style={rocketItemSizeStyle}>
        <RocketItem {...MeAndYou} />
      </div>
      <div>
        <button
          type="button"
          onClick={(e) => {
            goToPage('Docking3')
          }}
        >
          <div style={doorTripleIconLayoutStyle}>
            <BsFillDoorClosedFill style={doorIconStyle} />
            <BsFillDoorOpenFill style={doorIconStyle} />
            <BsFillDoorClosedFill style={doorIconStyle} />
          </div>
          Doking3
        </button>
      </div>
    </div>
  )
}

export default Rendezboo
