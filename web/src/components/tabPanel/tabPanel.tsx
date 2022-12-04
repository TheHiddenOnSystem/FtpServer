import { Box, Tab, Tabs } from "@mui/material"
import React from "react";

type HeaderInfo = {
    label:String,
    index:number
}
type ChildrenProps ={
    children?: React.ReactNode| JSX.Element;
    index: number
}
type TabPanelProps = {
    headers:Array<HeaderInfo>,
    children:Array<ChildrenProps>
}

export const TabPanel = ({headers,children}:TabPanelProps) => {
    const [value, setValue] = React.useState(0);

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    }

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider'}}>
                <Tabs 
                    value={value} 
                    onChange={handleChange}
                    variant={"fullWidth"} 
                    aria-label="basic tabs example">
                    {
                        headers.map( elem => <Tab tabIndex={elem.index} label={elem.label} {...a11yProps(elem.index)} />)
                    }
                </Tabs>
            </Box>
            {
                children.map(elem => <Panel index={elem.index} value={value}>{elem.children}</Panel>)
            }

        </Box>
    )
}

const a11yProps = (index: number) => {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}



type PanelProps = {
    children?: React.ReactNode| JSX.Element;
    index: number;
    value: number;
}

const Panel = (
    { children, value, index }: PanelProps
) => {
    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    {children}
                </Box>
            )}
        </div>
    )
}