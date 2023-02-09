import { combineReducers } from 'redux'
// import signal from './signal'
import { accessTokenReducer } from '../containers/AuthContainer'

const rootReducer = combineReducers({
   accessTokenReducer
})

export default rootReducer
