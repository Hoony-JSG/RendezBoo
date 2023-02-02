import RocketBtn_Diff from "./RocketBtn_Diff";
import RocketBtn_Same from "./RocketBtn_Same";

const RocketBtn = (props) => {
  if (props.Same) {
    return (
      <div>
        헉 같다
        <RocketBtn_Same />
      </div>
    );
  } else {
    return (
      <div>
        다르다
        <RocketBtn_Diff />
      </div>
    );
  }
};
export default RocketBtn;
