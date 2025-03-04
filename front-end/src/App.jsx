import React from "react";
import {ReactRouterAppProvider} from "@toolpad/core/react-router";
import {Outlet} from "react-router";
import DescriptionIcon from "@mui/icons-material/Description";
import InventoryIcon from "@mui/icons-material/Inventory";

const NAVIGATION = [
    {kind: "header", title: "Main items"},
    {segment: "stock", title: "Products", icon: <InventoryIcon/>},
    {segment: "invoices", title: "Invoices", icon: <DescriptionIcon/>},
];

const BRANDING = {
    title: "InvoicingApp",
    logo: <DescriptionIcon sx={{fontSize: 40, color: "dodgerblue"}}/>,
};

export default function App() {
    return (
        <ReactRouterAppProvider navigation={NAVIGATION} branding={BRANDING}>
            <Outlet/>
        </ReactRouterAppProvider>
    );
}
