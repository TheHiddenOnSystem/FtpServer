export type PermissionRequestInsertModel = {
    workSpace: string,
    user: string,
    permission: string[];
}

export type WorkSpaceRequestInsertModel = {
    name : string,
    permission:PermissionRequestInsertModel[]
}