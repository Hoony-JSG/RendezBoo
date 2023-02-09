import JoinItemInterests from './JoinItemInterests'
import NextPageButton from './NextPageButton'

const JoinItem4rd = (props) => {
  return (
    <div>
      <div>
        J_4_관심사
        <br />
        <div>
          <JoinItemInterests />
        </div>
      </div>
      <NextPageButton setNext={props.setNext} />
    </div>
  )
}

export default JoinItem4rd
