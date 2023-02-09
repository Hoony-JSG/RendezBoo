import { combineReducers } from 'redux'
import signal from './signal'
import { accessTokenReducer } from '../containers/AuthContainer'

const rootReducer = combineReducers({
  signal, accessTokenReducer
})

export default rootReducer
