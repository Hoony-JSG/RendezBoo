import SignalListItem from './SignalListItem'

const SignalList = ({msgs}) => {
    return (
        <div>
            <h2>SignalList</h2>
            <img src='' className='icon-msg' alt='msg-icon'/>
            <h1>Message</h1>
            <div className ='SignalList'>
                {msgs.map(msg => (
                    <SignalListItem msg={msg} key={msg.id} />
                ))}
            </div>
        </div>
    )  
}

export default SignalList;