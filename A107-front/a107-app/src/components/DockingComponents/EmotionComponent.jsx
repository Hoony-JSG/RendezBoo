export const EmotionComponent = (props) => {
  const { imgSrc, data, top, left } = props
  let h = data > 0.5 ? '162px' : '81px'
  let z = data > 0.25 ? 15 : -1

  return (
    <div style={{ top: top, left: left, position: 'absolute', zIndex: z }}>
      <img src={imgSrc} style={{ height: h, zIndex: z }} alt={imgSrc} />
    </div>
  )
}
