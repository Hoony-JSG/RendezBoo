import JoinItemSocialLoginButton from './JoinItemSocialLoginButton'
import NaverLogin from './NaverLogin'

const JoinItem1st = () => {
  return (
    <div style={{ display: 'inline-block' }}>
      J_1_소셜조인
      <div>
        <JoinItemSocialLoginButton />
      </div>
      <div style={{ alignContent: 'center', display: 'inherit' }}>
        <NaverLogin />
      </div>
    </div>
  )
}

export default JoinItem1st
