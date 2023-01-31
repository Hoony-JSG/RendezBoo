import React from "react";

const Rocket = ({match}) => {
    return (
        <div>
            <h1>{match.params.userid}'s Rocket</h1>
        </div>
    )
  }

export default Rocket;