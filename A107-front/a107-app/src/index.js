import React from 'react'
import ReactDOM from 'react-dom'
import { configureStore } from '@reduxjs/toolkit'
import { Provider } from 'react-redux'
import { composeWithDevTools } from '@redux-devtools/extension'
import './index.css'
import App from './App'
import reportWebVitals from './reportWebVitals'
import { BrowserRouter } from 'react-router-dom'
import rootReducer from './modules'

const store = configureStore({ reducer: rootReducer }, composeWithDevTools())

ReactDOM.render(
  //   <React.StrictMode>
  <Provider store={store}>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </Provider>,
  //   </React.StrictMode>,
  document.getElementById('root')
)

reportWebVitals()
