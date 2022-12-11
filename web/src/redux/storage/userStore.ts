import { createSlice } from "@reduxjs/toolkit"
import { actionGetAllPermission, actionInserPermission } from "../actions/permissionAction"
import { actionGetMyUserInfo, actionInsertWorkSpace, actionSelectWorkSpace } from "../actions/userAction"
import { PermissionModel } from "../model/permission"
import { UserDto, WorkSpaceDto } from "../model/user"
import { RootState } from "../store"

export interface UserState{
    userInfo?: UserDto,
    workSpaceSelected?: WorkSpaceDto,
    permissions: PermissionModel[],
    
}

const initialUserState: UserState = {
    userInfo: undefined,
    workSpaceSelected: undefined,
    permissions: [],
}



export const userSlice = createSlice({
    name: "user",
    initialState: initialUserState,

    reducers: {
        
    },
    extraReducers: (builder) => {
        builder.addCase(actionGetMyUserInfo.fulfilled,(state,action)=>{
            return { ...state, userInfo: action.payload }
        })
        builder.addCase(actionSelectWorkSpace.fulfilled,(state,action)=>{
            return { ...state, workSpaceSelected: action.payload }
        })
        builder.addCase(actionInsertWorkSpace.fulfilled,(state,action)=>{
            state.userInfo?.workSpace.push(action.payload)
            return state
        })
        builder.addCase(actionGetAllPermission.fulfilled,(state,action)=>{
            return {...state, permissions:action.payload}
        })
        builder.addCase(actionInserPermission.fulfilled,(state,action)=>{
            state.permissions?.push(action.payload)
            return state
        })
    }

})


//export const {} = authSlice.actions;
export const selectUser = (state:RootState) => state.user
export const selectUserInfo = (state:RootState) => state.user.userInfo
export const selectUserProfile = (state:RootState) => {
    return {
        email: state.user.userInfo?.email,
        name: state.user.userInfo?.name,
        username: state.user.userInfo?.username
    }
    
}
export const selectUserWorkSpace = (state:RootState) => state.user.userInfo?.workSpace
export const selectUserWorkSpaceSelected = (state:RootState) => state.user.workSpaceSelected
export const selectPermission = (state:RootState) => state.user.permissions 

export default userSlice.reducer