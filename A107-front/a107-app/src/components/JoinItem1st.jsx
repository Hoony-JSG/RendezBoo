import AllSocialLogins from './AllSocialLogins'
import JoinItemSocialLoginButton from './JoinItemSocialLoginButton'

const JoinItem1st = () => {
  return (
    <div style={{ display: 'inline-block' }}>
      J_1_소셜조인
      <div>
        <JoinItemSocialLoginButton />
      </div>
      <div style={{ alignContent: 'center', display: 'inherit' }}>
        <AllSocialLogins ver="join" />
      </div>
    </div>
  )
}

export default JoinItem1st
