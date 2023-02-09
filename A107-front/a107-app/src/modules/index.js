import { combineReducers } from 'redux'
import { accessTokenReducer } from '../containers/AuthContainer'

const rootReducer = combineReducers({
  accessTokenReducer
})

export default rootReducer
