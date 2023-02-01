import React from "react";
import { Route, Routes, useNavigate } from "react-router-dom";
// import { Userinfo } from "../pages";
import Userinfo from "../pages/Userinfo";

const RocketBtn_Diff = () => {
  const navigate = useNavigate();
  function doSignal() {
    console.log("메세지창");
    navigate("/signal");
  }
  function deleteFriend() {
    console.log("친구 삭제");
  }
  function report() {
    console.log("신고하기");
    navigate("/report");
  }
  return (
    <div>
      <button onClick={doSignal}>Message</button>
      <button onClick={deleteFriend}>친구 삭제</button>
      <button onClick={report}>신고하기</button>
    </div>
  );
};

export default RocketBtn_Diff;
