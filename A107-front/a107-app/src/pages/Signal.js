import React, { useState } from "react";
// import { SignalList, SignalItem, SignalForm } from "../components"
import SignalList from "../components/SignalList";
import SignalItem from "../components/SignalItem";
import SignalForm from "../components/SignalForm";

const Signal = () => {
    const [msgs, setMsgs] = useState([
        {
            id: 1,
            user: 'nickname',
            recentmsg: '너는 별을 보자며 내 손을 당겨서',
            recentsent: 'YYYY-MM-DD',
        },
        {
            id: 2,
            user: 'nickname',
            recentmsg: '너는 별을 보자며 내 손을 당겨서',
            recentsent: 'YYYY-MM-DD',
        },
        {
            id: 3,
            user: 'nickname',
            recentmsg: '너는 별을 보자며 내 손을 당겨서',
            recentsent: 'YYYY-MM-DD',
        },
    ])

    return (
        <div>
            <h1>Signal</h1>
            <SignalList msgs={msgs}/>
            <SignalItem />
            <SignalForm />
        </div>
    )
  }

export default Signal;