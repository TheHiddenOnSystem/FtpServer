import { createAsyncThunk } from "@reduxjs/toolkit"
import { closeNotification, openNotification } from "../notificationStore";
import { loggin, register } from "../petitions/authorizeController";

export const logginAction = createAsyncThunk('auth/logging',async ({
    username,
    password
}:{
    username:String,
    password:String
}) =>{

    return await loggin({
        username,
        password
    }).then(e=>{
        if(e.status === 200 || e.status ===  202 )
            return true;
        else
            return false;    
    }).catch(()=>false);
})

export const registerAction = createAsyncThunk('auth/register',async ({
    username,
    email,
    password
}:{
    username:String,
    email:String,
    password:String
},thunkAPI) => {
    let result = await register({
        username,
        email,
        password
    }).then(e=>{
        if(e.status === 200 || e.status ===  202 )
            return true;
        else
            return false;    
    }).catch(()=>false);

    if(result)
        thunkAPI.dispatch(openNotification("Conseguido"))
    else
        thunkAPI.dispatch(openNotification("Error"))

    setTimeout(()=>{
        thunkAPI.dispatch(closeNotification())
    },5000)
        
    return result
})
