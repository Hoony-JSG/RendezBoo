import '../../Styles/MBTIBtnStyle.css'

const MBTIBtn = (props) => {
  return (
    <div className="MBTIBtn_container">
      <button className="MBTIBtn_download-button">
        <div className="MBTIBtn_docs">Docs</div>
        <div className="MBTIBtn_download">New</div>
      </button>
    </div>
  )
}

export default MBTIBtn
