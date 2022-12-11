import axios, { AxiosResponse } from "axios";
import { URL_API_GET_ALL_USER, URL_API_GET_MY_INFO } from "../../routes/api";



export const getMyInfo = (): Promise<AxiosResponse<any, any>> => {
    return axios.get(URL_API_GET_MY_INFO,{
        withCredentials: true
    });
}

export const getAllUsers = () => {
    return axios.get(URL_API_GET_ALL_USER, {
        withCredentials:true
    })
} 

