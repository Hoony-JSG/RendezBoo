import React from 'react'

import { useParams } from 'react-router-dom'
import RocketBadge from '../components/RocketBadge'
import RocketItem from '../components/RocketItem'

const Rocket = () => {
  const Me = 'wjdgnsxhsl'
  const Inquire = useParams().userid
  const Same = Me === Inquire ? true : false
  const MeAndYou = {
    Me: Me,
    Inquire: Inquire,
    Same: Same,
    BadgeRep: 1,
  }

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
      <div></div>
    </div>
  )
}

export default Rocket
