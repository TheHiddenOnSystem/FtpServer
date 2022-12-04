import { createAsyncThunk } from "@reduxjs/toolkit"
import { openSnackBar } from "../notificationStore";
import { loggin, register } from "../petitions/authorizeController";

export const logginAction = createAsyncThunk('auth/logging', async ({
    username,
    password
}: {
    username: String,
    password: String
},thunkAPI) => {

    const result = await loggin({
        username,
        password
    }).then(e => {
        if (e.status === 200 || e.status === 202)
            return true;
        else
            return false;
    }).catch(() => false);

    if (result)
        thunkAPI.dispatch(openSnackBar({
            message: "Conseguido",
            severity: "success"
        }))
    else
        thunkAPI.dispatch(openSnackBar({
            message: "Error",
            severity: "error"
        }))

    return result;
})

export const registerAction = createAsyncThunk('auth/register', async ({
    username,
    email,
    password
}: {
    username: String,
    email: String,
    password: String
}, thunkAPI) => {
    let result = await register({
        username,
        email,
        password
    }).then(e => {
        if (e.status === 200 || e.status === 202)
            return true;
        else
            return false;
    }).catch(() => false);

    if (result)
        thunkAPI.dispatch(openSnackBar({
            message: "Conseguido",
            severity: "success"
        }))
    else
        thunkAPI.dispatch(openSnackBar({
            message: "Error",
            severity: "error"
        }))

    return result
})
