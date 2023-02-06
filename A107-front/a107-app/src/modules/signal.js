

const INPUT_SIGNAL = 'signal/INPUT_SIGNAL'
const SEND_SIGNAL = 'signal/SEND_SIGNAL'
const RECEIVE_SIGNAL = 'signal/RECEIVE_SIGNAL'

export const inputSignal = (input) => ({
  type: INPUT_SIGNAL,
  input,
})

let chat_seq = 3

export const sendSignal = (input) => ({
  type: SEND_SIGNAL,
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
    case INPUT_SIGNAL:
      return {
        ...state,
        input: action.input,
      }
    case SEND_SIGNAL:
      return {
        ...state,
        content: state.content.concat(action.content)
      }
    case RECEIVE_SIGNAL:
      return {
        state
      }
    default:
      return state
  }
}


export default signal
