import React from "react";
import ReactDOM from "react-dom/client";
import {createBrowserRouter, Navigate, RouterProvider} from "react-router-dom";
import App from "./App.jsx";
import DashboardLayoutWrapper from "./components/DashboardLayoutWrapper.jsx";
import InvoicesComponent from "./components/InvoicesComponent.jsx";
import StockProductsComponent from "./components/StockProductsComponent.jsx";
import LoginComponent from "./components/LoginComponent.jsx";
import RegistrationComponent from "./components/RegistrationComponent.jsx";
import {Preview} from "@mui/icons-material";
import PreviewInvoiceComponent from "./components/PreviewInvoiceComponent.jsx";
import DownloadDocumentComponent from "./components/DownloadDocumentComponent.jsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <App />,
        children: [
            { path: "/", element: <Navigate to="/login" replace /> },
            { path: "login", element: <LoginComponent /> },
            { path: "register", element: <RegistrationComponent /> },
            {
                element: <DashboardLayoutWrapper />,
                children: [
                    { path: "invoices", element: <InvoicesComponent /> },
                    { path: "stock", element: <StockProductsComponent /> },
                    { path: "preview", element: <PreviewInvoiceComponent /> },
                    { path: "download", element: <DownloadDocumentComponent /> },
                ],
            },
        ],
    },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>
);
