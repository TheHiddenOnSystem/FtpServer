import { Container } from "@mui/material"
import { useSelector } from "react-redux"
import { selectNotification,closeSnackBar, SnackBarState } from "../../redux/notificationStore"
import Snackbar from "../snackbar/snackbart"
import {useState,useEffect} from 'react'
import { useAppDispatch } from "../../redux/hooks"




type LayoutProps = {
    children: React.ReactNode
}

export const Layout = ({children}:LayoutProps)=>{
    const dispatch = useAppDispatch()
    const selectNotify = useSelector(selectNotification)

    const [stateSnackBar, setStateSnackBar] = useState<SnackBarState>(selectNotify.snackBar)



    useEffect(()=>{
        setStateSnackBar(selectNotify.snackBar)        
        return ()=>{

        }
    },[selectNotify.snackBar])

    return (
        
        <Container sx={{
            height: '100%',
            width: '100%'
        }}>
        
        {children}

        <Snackbar 
            handleClose={()=>dispatch(closeSnackBar())}
            autoHideDuration={1000}
            message={stateSnackBar.message!==null
                ?stateSnackBar.message
                :""}
            open={stateSnackBar.open} 
            severity={
                stateSnackBar.severity!==null
                ?stateSnackBar.severity
                :"info"
                }
            position={{horizontal: "right", vertical: "bottom"}}
            />
        </Container>
    )
}


