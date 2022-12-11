import { createSlice } from "@reduxjs/toolkit"
import { logginAction } from "../actions/authAction"
import { RootState } from "../store"

export interface AuthState{
    isLoggin:boolean,
    isAuthenticate: boolean
}

const initialAuthState: AuthState = {
    isLoggin:false,
    isAuthenticate:false    
}



export const authSlice = createSlice({
    name: "authentication",
    initialState: initialAuthState,

    reducers: {
        
    },
    extraReducers: (builder)=>{
        builder.addCase(logginAction.fulfilled, (state,action)=>{
            return {...state,isLoggin:action.payload}
        })
    }

})


//export const {} = authSlice.actions;
export const selectAuth = (state:RootState) => state.auth
export default authSlice.reducer