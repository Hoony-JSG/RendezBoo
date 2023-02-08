const ADD_SIGNAL = 'signal/ADD_SIGNAL'
const RECEIVE_SIGNAL = 'signal/RECEIVE_SIGNAL'

let chat_seq = 15

export const addSignal = (input) => ({
  type: ADD_SIGNAL,
  content: {
    chat_seq: chat_seq++,
    message: input,
    created_at: 'YYYY-MM-DD',
    senderSep: 'me',
    receiverSeq: 'Nickname',
  },
})

export const receiveSignal = () => ({
  type: RECEIVE_SIGNAL,
  content: [],
})

const initialState = {
  input: '',
  content: [
    {
      chat_seq: 11,
      message: '여기서 역삼역 가려면 어디로 가야해요?',
      created_at: 'YYYY-MM-DD',
      senderSeq: 'Nickname',
      receiverSeq: 'me',
    },
    {
      chat_seq: 12,
      message: '뉴진스 하입보이요',
      created_at: 'YYYY-MM-DD',
      senderSeq: 'me',
      receiverSeq: 'Nickname',
    },
    {
      chat_seq: 13,
      message: '커즈아아아아ㅏ노왓츄라잌보이',
      created_at: 'YYYY-MM-DD',
      senderSeq: 'Nickname',
      receiverSeq: 'me',
    },
    {
      chat_seq: 14,
      message: '요마아아아아ㅏ케미컬하입뽀이',
      created_at: 'YYYY-MM-DD',
      senderSeq: 'me',
      receiverSeq: 'Nickname',
    },
  ],
}

function signal(state = initialState, action) {
  switch (action.type) {
    case ADD_SIGNAL:
      return {
        content: state.content.concat(action.content)
      }
    case RECEIVE_SIGNAL:
      return {
        ...state
      }
    default:
      return state
  }
}


export default signal
