import { PermissionWorkSpaceDto, UserDto, WorkSpaceDto } from "./user";


export const workSpaceMock: WorkSpaceDto = {
    objectId: "string",
    user: "string",
    name: "string",
    permissionWorkSpace: [
        {
            objectId: "",
            permission:[],
            user: "",
            workSpace : "",
            
        }
    ],

}
export const permissionWorkSpaceMock: PermissionWorkSpaceDto = {
    objectId: "string",
    workSpace: "string",
    user: "string",
    permission: []
}


export const userDtoMock:UserDto ={
    objectId: "string",
    name: "string",
    username: "string",
    email: "string",
    roles: [
        {
            objectId: "string",
            name: "string"
        }
    ],
    workSpace: [
        workSpaceMock,
        workSpaceMock,
        workSpaceMock,
        workSpaceMock
    ],
    permissionWorkSpace: [
        permissionWorkSpaceMock,
        permissionWorkSpaceMock,
        permissionWorkSpaceMock
    ]

} 