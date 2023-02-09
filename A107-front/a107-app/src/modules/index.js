import { combineReducers } from 'redux'
// import signal from './signal'
import { accessTokenReducer } from '../containers/JwtContainer'
import { userInfoReducer } from '../containers/UserInfo'

const rootReducer = combineReducers({
  accessTokenReducer,
  userInfoReducer
})

export default rootReducer
