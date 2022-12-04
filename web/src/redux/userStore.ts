import { createSlice } from "@reduxjs/toolkit"
import { RootState } from "./store"

export interface UserState{
    isLoggin:boolean,
    isAuthenticate: boolean
}

const initialUserState: UserState = {
    isLoggin:false,
    isAuthenticate:false    
}



export const userSlice = createSlice({
    name: "user",
    initialState: initialUserState,

    reducers: {
        
    }

})


//export const {} = authSlice.actions;
export const selectAuth = (state:RootState) => state.user
export default userSlice.reducer