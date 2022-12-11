import { createAsyncThunk } from "@reduxjs/toolkit";
import { PermissionWorkSpaceDto, UserDto, WorkSpaceDto } from "../model/user";
import { WorkSpaceRequestInsertModel } from "../model/workspace";
import { getMyInfo } from "../petitions/userController";
import { getWorkSpaceById, insertSpace } from "../petitions/workSpaceController";




export const actionGetMyUserInfo = createAsyncThunk('user/getMyUserInfoUser', async () => {
    let result = await getMyInfo()

    return result.data as UserDto
})

export const actionSelectWorkSpace = createAsyncThunk('user/selectWorkSpace',async (id:string) =>{
    let result = await getWorkSpaceById(id)

    return result.data as WorkSpaceDto
})

export const actionInsertWorkSpace = createAsyncThunk('user/createWorkSpace',
    async (workSpace:WorkSpaceRequestInsertModel, ThunkApi) =>{
        let result = await insertSpace(workSpace)
        let objectId = result.data;

        


        let workSpaceInserted: WorkSpaceDto = {
            objectId: objectId,
            user: "",
            name: workSpace.name,
            permissionWorkSpace: workSpace.permission.map(elem => {
                return {
                    workSpace: objectId,
                    user: elem.user,
                    permission: elem.permission
                } as PermissionWorkSpaceDto
            } )
        }

        return workSpaceInserted
    }    
)

