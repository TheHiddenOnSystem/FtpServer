import { AlertColor } from "@mui/material"
import { createSlice, PayloadAction } from "@reduxjs/toolkit"
import { RootState } from "./store"



const actionOpenPopup = (
    state: NotificationState,
    action: PayloadAction<String>
    ) => {
        return {
            ...state,
            popup:{
                open: true,
                message: action.payload
            }
        }
}

const actionClosePopup = (
    state: NotificationState
    ) => {
        return {
            ...state,
            popup:{
                open: false,
            }
        }
}

const actionOpenSnackBar = (
    state: NotificationState,
    action: PayloadAction<{
        message: String,
        severity: AlertColor|null
    }>
    ) => {
        return {
            ...state,
            snackBar:{
                open: true,
                message: action.payload.message,
                severity: action.payload.severity
            }
        }
}

const actionCloseSnackBar = (
    state: NotificationState) => {
        return {
            ...state,
            snackBar:{
                open: false,
                message: null,
                severity: null
            }
        }
}

export interface SnackBarState{
    open: boolean,
    message: String|null,
    severity: AlertColor|null
}
export interface PopUpState{
    open: boolean,
    message?: String
}

export interface NotificationState{
    popup: PopUpState,
    snackBar: SnackBarState
}

const initialNotificationState: NotificationState = {
    popup:{
        open:false
    },
    snackBar:{
        open:false,
        message: null,
        severity: null
    }    
}



export const notificationSlice = createSlice({
    name: "notification",
    initialState: initialNotificationState,

    reducers: {
        openPopUp: actionOpenPopup,
        closePopUp: actionClosePopup,
        openSnackBar: actionOpenSnackBar,
        closeSnackBar: actionCloseSnackBar
    },


})


export const {
    openPopUp,
    closePopUp,
    openSnackBar,
    closeSnackBar
} = notificationSlice.actions

export default notificationSlice.reducer
export const selectNotification = (state:RootState) => state.notify;
