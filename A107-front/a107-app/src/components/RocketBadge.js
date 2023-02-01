import RocketBadgeList from "./RocketBadgeList";
import RocketBadgeRep from "./RocketBadgeRep";

const RocketBadge = (props) => {
  console.log(props.Same ? "내꺼" : "다른사람꺼");
  const WholeFrame = {
    border: "1px solid blue",
    padding: "5px",
    height: "200px",
  };
  const BadgeDes = {
    border: "1px solid black",
    height: props.Same ? "50px" : "100px",
  };
  console.log("뱃지 프레임");
  return (
    <div style={WholeFrame}>
      <RocketBadgeRep />
      {(props.Same && (
        <div>
          <RocketBadgeList />
          <div style={BadgeDes}>뱃지 설명 작게</div>{" "}
        </div>
      )) ||
        (!props.Same && <div style={BadgeDes}>뱃지 설명 크게</div>)}
      {/* {props.Same && <RocketBadgeList /> && (
        <div style={BadgeDes}>뱃지 설명 작게</div>
      )}
      {!props.Same && <div style={BadgeDes}>뱃지 설명 크게</div>} */}
    </div>
  );
};

export default RocketBadge;
