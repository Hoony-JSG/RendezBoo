import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { useDispatch, useSelector } from 'react-redux'
import axios from 'axios'
// import { SignalList, SignalItem, SignalForm } from "../components"
import { SiRocketdotchat } from 'react-icons/si'
import SignalList from '../components/SignalComponents/SignalList'
import SignalSelected from '../components/SignalComponents/SignalSelected'

const Signal = () => {
  const tmpChatRoomSeq = useParams().tmpChatRoomSeq

  const newChat = useSelector((state) => state.signal.content)

  useEffect(() => {
    const tmpChatRoom = [
      {
        chatRoomSeq: 0,
        userFemaleSeq: 'Nickname',
        userMaleSeq: 'me',
        content: newChat,
        // content: [
        //   //채팅방 시퀀스로 api 요청 보내서 받아옴
        //   {
        //     chat_seq: 11, //문자열로 들어옴 몽고디비
        //     message: '여기서 역삼역 가려면 어디로 가야해요?',
        //     created_at: 'YYYY-MM-DD',
        //     senderSeq: 'Nickname',
        //     receiverSeq: 'me',
        //   },
        //   {
        //     chat_seq: 12,
        //     message: '뉴진스 하입보이요',
        //     created_at: 'YYYY-MM-DD',
        //     senderSeq: 'me',
        //     receiverSeq: 'Nickname',
        //   },
        //   {
        //     chat_seq: 13,
        //     message: '커즈아아아아ㅏ노왓츄라잌보이',
        //     created_at: 'YYYY-MM-DD',
        //     senderSeq: 'Nickname',
        //     receiverSeq: 'me',
        //   },
        //   {
        //     chat_seq: 14,
        //     message: '요마아아아아ㅏ케미컬하입뽀이',
        //     created_at: 'YYYY-MM-DD',
        //     senderSeq: 'me',
        //     receiverSeq: 'Nickname',
        //   },
        // ],
        created_at: 'YYYY-MM-DD',
      },
      {
        chatRoomSeq: 1,
        userFemaleSeq: 'Nickname',
        userMaleSeq: 'me',
        content: [
          {
            chat_seq: 21,
            message: '너는 별을 보자며 내 손을 끌어서',
            created_at: 'YYYY-MM-DD',
            senderSeq: 'Nickname',
            receiverSeq: 'me',
          },
          {
            chat_seq: 22,
            message: '저녁노을이 진 옥상에 걸터앉아',
            created_at: 'YYYY-MM-DD',
            senderSeq: 'me',
            receiverSeq: 'Nickname',
          },
          {
            chat_seq: 23,
            message: 'Every time I look up in the sky',
            created_at: 'YYYY-MM-DD',
            senderSeq: 'Nickname',
            receiverSeq: 'me',
          },
          {
            chat_seq: 24,
            message: '근데 단 한 개도 없는 Star',
            created_at: 'YYYY-MM-DD',
            senderSeq: 'me',
            receiverSeq: 'Nickname',
          },
          {
            chat_seq: 25,
            message: '괜찮아 네가 내 우주고 밝게 빛나 줘',
            created_at: 'YYYY-MM-DD',
            senderSeq: 'Nickname',
            receiverSeq: 'me',
          },
        ],
        created_at: 'YYYY-MM-DD',
      },
      {
        chatRoomSeq: 2,
        userFemaleSeq: 'Nickname',
        userMaleSeq: 'me',
        content: [
          {
            chat_seq: 31,
            message: 'ASAP 내 반쪽 아니 완전 Copy',
            created_at: 'YYYY-MM-DD',
            senderSeq: 'Nickname',
            receiverSeq: 'me',
          },
          {
            chat_seq: 32,
            message: '나와 똑같아 내 맘 잘 알아줄',
            created_at: 'YYYY-MM-DD',
            senderSeq: 'me',
            receiverSeq: 'Nickname',
          },
          {
            chat_seq: 33,
            message: 'ASAP 꼭 닮은 내 Decalcomanie',
            created_at: 'YYYY-MM-DD',
            senderSeq: 'Nickname',
            receiverSeq: 'me',
          },
          {
            chat_seq: 34,
            message: '눈앞에 나타나 줘',
            created_at: 'YYYY-MM-DD',
            senderSeq: 'me',
            receiverSeq: 'Nickname',
          },
        ],
        created_at: 'YYYY-MM-DD',
      },
    ]
    setChatRoom(tmpChatRoom)
    console.log(chatRoom[0])
  }, [newChat])

  const [chatRoom, setChatRoom] = useState([
    {
      chatRoomSeq: 0,
      userFemaleSeq: 'Nickname',
      userMaleSeq: 'me',
      content: newChat,
      // content: [
      //   //채팅방 시퀀스로 api 요청 보내서 받아옴
      //   {
      //     chat_seq: 11, //문자열로 들어옴 몽고디비
      //     message: '여기서 역삼역 가려면 어디로 가야해요?',
      //     created_at: 'YYYY-MM-DD',
      //     senderSeq: 'Nickname',
      //     receiverSeq: 'me',
      //   },
      //   {
      //     chat_seq: 12,
      //     message: '뉴진스 하입보이요',
      //     created_at: 'YYYY-MM-DD',
      //     senderSeq: 'me',
      //     receiverSeq: 'Nickname',
      //   },
      //   {
      //     chat_seq: 13,
      //     message: '커즈아아아아ㅏ노왓츄라잌보이',
      //     created_at: 'YYYY-MM-DD',
      //     senderSeq: 'Nickname',
      //     receiverSeq: 'me',
      //   },
      //   {
      //     chat_seq: 14,
      //     message: '요마아아아아ㅏ케미컬하입뽀이',
      //     created_at: 'YYYY-MM-DD',
      //     senderSeq: 'me',
      //     receiverSeq: 'Nickname',
      //   },
      // ],
      created_at: 'YYYY-MM-DD',
    },
    {
      chatRoomSeq: 1,
      userFemaleSeq: 'Nickname',
      userMaleSeq: 'me',
      content: [
        {
          chat_seq: 21,
          message: '너는 별을 보자며 내 손을 끌어서',
          created_at: 'YYYY-MM-DD',
          senderSeq: 'Nickname',
          receiverSeq: 'me',
        },
        {
          chat_seq: 22,
          message: '저녁노을이 진 옥상에 걸터앉아',
          created_at: 'YYYY-MM-DD',
          senderSeq: 'me',
          receiverSeq: 'Nickname',
        },
        {
          chat_seq: 23,
          message: 'Every time I look up in the sky',
          created_at: 'YYYY-MM-DD',
          senderSeq: 'Nickname',
          receiverSeq: 'me',
        },
        {
          chat_seq: 24,
          message: '근데 단 한 개도 없는 Star',
          created_at: 'YYYY-MM-DD',
          senderSeq: 'me',
          receiverSeq: 'Nickname',
        },
        {
          chat_seq: 25,
          message: '괜찮아 네가 내 우주고 밝게 빛나 줘',
          created_at: 'YYYY-MM-DD',
          senderSeq: 'Nickname',
          receiverSeq: 'me',
        },
      ],
      created_at: 'YYYY-MM-DD',
    },
    {
      chatRoomSeq: 2,
      userFemaleSeq: 'Nickname',
      userMaleSeq: 'me',
      content: [
        {
          chat_seq: 31,
          message: 'ASAP 내 반쪽 아니 완전 Copy',
          created_at: 'YYYY-MM-DD',
          senderSeq: 'Nickname',
          receiverSeq: 'me',
        },
        {
          chat_seq: 32,
          message: '나와 똑같아 내 맘 잘 알아줄',
          created_at: 'YYYY-MM-DD',
          senderSeq: 'me',
          receiverSeq: 'Nickname',
        },
        {
          chat_seq: 33,
          message: 'ASAP 꼭 닮은 내 Decalcomanie',
          created_at: 'YYYY-MM-DD',
          senderSeq: 'Nickname',
          receiverSeq: 'me',
        },
        {
          chat_seq: 34,
          message: '눈앞에 나타나 줘',
          created_at: 'YYYY-MM-DD',
          senderSeq: 'me',
          receiverSeq: 'Nickname',
        },
      ],
      created_at: 'YYYY-MM-DD',
    },
  ])

  const windowStyle = {
    boxSizing: 'border-box',
    display: 'flex',
    flexDirection: 'row',
    padding: '40px',
    gap: '20px',
    justifyContent: 'center',
    alignItems: 'center',
    position: 'absolute',
    width: '80%',
    height: '80%',
    left: '160px',
    top: '120px',
    background:
      'linear-gradient(180deg, rgba(0, 0, 0, 0.5) 0%, rgba(0, 0, 0, 0) 100%)',
    border: '2px solid #FFFFFF',
    filter:
      'drop-shadow(0px 0px 2px rgba(255, 255, 255, 0.25)) drop-shadow(0px 0px 5px rgba(0, 0, 0, 0.25))',
    borderRadius: '50px',
  }
  const asideStyle = {
    display: 'flex',
    width: '35%',
    flexDirection: 'column',
    padding: '10px',
    gap: '20px',
    justifyContent: 'center',
    alignItems: 'center',
  }

  const iconStyle = {
    color: 'white',
    width: '60px',
    height: '60px',
  }

  return (
    <div>
      <div className="window" style={windowStyle}>
        <div className="aside" style={asideStyle}>
          <SiRocketdotchat style={iconStyle} />
          <h1>Signal</h1>
          <div
            className="SignalList"
            style={{
              display: 'flex',
              flexDirection: 'column',
              gap: '20px',
              width: '100%',
            }}
          >
            {chatRoom.map((chat) => (
              <SignalList chat={chat} key={chat.chatRoomSeq} />
            ))}
          </div>
        </div>
        <div className="content" style={{ width: '65%' }}>
          {tmpChatRoomSeq ? (
            <SignalSelected content={chatRoom[tmpChatRoomSeq].content} />
          ) : (
            <div>
              <h1>메시지를 선택하세요.</h1>
            </div>
          )}
        </div>
      </div>
    </div>
  )
}

export default Signal
