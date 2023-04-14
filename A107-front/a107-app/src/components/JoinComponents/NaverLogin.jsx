import NaverLogo from '../../Images/naverlogin.png'

const NaverLogin = (props) => {
  const NAVER_PROD_REDIRECT =
    'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=F3K8r9yyEG_RFk8RpLgi&redirect_uri=https%3a%2f%2fi8a107.p.ssafy.io%2foauth%2fnaver&state=1234'

  return (
      <a href={NAVER_PROD_REDIRECT}>
        <img
          src={NaverLogo}
          alt="Login with Naver"
          style={{ filter: 'drop-shadow(0px 4px 4px rgba(0, 0, 0, 0.25)) drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25))' }}
        />
      </a>
  )
}

export default NaverLogin
