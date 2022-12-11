import axios from "axios"
import { URL_API_GET_ALL_PERMISSION, URL_API_INSERT_PERMISSION } from "../../routes/api"





export const getAllPermission = () =>{
    return axios.get(URL_API_GET_ALL_PERMISSION,{withCredentials: true})
}


export const insertPermission = (name: string ) =>{
    return axios.get(`${URL_API_INSERT_PERMISSION}?name=${name}`,{withCredentials:true})
}