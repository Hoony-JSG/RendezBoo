import axios from 'axios'
import { useEffect, useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom'
import JoinItem2nd from './JoinItem2nd'
import JoinItem3rd from './JoinItem3rd'
import JoinItem4rd from './JoinItem4rd'

const JoinItem = () => {
  const location = useLocation()

  const NAVER_URL =
    'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=F3K8r9yyEG_RFk8RpLgi&redirect_uri=https%3a%2f%2fi8a107.p.ssafy.io%2foauth%2fnaver&state=1234'
  const KAKAO_URL =
    'https://kauth.kakao.com/oauth/authorize?client_id=712c281c808f36c63bc8cea4a11ba42f&redirect_uri=https%3a%2f%2fi8a107.p.ssafy.io%2foauth%2fkakao&response_type=code'
  const params = new URLSearchParams(location.search)
  const email = params.get('email')
  const emailType = params.get('type')
  console.log('확인 타입 : ' + emailType)

  const [birthday, setBirthday] = useState()
  const [gender, setGender] = useState()
  const [mbti, setMbti] = useState()
  const [name, setName] = useState()
  const [phoneNumber, setPhoneNumber] = useState()
  const [profileImagePath, setProfileImagePath] = useState()

  const dataForBack = {
    birthday: birthday,
    email: email,
    gender: gender,
    mbti: mbti,
    name: name,
    phoneNumber: phoneNumber,
    profileImagePath: profileImagePath,
  }

  const sendUserSetting = async () => {
    const dateObj = new Date(birthday)
    const formattedBirth = `${dateObj.getFullYear()}-${String(
      dateObj.getMonth() + 1
    ).padStart(2, '0')}-${String(dateObj.getDate()).padStart(2, '0')}`
    try {
      let response = await axios.post(
        'https://i8a107.p.ssafy.io/api/user/join',
        {
          birthday: formattedBirth,
          city: '',
          email: email,
          gender: gender,
          isAdmin: false,
          mbti: mbti,
          name: name,
          password: '',
          phoneNumber: phoneNumber,
          profileImagePath: '',
        }
      )
      console.log('유저 세팅 전송 Good')

      if (emailType == 0) {
        window.location.href = NAVER_URL
      } else {
        window.location.href = KAKAO_URL
      }
    } catch (error) {
      console.log('유저 세팅 전송 오류')
      console.error(error)
    }
  }

  const setNext = () => {
    if (order === 3) {
      sendUserSetting()
      // window.location.href = '/'
    } else {
      setOrder(order + 1)
    }
  }

  const print = () => {
    const dateObj = new Date(birthday)
    const formattedDate = `${dateObj.getFullYear()}-${String(
      dateObj.getMonth() + 1
    ).padStart(2, '0')}-${String(dateObj.getDate()).padStart(2, '0')}`
    console.log('----------------------')
    console.log('받은 이메일 : ' + email)
    console.log('받은 이메일 타입 : ' + emailType)
    console.log('받은 폰번호 : ' + phoneNumber)
    console.log('받은 이름 : ' + name)
    console.log('받은 성별 : ' + gender)
    console.log('받은 MBTI : ' + mbti)
    console.log('받은 생일 : ' + birthday)
    console.log('받은 생일(format) : ' + formattedDate)
    // console.log(
    //   '받은 생일(formatted) : ' +
    //     `${birthday.getFullYear()}-${String(birthday.getMonth() + 1).padStart(
    //       2,
    //       '0'
    //     )}-${String(birthday.getDate()).padStart(2, '0')}`
    // )
    console.log('받은 프사 : ' + profileImagePath)
  }

  useEffect(print, [gender, mbti])

  const ItemList = [
    '',
    '',
    <JoinItem2nd
      email={email}
      emailType={emailType}
      phoneNumber={setPhoneNumber}
      name={setName}
      gender={setGender}
      setNext={setNext}
    />,
    <JoinItem3rd
      profileImagePath={setProfileImagePath}
      mbti={setMbti}
      birthday={setBirthday}
      setNext={setNext}
    />,
    // <JoinItem4rd setNext={setNext} />,
  ]
  const [order, setOrder] = useState(3, '')
  return <div style={{ marginTop: '20px' }}>{ItemList[order]}</div>
}

export default JoinItem
