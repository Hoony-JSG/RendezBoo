import React, { useState } from "react";

import { useParams } from "react-router-dom";
import RocketItem from "../components/RocketItem";

const Rocket = () => {
  const MeAndYou = {
    Me: "훈스",
    Inquire: useParams().userid,
  };
  return (
    <div>
      <h1>{useParams().userid}'s Rocket</h1>
      <RocketItem {...MeAndYou} />
    </div>
  );
};

export default Rocket;
