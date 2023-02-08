import { useParams } from 'react-router-dom' 
import axios from 'axios'
import { useEffect, useState } from 'react'
import Docking3List from '../components/DockingComponents/Docking3List'
import Docking3Waiting from '../components/Docking3Waiting'
import Docking3Meeting from '../components/Docking3Meeting'


const Docking3 = ()=>{
    const roomid = useParams().roomid
    const [docking3List, setDocking3List] = useState([]);
    const [completeFlag, setCompleteFlag] = useState(false);
    useEffect(()=>{
        axios.get('http://localhost:8080/api/multi-meetings/').then((response)=>{
            setDocking3List(response.data)
            console.log(response.data)
        })
    }, [])
    //DockingRoomList를 onclick -> docking3 미팅룸 시퀀스가 셋팅됨
    //셋팅 하기 전엔 null

    const complete = () => {
        setCompleteFlag(true)
    }

    return(
        <div className='content'>
            {
                roomid?(
                    completeFlag?(
                        <Docking3Meeting roomid={roomid}/>
                    ):(
                        <Docking3Waiting roomid={roomid} complete={complete}/>
                    )
                
                ):(
                <div className="content">
                    <h1>현재 운영중인 미팅방 리스트들입니다</h1>
                    {docking3List.map((docking3room) => (
                        <Docking3List
                        docking3room={docking3room} key={docking3room.multiMeetingRoomSeq}
                        />
                    ))}
                </div>
                )
            }
            
        </div>
    )
}
export default Docking3
