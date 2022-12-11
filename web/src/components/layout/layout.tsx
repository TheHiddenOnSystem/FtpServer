import { Collapse, Drawer, IconButton, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Stack, Typography } from "@mui/material"
import { useSelector } from "react-redux"
import { selectNotification, closeSnackBar, SnackBarState } from "../../redux/storage/notificationStore"
import Snackbar from "../snackbar/snackbart"
import { useState, useEffect } from 'react'
import { useAppDispatch } from "../../redux/hooks"
import { AuthState, selectAuth } from "../../redux/storage/authStore"
import { useNavigate } from "react-router-dom"
import {
    ExpandLess,
    ExpandMore,
    Inbox,
    List as ListIcon,
    Settings,
    StarBorder
} from '@mui/icons-material'
import { URL_WEB_HOME } from "../../routes/web"




type LayoutProps = {
    children: React.ReactNode,

}

export const Layout = ({ children }: LayoutProps) => {
    const dispatch = useAppDispatch()
    const selectNotify = useSelector(selectNotification)

    const [stateSnackBar, setStateSnackBar] = useState<SnackBarState>(selectNotify.snackBar)
    const [openStateDrawer, setOpenStateDrawer] = useState<boolean>(false)

    useEffect(() => {
        setStateSnackBar(selectNotify.snackBar)
        return () => {

        }
    }, [selectNotify.snackBar])

    return (
        <>
            <Stack
                sx={{
                    height: '100vh',
                    width: '100%',
                }} spacing={3}>
                <CustomDrawer open={openStateDrawer} onClose={() => setOpenStateDrawer(false)} />
                <NavBarLayout changeStateOpenMenu={() => { setOpenStateDrawer(!openStateDrawer) }} />
                {
                    children
                }

            </Stack>
            <Snackbar
                handleClose={() => dispatch(closeSnackBar())}
                autoHideDuration={1000}
                message={stateSnackBar.message !== null
                    ? stateSnackBar.message
                    : ""}
                open={stateSnackBar.open}
                severity={
                    stateSnackBar.severity !== null
                        ? stateSnackBar.severity
                        : "info"
                }
                position={{ horizontal: "right", vertical: "bottom" }}
            />
        </>
    )
}

const ListButton = () => {
    const navigate = useNavigate();

    const [openSettings, setOpenSettings] = useState<boolean>(false)

    return (
        <List>
            <ListItem >
                <ListItemButton onClick={() => navigate(URL_WEB_HOME)}>
                    <ListItemIcon>
                        <Inbox />
                    </ListItemIcon>
                    <ListItemText primary="Home" />
                </ListItemButton>
            </ListItem>
            <ListItem >
                <ListItemButton onClick={() => setOpenSettings(true)}>
                    <ListItemIcon>
                        <Settings />
                    </ListItemIcon>
                    <ListItemText primary="Settings" />
                    {openSettings ? <ExpandLess /> : <ExpandMore />}
                </ListItemButton>
            </ListItem>
            <ListItem>
                <Collapse in={openSettings} timeout="auto" unmountOnExit>
                    <List component="div" disablePadding>
                        <ListItemButton sx={{ pl: 4 }}>
                            <ListItemIcon>
                                <StarBorder />
                            </ListItemIcon>
                            <ListItemText primary="Dont have Use" />
                        </ListItemButton>
                    </List>
                </Collapse>
            </ListItem>
        </List>
    )
}

const CustomDrawer = ({
    open,
    onClose,
}: {
    open: boolean,
    onClose: () => void
}) => {

    return (
        <Drawer
            anchor={"left"}
            open={open}
            onClose={onClose}
        >
            <ListButton />
        </Drawer>
    )
}


/*
const NavBarLayout = ({
    changeStateOpenMenu
}: {
    changeStateOpenMenu: () => void
}) => {
    const selectorAuthenticate = useSelector(selectAuth)
    const [authenticateState, setAuthenticateState] = useState<AuthState>(selectorAuthenticate)


    useEffect(() => {

        setAuthenticateState(selectorAuthenticate)
        return () => {

        }
    }, [selectorAuthenticate])


    return (
        <AppBar position="static">
            <Toolbar>
                {
                    authenticateState.isLoggin &&
                    <>
                        <IconButton onClick={changeStateOpenMenu}>
                            <ListIcon />
                        </IconButton>
                    </>

                }
                <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                    FTP Server
                </Typography>

            </Toolbar>
        </AppBar>
    )
}
*/

const NavBarLayout = ({
    changeStateOpenMenu
}: {
    changeStateOpenMenu: () => void
}) => {
    const selectorAuthenticate = useSelector(selectAuth)
    const [authenticateState, setAuthenticateState] = useState<AuthState>(selectorAuthenticate)


    useEffect(() => {

        setAuthenticateState(selectorAuthenticate)
        return () => {

        }
    }, [selectorAuthenticate])


    return (
        <Stack direction={"row"} spacing={4}>
            {
                authenticateState.isLoggin &&
                <>
                    <IconButton onClick={changeStateOpenMenu}>
                        <ListIcon />
                    </IconButton>
                </>

            }
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                FTP Server
            </Typography>
        </Stack>
    )
}