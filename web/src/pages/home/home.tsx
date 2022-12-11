import { Button, Card, CardContent, CardHeader, Grid, IconButton, List, ListItem, Paper, Skeleton, Stack, TextField, Typography } from "@mui/material"
import { SupervisedUserCircle } from "@mui/icons-material"
import { useSelector } from "react-redux"
import { selectUserProfile, selectUserWorkSpace } from "../../redux/storage/userStore"
import { useAppDispatch } from "../../redux/hooks"
import { actionGetMyUserInfo, actionSelectWorkSpace } from "../../redux/actions/userAction"
import { useEffect, useState } from "react"
import { WorkSpaceDto } from "../../redux/model/user"
import { useNavigate } from "react-router-dom"
import { URL_WEB_SHOW_WORKSPACE } from "../../routes/web"
import { HeaderInfo, TabPanel } from "../../components/tabPanel/tabPanel"
import ModelWorkSpace from "./workSpaceModel"


const headerInfo: HeaderInfo[] = [
    {
        index: 0,
        label: "ViewWorkSpace"
    },
    {
        index: 1,
        label: "CreateWorkSpace"
    },
    {
        index: 2,
        label: "ViewWorkSpace"
    }
]

const Home = () => {
    const dispatch = useAppDispatch()

    useEffect(() => {
        dispatch(actionGetMyUserInfo())

        return () => {

        }
    }, [dispatch])


    return (
        <>
            <Stack>
                <CardUser />
            </Stack>
            <TabPanel key={"tabPanelHome"} headers={headerInfo}>
                {
                    [
                        {
                            index: 0,
                            children: <ViewWorkSpace />
                        },
                        {
                            index: 1,
                            children: <ModelWorkSpace />
                        },
                        {
                            index: 2,
                            children: <ViewWorkSpace />
                        }
                    ]
                }
            </TabPanel>
        </>
    )
}

export default Home





const CardUser = () => {

    const selectorUserProfile = useSelector(selectUserProfile)
    const [profileUserState, setProfileUserState] = useState(selectorUserProfile)

    useEffect(() => {
        setProfileUserState(selectorUserProfile)

        return () => {

        }
    }, [selectorUserProfile])

    return (
        <Paper sx={{
            width: '100%',
            padding: '2%'
        }}>
            <Stack direction={"row"} spacing={4} alignContent={"center"}>
                {
                    selectorUserProfile.name !== undefined
                        ?
                        <IconButton>
                            <SupervisedUserCircle />
                        </IconButton>
                        :
                        <Skeleton animation="wave" variant="circular" width={40} height={40} />
                }
                {
                    selectorUserProfile.name !== undefined
                        ?
                        <Typography>{profileUserState.username}</Typography>
                        :
                        <Skeleton animation="wave" variant="rectangular" width='20%' height={40}>
                            <Typography>.</Typography>
                        </Skeleton>
                }

            </Stack>
        </Paper>
    )
}


const ViewWorkSpace = () => {
    const selectorWorkSpace = useSelector(selectUserWorkSpace)
    const [stateWorkSpaceUser, setStateWorkSpaceUser] = useState(selectorWorkSpace)

    useEffect(() => {
        setStateWorkSpaceUser(selectorWorkSpace)

        return () => {

        }
    }, [selectorWorkSpace])

    return (
        <Stack>
            {
                stateWorkSpaceUser &&
                stateWorkSpaceUser.map(elem => <CardWorkSpace workSpace={elem} />)
            }
        </Stack>
    )
}


const CardWorkSpace = ({ workSpace }: {
    workSpace: WorkSpaceDto
}) => {
    const dispatch = useAppDispatch()
    const navigate = useNavigate()



    return (
        <Card>
            <CardHeader>

            </CardHeader>
            <CardContent>

                <Grid container>
                    <Grid item>
                        <Typography>{workSpace.name}</Typography>
                    </Grid>
                    <Grid>
                        <Button onClick={() => {
                            dispatch(actionSelectWorkSpace(workSpace.name))
                            navigate(URL_WEB_SHOW_WORKSPACE, { replace: true })
                        }}> asdasda s</Button>
                    </Grid>
                </Grid>
            </CardContent>

        </Card>
    )
}
