import React, { useRef } from 'react'
import '../../Styles/RocketBadgeListStyle.css'

const RocketBadgeList = (props) => {
  const { badgeList } = props
  const SRC_URL = 'https://d156wamfkmlo3m.cloudfront.net/'
  const listRef = useRef(null)

  const handleWheelScroll = (e) => {
    const container = listRef.current
    if (container) {
      container.scrollLeft += e.deltaY
    }
  }

  return (
    <div className="BadgeList_container" onWheel={handleWheelScroll}>
      <div className="BadgeList_container-title">
        <span>뱃지 리스트</span>
      </div>
      <div className="BadgeList_list" ref={listRef}>
        {badgeList.map((badge, idx) => (
          <div class="BadgeList_flip-card" key={idx}>
            <div class="BadgeList_flip-card-inner">
              <div class="BadgeList_flip-card-front">
                <div className="BadgeList_item" title={badge.description}>
                  <img
                    className="BadgeList_item-img"
                    src={SRC_URL + badge.url}
                    alt={badge.name}
                  />
                </div>
              </div>
              <div class="BadgeList_flip-card-back">
                <div className="BadgeList_item-name">{badge.name}</div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  )
}

export default RocketBadgeList
