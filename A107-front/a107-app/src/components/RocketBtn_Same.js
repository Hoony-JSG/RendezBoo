import { useNavigate } from "react-router-dom";

const RocketBtn_Same = () => {
  const navigate = useNavigate();
  function edit() {
    console.log("정보 수정");
    navigate("/Userinfo/:userid");
  }
  function itemBox() {
    console.log("아이템 상자");
    navigate("/Inventory/:userid");
  }
  return (
    <div>
      <button onClick={edit}>정보 수정</button>
      <button onClick={itemBox}>아이템 상자</button>
    </div>
  );
};

export default RocketBtn_Same;
