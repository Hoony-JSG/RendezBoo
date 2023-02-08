import { combineReducers } from 'redux'
import signal from './signal'
import login from './login'

const rootReducer = combineReducers({
  signal,
  login,
})

export default rootReducer
