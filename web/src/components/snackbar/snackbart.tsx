import { Alert, Snackbar,AlertColor, SnackbarOrigin } from "@mui/material"


const CustomSnackBar = ({
    open,
    autoHideDuration,
    handleClose,
    message,
    severity,
    position
}: {
    open: boolean,
    autoHideDuration: number,
    handleClose: () => void,
    message:String,
    severity: AlertColor,
    position?: SnackbarOrigin
}) => {
    return (
        <Snackbar
            open={open}
            autoHideDuration={autoHideDuration}
            onClose={handleClose}
            anchorOrigin={position}
            >
            <Alert onClose={handleClose} severity={severity} sx={{ width: '100%' }}>
                {message}
            </Alert>
        </Snackbar>
    )
}



export default CustomSnackBar