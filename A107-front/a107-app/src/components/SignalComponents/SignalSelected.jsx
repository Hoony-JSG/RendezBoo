import SignalForm from './SignalForm';
import SignalSelectedItem from './SignalSelectedItem'

const SignalSelected = ({content}) => {

    return (
        <div>
            <h1>SignalSelected</h1>
            <div>
                {content.map(chat => (
                    <SignalSelectedItem chat={chat} key={chat.chatSeq} />
                ))}
            </div>
            <SignalForm />
        </div>
    )
  }

export default SignalSelected;