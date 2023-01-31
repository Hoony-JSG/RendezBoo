import React from "react";
import { useParams } from "react-router-dom";

const Rocket = () => {
    return (
        <div>
            <h1>{useParams().userid}'s Rocket</h1>
        </div>
    )
  }

export default Rocket;