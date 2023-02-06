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
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    padding: '0px',
    border: '1px solid white',
    backdropFilter: 'blur(10px)',
    margin: '30px',
    height: 'auto',
  }
  const MeAndYou = {
    Me: id,
    Inquire: id,
    Rendez: true,
    BadgeRep: 1,
    ver: 'Me',
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
  const onThisButtonStyle = {
    height: '500px',
    display: 'flex',
    alignItems: 'center',
  }
  return (
    <div style={Contents}>
      <div style={onThisButtonStyle}>
        <button
          type="button"
          onClick={(e) => {
            goToPage('Docking1')
          }}
        >
          <div style={doorIconLayoutStyle}>
            <BsFillDoorOpenFill style={doorIconStyle} />
          </div>
          Docking1
        </button>
      </div>
      <div style={onThisButtonStyle}>
        <div style={rocketItemSizeStyle}>
          <RocketItem {...MeAndYou} />
        </div>
      </div>
      <div style={onThisButtonStyle}>
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
          Docking3
        </button>
      </div>
    </div>
  )
}

export default Rendezboo
