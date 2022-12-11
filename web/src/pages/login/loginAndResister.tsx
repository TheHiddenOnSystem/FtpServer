import { Button, Container, Grid, Paper, Stack, TextField } from '@mui/material'
import { useState, useCallback } from 'react'
import { TabPanel } from '../../components/tabPanel/tabPanel'
import { logginAction, registerAction } from '../../redux/actions/authAction'
import { useAppDispatch } from '../../redux/hooks'





const headers = [
    {
        label: "Logging",
        index: 0
    },
    {
        label: "Register",
        index: 1
    }
]


export const LogginAndRegister = () => {

    return (
        <Stack
            height={"70%"}
            alignContent={"center"}
            justifyContent={"center"}>

            <Container
                maxWidth={"xs"}
            >
                <Paper sx={{
                    padding: "3%"
                }}>
                    <TabPanel key='logginAndResgister' headers={headers}>
                        {
                            [
                                {
                                    children: <Loggin />,
                                    index: 0
                                },
                                {
                                    children: <Register />,
                                    index: 1
                                }
                            ]
                        }
                    </TabPanel>
                </Paper>
            </Container>
        </Stack>

    )
}



const Loggin = () => {
    const dispatch = useAppDispatch()

    const [state, setState] = useState<{
        username: String,
        password: String
    }>({
        username: "username",
        password: "password"
    })


    const actionSubmit = useCallback(() => {
        dispatch(logginAction(state))
    }, [state, dispatch])

    return (
        <Grid container direction="column" spacing={4}>
            <Grid item container justifyContent={"center"}>
                <TextField
                    id="outlined-error"
                    label="UserName"
                    defaultValue="Hello World"
                    onChange={(e) => setState({ ...state, username: e.target.value })}
                />
            </Grid>
            <Grid item container justifyContent={"center"}>
                <TextField
                    id="outlined-error-helper-text"
                    label="Password"
                    defaultValue="Enter Password"
                    type={'password'}
                    onChange={(e) => setState({ ...state, password: e.target.value })}
                />
            </Grid>
            <Grid item container justifyContent={"center"}>
                <Button fullWidth onClick={actionSubmit}>Loggin</Button>
            </Grid>
        </Grid>
    )
}

const Register = () => {
    const dispatch = useAppDispatch();
    const [state, setState] = useState<{
        username: String,
        password: String,
        email: String
    }>({
        username: "username",
        password: "password",
        email: "email"
    })

    /*
const [validateState,setValidateState] = useState<{
        username:Boolean,
            email:Boolean,
            password:Boolean,
    }>({
                username:true,
            email: true,
            password: true
    })
            */
    const actionSubmit = useCallback(() => {
        dispatch(registerAction(state))
    }, [state, dispatch])

    return (
        <Grid container direction="column" spacing={4}>
            <Grid item container justifyContent={"center"}>
                <TextField
                    id="outlined-error"
                    label="UserName"
                    defaultValue="Hello World"
                    onChange={(e) => setState({ ...state, username: e.target.value })}
                />
            </Grid>
            <Grid item container justifyContent={"center"}>
                <TextField
                    id="outlined-error-helper-text"
                    label="Email"
                    defaultValue="Enter email"
                    onChange={(e) => setState({ ...state, email: e.target.value })}

                />
            </Grid>
            <Grid item container justifyContent={"center"}>
                <TextField
                    id="outlined-error-helper-text"
                    label="Password"
                    type={'password'}
                    defaultValue="Enter Password"
                    onChange={(e) => setState({ ...state, password: e.target.value })}
                />
            </Grid>
            <Grid container item justifyContent={"center"}>
                <Button fullWidth onClick={actionSubmit}>Register</Button>
            </Grid>
        </Grid>
    )
}






