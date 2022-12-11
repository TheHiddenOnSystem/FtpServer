import { Button, Container, Paper, Skeleton, Stack, Typography } from "@mui/material"
import { useEffect, useState } from "react"
import { useSelector } from "react-redux"
import { HeaderInfo, TabPanel } from "../../components/tabPanel/tabPanel"
import { WorkSpaceDto } from "../../redux/model/user"
import { selectUserWorkSpaceSelected } from "../../redux/storage/userStore"


const headerInfo: HeaderInfo[] = [
    {
        label: "FileBrowser",
        index: 0
    },
    {
        label: "Chat",
        index: 1
    },
    {
        label: "Settings",
        index: 2
    }

]


const WorkSpace = () => {
    //const navitate = useNavigation()
    const selectorWorkSpaceSelected = useSelector(selectUserWorkSpaceSelected)
    const [stateWorkSpaceSelected, setStateWorkSpaceSelected] = useState<WorkSpaceDto | undefined>(undefined)

    useEffect(() => {

        if (selectorWorkSpaceSelected !== undefined)
            setStateWorkSpaceSelected(selectorWorkSpaceSelected)

        return () => {

        }
    }, [selectorWorkSpaceSelected])


    return (
        <Stack 
            spacing={5} height="92%" display={"flex"}>

            <Stack direction={"row"} spacing={"3%"}>
                {
                    stateWorkSpaceSelected !== undefined
                        ?
                        <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                            {stateWorkSpaceSelected?.name}
                        </Typography>
                        :
                        <Skeleton animation="wave" variant="circular" width={40} height={40} />
                }
                <Button>adjklshajklhnsdn</Button>
                <Button>adjklshajklhnsdn</Button>
                <Button>adjklshajklhnsdn</Button>
                <Button>adjklshajklhnsdn</Button>
            </Stack>

            <Paper sx={{
                padding: "3%",
                height: "100%",
                width: "90%"
            }}>
                <Container sx={{
                    height: "100%",
                    width: "100%"
                }}>
                    <TabPanel key='workSpaceKey' headers={headerInfo}>
                        {
                            [
                                {
                                    children: <FileBrowser />,
                                    index: 0
                                },
                                {
                                    children: <Chat />,
                                    index: 1
                                },
                                {
                                    children: <Settings/>,
                                    index: 2
                                }
                            ]
                        }
                    </TabPanel>
                </Container>

            </Paper>
        </Stack>
    )
}



export default WorkSpace




const FileBrowser = () => {
    return (
        <Stack height="100%" width="95%" spacing={5} justifyContent="center">
            <Stack
                spacing={4}
                direction={"row"}>
                <Skeleton animation="wave" variant="circular" width={40} height={40} />
                <Skeleton animation="wave" variant="text" width={"90%"} />
            </Stack>

            <Stack
                spacing={4}
                direction={"row"}
                height={"80%"}
                display={"block"}>

                <Skeleton
                    animation="wave" variant="rectangular"
                    width={"100%"} height={"100%"}>
                    <Typography>.</Typography>
                </Skeleton>
            </Stack>

            <Stack
                spacing={4}
                direction={"row"}>
                <Skeleton animation="wave" variant="circular" width={40} height={40} />
                <Skeleton animation="wave" variant="text" width={"90%"} />
            </Stack>
        </Stack>
    )
}


const Chat = () =>{
    return (
        <Stack height="100%" width="95%" spacing={5} justifyContent="center">
            <Stack
                spacing={4}
                direction={"row"}>
                <Skeleton animation="wave" variant="circular" width={40} height={40} />
                <Skeleton animation="wave" variant="text" width={"90%"} />
            </Stack>

            <Stack
                spacing={4}
                direction={"row"}
                height={"80%"}
                display={"block"}>

                <Skeleton
                    animation="wave" variant="rectangular"
                    width={"100%"} height={"100%"}>
                    <Typography>.</Typography>
                </Skeleton>
            </Stack>

            <Stack
                spacing={4}
                direction={"row"}>
                <Skeleton animation="wave" variant="circular" width={40} height={40} />
                <Skeleton animation="wave" variant="text" width={"90%"} />
            </Stack>
        </Stack>
    )
}

const Settings = () =>{

    return (
        <Stack height="100%" width="95%" spacing={5} justifyContent="center">
            <Stack
                spacing={4}
                direction={"row"}>
                <Skeleton animation="wave" variant="circular" width={40} height={40} />
                <Skeleton animation="wave" variant="text" width={"90%"} />
            </Stack>

            <Stack
                spacing={4}
                direction={"row"}
                height={"80%"}
                display={"block"}>

                <Skeleton
                    animation="wave" variant="rectangular"
                    width={"100%"} height={"100%"}>
                    <Typography>.</Typography>
                </Skeleton>
            </Stack>

            <Stack
                spacing={4}
                direction={"row"}>
                <Skeleton animation="wave" variant="circular" width={40} height={40} />
                <Skeleton animation="wave" variant="text" width={"90%"} />
            </Stack>
        </Stack>
    )
}