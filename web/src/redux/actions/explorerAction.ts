import { createAsyncThunk } from "@reduxjs/toolkit";
import { UserDto } from "../model/user";
import { getAllUsers } from "../petitions/userController";



export const actionGetAllUsers = createAsyncThunk('getAllUsers', async () =>{
    let result = await getAllUsers();
    return result.data as UserDto[]
})