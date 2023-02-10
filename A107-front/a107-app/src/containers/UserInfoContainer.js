import { createSlice } from "@reduxjs/toolkit";

// 초기 상태
const initialState = {
  userEmail: null,
  userSeq: null,
  userName: null,
  userGender: null
}


export const userInfoSlice = createSlice({
  name: 'userInfo',   // 저장소 이름
  initialState: initialState,   // 초기 상태
  reducers: {
    SET_USER_INFO: (state, action) => {
      state.userEmail = action.payload.email
      state.userSeq = action.payload.seq
      state.userName = action.payload.name
      state.userGender = action.payload.gender
    },
    REMOVE_USER_INFO: (state) => {
      state.userEmail = null
      state.userSeq = null
      state.userName = null
      state.userGender = null
    }
  }
})

export const { SET_USER_INFO, REMOVE_USER_INFO } = userInfoSlice.actions

export const userInfoReducer =  userInfoSlice.reducer