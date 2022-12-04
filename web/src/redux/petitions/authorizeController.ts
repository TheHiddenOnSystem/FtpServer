
import axios from 'axios'
import { LOGGING, REGISTER } from '../../routes/api'




export const register=(
    {
        username,
        password,
        email
    }:{
        username:String,
        password:String,
        email:String
    }
)=> axios.put(REGISTER,{
    username:username,
    password:password,
    email:email
});


export const loggin =(
    {
        username,
        password
    }:{
        username:String,
        password:String
    }
)=> axios.post(LOGGING,{
    name:username,
    password:password
})