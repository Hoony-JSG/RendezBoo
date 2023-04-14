import { combineReducers } from 'redux'
// import signal from './signal'
import { accessTokenReducer } from '../containers/JwtContainer'
import { userInfoReducer } from '../containers/UserInfoContainer'
import { persistReducer } from 'redux-persist'
import storage from 'redux-persist/lib/storage'
import { configureStore } from '@reduxjs/toolkit'
import { composeWithDevTools } from '@redux-devtools/extension'

const rootReducer = combineReducers({
  accessTokenReducer,
  userInfoReducer
})


const persistConfig = {
  key: 'root',
  storage,
  blacklist: ['accessTokenReducer']
}



// export default rootReducer
const persistedReducer = persistReducer(persistConfig, rootReducer)

export default persistedReducer

// const store = configureStore({ reducer: persistedReducer }, composeWithDevTools())

// export default store