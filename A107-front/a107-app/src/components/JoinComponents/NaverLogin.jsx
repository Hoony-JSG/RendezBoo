import NaverLogo from '../../Images/btnG_완성형.png'

const NaverLogin = (props) => {
  const NAVER_PROD_REDIRECT =
    'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=F3K8r9yyEG_RFk8RpLgi&redirect_uri=https%3a%2f%2fi8a107.p.ssafy.io%2foauth%2fnaver&state=1234'

  return (
      <a href={NAVER_PROD_REDIRECT}>
        <img
          src={NaverLogo}
          alt="Naver logo"
          style={{ width: '300px', height: '75px' }}
        />
      </a>
  )
}

export default NaverLogin
