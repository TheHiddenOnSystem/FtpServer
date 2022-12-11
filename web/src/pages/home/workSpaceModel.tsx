import { Button, Grid, List, ListItem, Paper, Skeleton, TextField } from "@mui/material"
import { useEffect, useState } from "react"
import { useSelector } from "react-redux"
import { actionGetAllUsers } from "../../redux/actions/explorerAction"
import { actionGetAllPermission } from "../../redux/actions/permissionAction"
import { actionInsertWorkSpace } from "../../redux/actions/userAction"
import { useAppDispatch } from "../../redux/hooks"
import { WorkSpaceRequestInsertModel } from "../../redux/model/workspace"
import { selectPermission } from "../../redux/storage/userStore"


const ModelWorkSpace = () => {
    const dispatch = useAppDispatch()
    const [stateCreateWorkSpace, setStateCreateWorkSpace] = useState<WorkSpaceRequestInsertModel | undefined>(undefined)

    useEffect(() => {

        dispatch(actionGetAllPermission())
        dispatch(actionGetAllUsers())
        return () => {
            
        }
    })

    return (
        <Paper sx={{
            padding: "1%",
            height: "90%"
        }}>

            <Grid container direction={"row"}
                height={'100%'}
                width={'100%'}
            >
                <Grid container md={5} item justifyContent={"center"}>

                    <List>
                        <ListItem>
                            <TextField label="Name WorkSpace" />
                        </ListItem>
                    </List>
                </Grid>

                <Grid container item md={5} direction="row">
                    <Grid container item md={5} justifyContent={"center"}>
                        <ListPermission />
                    </Grid>
                    <Grid container item md={2} justifyContent={"center"}>
                        <ListButtonWorkSpaceModal />
                    </Grid>
                    <Grid container item md={5} justifyContent={"center"}>
                        <SelectedCard />
                    </Grid>
                </Grid>

                <Grid item md={12}>
                    <Grid container item justifyContent={"center"}>
                        <Button onClick={() => {
                            if (stateCreateWorkSpace !== undefined)
                                dispatch(actionInsertWorkSpace(stateCreateWorkSpace))
                        }}>
                            Create
                        </Button>
                    </Grid>
                </Grid>
            </Grid>


        </Paper>
    )
}

export default ModelWorkSpace

const ListPermission = () => {
    const selectorPermission = useSelector(selectPermission)
    const [statePermission, setStatePermission] = useState(selectorPermission)



    useEffect(() => {
        setStatePermission(selectorPermission)
    }, [selectPermission])

    return (
        <>
            {

            }
            {
                statePermission.length > 0
                    ?
                    <List>
                        <ListItem>

                        </ListItem>

                    </List>
                    :
                    <Skeleton animation="wave" variant="rectangular" width='100%' height="100%" />
            }
        </>
    )
}

const ListButtonWorkSpaceModal = () => {
    return (
        <List>
            <ListItem>

            </ListItem>

        </List>

    )
}

const SelectedCard = () => {
    return (
        <List>
            <ListItem>

            </ListItem>

        </List>
    )
}