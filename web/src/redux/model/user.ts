

export type UserDto = {
    objectId:String,
    name:String,
    username:String,
    email:String,
    roles: [
        {
            objectId:String,
            name:String
        }
    ],
    workSpace: [
        {
            objectId:String,
            user:String,
            name:String,
            permissionWorkSpace:[
                {
                    objectId:String,
                    workSpace:String,
                    user:String,
                    permission:String[]
                }
            ],

        }
    ],
    permissionWorkSpace:[
        {
            objectId:String,
            workSpace:String,
            user:String,
            permission:String[]
        }
    ]

}



