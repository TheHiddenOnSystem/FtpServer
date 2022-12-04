import { Container } from "@mui/material"




type LayoutProps = {
    children: React.ReactNode
}

export const Layout = ({children}:LayoutProps)=>{

    return (
        
        <Container sx={{
            height: '100%',
            width: '100%'
        }}>
        
        {children}
        </Container>
    )
}