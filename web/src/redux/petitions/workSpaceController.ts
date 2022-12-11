import axios from "axios";
import { URL_API_INSERT_WORKSPACE,URL_API_SELECT_USER_WORK_SPACES } from "../../routes/api";
import { WorkSpaceRequestInsertModel } from "../model/workspace";



export const getWorkSpaceById = (id:string) => {
    return axios.post(`${URL_API_SELECT_USER_WORK_SPACES}?objectId=${id}`,
    null,
    {
        withCredentials: true
    }
    )
}



export const insertSpace = (workSpaceInsert : WorkSpaceRequestInsertModel) => {
    return axios.post(URL_API_INSERT_WORKSPACE, workSpaceInsert, {withCredentials:true})
}