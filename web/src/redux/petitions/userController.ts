import axios from "axios";
import { GET_MY_INFO } from "../../routes/api";



const getMyInfo = () =>{
    return axios.get(GET_MY_INFO,{
        withCredentials: true
    });
}

