import { Box, Stack, Tab, Tabs } from "@mui/material"
import React from "react";

export type HeaderInfo = {
    label:String,
    index:number
}
export type ChildrenProps ={
    children?: React.ReactNode| JSX.Element;
    index: number
}
export type TabPanelProps = {
    key:React.Key,
    headers:HeaderInfo[],
    children:ChildrenProps[]
}

export const TabPanel = ({key,headers,children}:TabPanelProps) => {
    const [value, setValue] = React.useState(0);

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    }

    return (
        <Box sx={{ width: '100%', height:'100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider'}}>
                <Tabs 
                    key={key}
                    value={value} 
                    onChange={handleChange}
                    variant={"fullWidth"} 
                    aria-label="basic tabs example">
                    {
                        headers.map( (elem,index) => <Tab key={`tab-${key}-${index}-${elem.index}`} tabIndex={elem.index} label={elem.label} {...a11yProps(elem.index)} />)
                    }
                </Tabs>
            </Box>
            {
                children
                    .filter((e)=>e.index===value)
                    .map(
                        (elem,index) =>
                        <Panel key={`panel-${key}-${index}-${elem.index}`}
                            index={elem.index}
                            value={value}>{elem.children}
                        </Panel>)
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
    key:string,
    children?: React.ReactNode| JSX.Element;
    index: number;
    value: number;
}

const Panel = (
    { key,children, value, index }: PanelProps
) => {
    return (
        <Stack
            width={"100%"}
            height={"100%"}
            key={key}
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
        >
            {
                value === index && 
                    children
            }
        </Stack>
    )
}