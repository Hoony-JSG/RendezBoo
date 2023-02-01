import RocketBtn from "./RocketBtn";

const RocketItem = (props) => {
  const Me = props.Me;
  const Inquire = props.Inquire;
  const Same = props.Same;
  const setTempBorder = {
    border: "1px solid black",
  };
  return (
    <div style={setTempBorder}>
      <h1> 로켓아이템 </h1>
      <div>
        <img src="../img/RocketItemProfileImg.png" />
      </div>
      <h3>Me = {Me}</h3>
      <h5>Inquire = {Inquire}</h5>

      <RocketBtn Same={Same} />
    </div>
  );
};

export default RocketItem;
