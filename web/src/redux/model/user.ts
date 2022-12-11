
export type WorkSpaceDto = {
    objectId: string,
    user: string,
    name: string,
    permissionWorkSpace: PermissionWorkSpaceDto[]

}
export type PermissionWorkSpaceDto = {
    objectId: string,
    workSpace: string,
    user: string,
    permission: string[]
}
export type RoleDto = {
    objectId: string,
    name: string
}

export type UserDto = {
    objectId:string,
    name:string,
    username:string,
    email:string,
    roles: RoleDto[],
    workSpace: WorkSpaceDto[],
    permissionWorkSpace: PermissionWorkSpaceDto[]

}




