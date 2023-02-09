import { useParams } from 'react-router-dom' 
import axios from 'axios'
import { useEffect, useState } from 'react'
import Docking3List from '../components/DockingComponents/Docking3List' 
import Docking3WaitingMeeting from '../components/Docking3WaitingMeeting'


const Docking3 = ()=>{
    const [multiMeetingRoomSeq, setMultiMeetingRoomSeq] = useState(null)
    const [docking3List, setDocking3List] = useState([])
    useEffect(()=>{
        axios.get('http://localhost:8080/api/multi-meetings/').then((response)=>{
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
                    <Docking3WaitingMeeting multiMeetingRoomSeq={multiMeetingRoomSeq} />    
                ):(
                <div className="content">
                    <h1>현재 운영중인 미팅방 리스트들입니다</h1>
                    {docking3List.map((docking3room) => (
                        <Docking3List
                        docking3room={docking3room}
                        key={docking3room.multiMeetingRoomSeq}
                        setMultiMeetingRoomSeq={setMultiMeetingRoomSeq}/>
                    ))}
                </div>
                )
            }
            
        </div>
    )
}
export default Docking3
