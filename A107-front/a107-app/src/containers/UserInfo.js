import { createSlice } from "@reduxjs/toolkit";


const initialState = {
  userEmail: null,
  userSeq: -1,
  userName: null
}


export const userInfoSlice = createSlice({
  name: 'userInfo',
  initialState: initialState,
  reducers: {
    SET_USER_INFO: (state, action) => {
      state.userEmail = action.payload.email
      state.userSeq = action.payload.seq
      state.userName = action.payload.name
    },
    REMOVE_USER_INFO: (state) => {
      state.userEmail = null
      state.userSeq = -1
      state.userName = null
    }
  }
})

export const { SET_USER_INFO, REMOVE_USER_INFO } = userInfoSlice.actions

export const userInfoReducer =  userInfoSlice.reducer