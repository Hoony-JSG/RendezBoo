import React from "react";
import { useParams } from "react-router-dom";
const Userinfo = () => {
  console.log("유저인포 페이지");
  return <div>유저인포 : {useParams().userid}</div>;
};
export default Userinfo;
