import { useParams } from 'react-router-dom' 
import axios from 'axios'
import { useEffect, useState } from 'react'
import Docking3List from '../components/DockingComponents/Docking3List' 
import Docking3WaitingMeeting from '../components/Docking3WaitingMeeting'
import Docking3Room from '../components/DockingComponents/Docking3Room'


const Docking3 = ()=>{

    const APPLICATION_SERVER_URL =
    process.env.NODE_ENV === 'production' ? 'https://i8a107.p.ssafy.io/' : 'http://localhost:8080/'  
    const multiMeetingRoomSeq = useParams().roomid
    const [docking3List, setDocking3List] = useState([])
    useEffect(()=>{
        axios.get(APPLICATION_SERVER_URL + 'api/multi-meetings/').then((response)=>{
            setDocking3List(response.data)
            console.log(response.data)
        })
    }, [])
    //DockingRoomList를 onclick -> docking3 미팅룸 시퀀스가 셋팅됨
    //셋팅 하기 전엔 null

    return(
        <div className='content'>
            {
                multiMeetingRoomSeq?(
                    <Docking3WaitingMeeting multiMeetingRoomSeq={multiMeetingRoomSeq}/>    
                ):(
                <div className="content">
                    <Docking3Room />
                    <h1>현재 운영중인 미팅방 리스트들입니다</h1>
                    {docking3List.map((docking3room) => (
                        <Docking3List
                        docking3room={docking3room}
                        key={docking3room.multiMeetingRoomSeq}/>
                    ))}
                </div>
                )
            }
            
        </div>
    )
}
export default Docking3
