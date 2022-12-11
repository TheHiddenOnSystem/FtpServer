import { createAsyncThunk } from "@reduxjs/toolkit";
import { PermissionModel } from "../model/permission";
import { getAllPermission, insertPermission } from "../petitions/permissionController";




export const actionGetAllPermission = createAsyncThunk('permission/getAll', async () =>{
    let result = await getAllPermission()

    return result.data as PermissionModel[]
})


export const actionInserPermission =createAsyncThunk('permisison/insert', async (name:string) =>{
    let result = await insertPermission(name);
    
    let permissionInserted = {
        name: name,
        objectId: result.data
    } as PermissionModel

    return permissionInserted
})