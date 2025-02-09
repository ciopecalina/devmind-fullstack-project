import { Route, Routes } from "react-router-dom";
import { AppProvider } from "@toolpad/core/AppProvider";
import { DashboardLayout } from "@toolpad/core/DashboardLayout";
import { PageContainer } from "@toolpad/core/PageContainer";
import SignInComponent from "./components/SigninComponent.jsx";
import RegistrationComponent from "./components/RegistrationComponent";
import DescriptionIcon from "@mui/icons-material/Description";
import CategoryIcon from "@mui/icons-material/Category";
import StockProductsComponent from "./components/StockProductsComponent.jsx";
import InvoicesComponent from "./components/InvoicesComponent.jsx";

function App() {
    const NAVIGATION = [
        {
            kind: "header",
            title: "Main items",
        },
        {
            segment: "products",
            title: "Products",
            icon: <CategoryIcon />,
        },
        {
            segment: "invoices",
            title: "Invoices",
            icon: <DescriptionIcon />,
        },
    ];

    return (
        <AppProvider
            navigation={NAVIGATION}
            branding={{
                logo: <img src="https://mui.com/static/logo.png" alt="MUI logo" />,
                title: "InvoicingApp",
                homeUrl: "/user/invoices",
            }}
        >
            <Routes>
                <Route path="/" element={<SignInComponent />} />
                <Route path="/signin" element={<SignInComponent />} />
                <Route path="/register" element={<RegistrationComponent />} />

                <Route
                    path="/user"
                    element={
                        <DashboardLayout>
                            <PageContainer />
                        </DashboardLayout>
                    }
                >
                    <Route path="stock-products" element={<StockProductsComponent />} />
                    <Route path="invoices" element={<InvoicesComponent />} />
                </Route>
            </Routes>
        </AppProvider>
    );
}

export default App;
