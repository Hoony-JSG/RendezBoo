const SignalListItem = ({msg}) => {
    const { user, recentmsg, recentsent } = msg
    return (
        <div className="SignalListItem">
            <h1>SignalListItem</h1>
            <div>
                <h3>{user}</h3>
                <p>{recentsent}</p>
            </div>
            <p>{recentmsg}</p>
        </div>
    )
  }

export default SignalListItem;