import AllSocialLogins from './AllSocialLogins'

const LoginSocial = (props) => {
  console.log('로그인 소셜')
  return (
    <div>
      로그인 소셜
      <br />
      <div>
        <AllSocialLogins ver={props.ver} />
      </div>
    </div>
  )
}

export default LoginSocial
