const RocketBadgeList = (props) => {
  const { badgeList } = props
  const SRC_URL = 'https://d156wamfkmlo3m.cloudfront.net/'

  return (
    <div>
      <h3>로켓 뱃지 리스트</h3>
      <div>
        {badgeList.map((badge, idx) => (
          <div
            key={idx}
            title={badge.description}
            style={{ display: 'inline-block' }}
          >
            <img
              src={SRC_URL + badge.url}
              alt={badge.name}
              style={{ width: '128px', height: '128px' }}
            />
            <div style={{ margin: '5px 0px' }}>{badge.name}</div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default RocketBadgeList
