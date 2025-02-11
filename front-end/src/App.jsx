import React from "react";
import { ReactRouterAppProvider } from "@toolpad/core/react-router";
import { Outlet } from "react-router";
import DescriptionIcon from "@mui/icons-material/Description";
import CategoryIcon from "@mui/icons-material/Category";

const NAVIGATION = [
    { kind: "header", title: "Main items" },
    { segment: "stock", title: "Products", icon: <CategoryIcon /> },
    { segment: "invoices", title: "Invoices", icon: <DescriptionIcon /> },
];

const BRANDING = {
    title: "InvoicingApp",
};

export default function App() {
    return (
        <ReactRouterAppProvider navigation={NAVIGATION} branding={BRANDING}>
            <Outlet />
        </ReactRouterAppProvider>
    );
}
