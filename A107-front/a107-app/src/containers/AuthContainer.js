import { createSlice } from "@reduxjs/toolkit";

export const ACCESS_TOKEN_AGE = 1800 * 1000    // 30 min

export const accessTokenSlice = createSlice({
  name: 'accessToken',
  initialState: {
    authenticated: false,
    accessToken: null,
    expireTime: null
  },
  reducers: {
    SET_TOKEN: (state, action) => {
      state.authenticated = true
      state.accessToken = action.payload
      state.expireTime = new Date().getTime() + ACCESS_TOKEN_AGE
    },
    REMOVE_TOKEN: (state) => {
      state.authenticated = false
      state.accessToken = null,
      state.expireTime = null
    }
  }
})

export const { SET_TOKEN, REMOVE_TOKEN } = accessTokenSlice.actions

export default accessTokenSlice.reducer