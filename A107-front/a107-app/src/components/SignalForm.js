import { SiRocketdotchat } from "react-icons/si";

const SignalForm = () => {
    return (
        <div>
            {/* <h1>SignalForm</h1> */}
            <form>
                <input placeholder="메시지를 입력하세요" />
                <button type="submit"><SiRocketdotchat/></button>
            </form>
        </div>
    )
  }

export default SignalForm;