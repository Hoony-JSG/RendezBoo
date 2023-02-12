import React from 'react'
import { useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'
import RocketBadge from '../components/RocketComponents/RocketBadge'
import RocketItem from '../components/RocketComponents/RocketItem'
import RocketRadar from '../components/RocketComponents/RocketRadar'

const Rocket = () => {
  const userid = useSelector((state) => state.userInfoReducer.userEmail)

  const Me = userid
  const Inquire = useParams().userid
  const ver = Me === Inquire ? 'Me' : 'Other'
  const MeAndYou = {
    Me: Me,
    Inquire: Inquire,
    ver: ver,
    BadgeRep: 1,
  }
  console.log(ver)

  return (
    <div>
      <div></div>
      <h1>{useParams().userid}'s Rocket</h1>
      <div>
        <RocketItem {...MeAndYou} />
      </div>
      <div>
        <RocketBadge {...MeAndYou} />
      </div>
      <div>
        <RocketRadar />
      </div>
    </div>
  )
}

export default Rocket
