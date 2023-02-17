import React from 'react'
import ReactDOM from 'react-dom'
import { configureStore } from '@reduxjs/toolkit'
import { Provider } from 'react-redux'
import { composeWithDevTools } from '@redux-devtools/extension'
import './index.css'
import App from './App'
import reportWebVitals from './reportWebVitals'
import { BrowserRouter } from 'react-router-dom'
import { CookiesProvider } from 'react-cookie'
import { PersistGate } from 'redux-persist/integration/react'
import { persistStore } from 'redux-persist'
import persistedReducer from './modules'
import thunk from 'redux-thunk'

const store = configureStore({ 
  reducer: persistedReducer,
  middleware: [thunk] 
}, composeWithDevTools())


const persistor = persistStore(store)

ReactDOM.render(
  //   <React.StrictMode>
  <CookiesProvider>
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
      </PersistGate>
    </Provider>
  </CookiesProvider>,

  //   </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
