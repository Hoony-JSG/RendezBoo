import { createSlice } from "@reduxjs/toolkit";

export const ACCESS_TOKEN_AGE = 1800 * 1000    // 30 min

const initialState = {
  authenticated: false,
  accessToken: null,
  expireTime: null
}


export const accessTokenSlice = createSlice({
  name: 'accessToken',
  initialState: initialState,
  reducers: {
    SET_TOKEN: (state, action) => {
      console.log(action)
      state.authenticated = true
      state.accessToken = action.payload
      state.expireTime = new Date().getTime() + ACCESS_TOKEN_AGE
    },
    REMOVE_TOKEN: (state) => {
      state.authenticated = false
      state.accessToken = null
      state.expireTime = null
    }
  }
})

export const { SET_TOKEN, REMOVE_TOKEN } = accessTokenSlice.actions

export const accessTokenReducer =  accessTokenSlice.reducer