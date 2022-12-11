import { createSlice } from "@reduxjs/toolkit"
import { actionGetAllUsers } from "../actions/explorerAction"
import { UserDto } from "../model/user"
import { RootState } from "../store"

export interface ExploreState{
    otherUsers : UserDto[]
}

const initialExplorerState: ExploreState = {
    otherUsers:[]
}



export const explorerSlice = createSlice({
    name: "explore",
    initialState: initialExplorerState,

    reducers: {
        
    },
    extraReducers: (builder) => {
        builder.addCase(actionGetAllUsers.fulfilled, (state, action)=>{
            return {...state, otherUsers: action.payload}
        })
    }

})


export const selectExplorer = (state:RootState) => state.explorer
export const selectOtherUser = (state:RootState) => state.explorer.otherUsers



export default explorerSlice.reducer
