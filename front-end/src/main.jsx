import React from "react";
import ReactDOM from "react-dom/client";
import {createBrowserRouter, Navigate, RouterProvider} from "react-router-dom";
import App from "./App.jsx";
import DashboardLayoutWrapper from "./components/DashboardLayoutWrapper.jsx";
import InvoicesComponent from "./components/InvoicesComponent.jsx";
import StockProductsComponent from "./components/StockProductsComponent.jsx";
import LoginComponent from "./components/LoginComponent.jsx";
import RegistrationComponent from "./components/RegistrationComponent.jsx";
import AdminComponent from "./components/AdminComponent.jsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <App />,
        children: [
            { path: "/", element: <Navigate to="/login" replace /> },
            { path: "login", element: <LoginComponent /> },
            { path: "register", element: <RegistrationComponent /> },
            { path: "admin", element: <AdminComponent /> },
            {
                element: <DashboardLayoutWrapper />,
                children: [
                    { path: "invoices", element: <InvoicesComponent /> },
                    { path: "stock", element: <StockProductsComponent /> },
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
