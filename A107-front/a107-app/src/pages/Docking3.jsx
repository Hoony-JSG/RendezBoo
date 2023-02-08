import axios from 'axios';
import { useEffect, useState } from 'react'
import Docking3List from '../components/Docking3List'

const Docking3 = ()=>{
    const [docking3List, setDocking3List] = useState([]);
    useEffect(()=>{
        axios.get('https://i8a107.p.ssafy.io/api/multi-meetings/').then((response)=>{
            setDocking3List(response.data)
            console.log(response.data)
        })
    }, [])
    //onclick -> docking3 미팅룸 시퀀스가 셋팅됨
    //셋팅 하기 전엔 null
    return(
        <div className='content'>
            <h1>현재 운영중인 미팅방 리스트들입니다</h1>
            {docking3List.map((docking3room) => (
                <Docking3List
                docking3room={docking3room} key={docking3room.multiMeetingRoomSeq}
                />
            ))}
        </div>
    )
}
export default Docking3