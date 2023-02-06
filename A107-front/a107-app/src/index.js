import React from 'react'
import ReactDOM from 'react-dom'
import { createStore } from 'redux'
import { Provider } from 'react-redux'
import './index.css'
import App from './App'
import reportWebVitals from './reportWebVitals'
import { BrowserRouter } from 'react-router-dom'
import rootReducer from './modules'

const store = createStore(rootReducer)

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
