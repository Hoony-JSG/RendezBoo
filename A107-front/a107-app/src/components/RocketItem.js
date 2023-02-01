import React from "react";
import RocketBtn from "./RocketBtn";

const RocketItem = (props) => {
  const Me = props.Me;
  const Inquire = props.Inquire;
  const Same = Me === Inquire ? true : false;
  return (
    <div>
      <h1> 로켓아이템 </h1>
      <div>
        <img src="../img/RocketItemProfileImg.png" />
      </div>
      <h3>Me = {Me}</h3>
      <h5>Inquire = {Inquire}</h5>

      <RocketBtn Same={Same} />
    </div>
  );
};

export default RocketItem;
