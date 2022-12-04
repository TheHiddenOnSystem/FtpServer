import { createSlice, PayloadAction } from "@reduxjs/toolkit"



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

const actionOpenNotification = (
    state: NotificationState,
    action: PayloadAction<String>
    ) => {
        return {
            ...state,
            notification:{
                open: true,
                message: action.payload
            }
        }
}

const actionCloseNotification = (
    state: NotificationState) => {
        return {
            ...state,
            notification:{
                open: false
            }
        }
}



interface BasicStatusNotification{
    open: boolean,
    message?: String
}

export interface NotificationState{
    popup: BasicStatusNotification
    notification: BasicStatusNotification
}

const initialNotificationState: NotificationState = {
    popup:{
        open:false
    },
    notification:{
        open:false
    }    
}



export const notificationSlice = createSlice({
    name: "notification",
    initialState: initialNotificationState,

    reducers: {
        openPopUp: actionOpenPopup,
        closePopUp: actionClosePopup,
        openNotification: actionOpenNotification,
        closeNotification: actionCloseNotification
    },


})


export const {
    openPopUp,
    closePopUp,
    openNotification,
    closeNotification
} = notificationSlice.actions

export default notificationSlice.reducer
