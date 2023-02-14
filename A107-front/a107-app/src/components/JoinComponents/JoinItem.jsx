import { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom'
import JoinItem2nd from './JoinItem2nd'
import JoinItem3rd from './JoinItem3rd'
import JoinItem4rd from './JoinItem4rd'

const JoinItem = () => {
  const location = useLocation()
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

  const setNext = () => {
    if (order === 3) {
      window.location.href = '/'
    } else {
      setOrder(order + 1)
    }
  }

  const print = () => {
    console.log('----------------------')
    console.log('받은 이메일 : ' + email)
    console.log('받은 이메일 타입 : ' + emailType)
    console.log('받은 폰번호 : ' + phoneNumber)
    console.log('받은 이름 : ' + name)
    console.log('받은 성별 : ' + gender)
    console.log('받은 MBTI : ' + mbti)
    console.log('받은 생일 : ' + birthday)
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
  const [order, setOrder] = useState(2, '')
  return <div style={{ marginTop: '20px' }}>{ItemList[order]}</div>
}

export default JoinItem
