import React from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { DashboardLayout } from "@toolpad/core/DashboardLayout";
import { PageContainer } from "@toolpad/core/PageContainer";
import { AuthenticationContext, SessionContext } from "@toolpad/core/AppProvider";
import { Button, Box, Typography, Avatar } from "@mui/material";
import LogoutIcon from "@mui/icons-material/Logout";

const demoSession = {
    user: {
        name: "User name",
        email: "email@outlook.com",
    },
};

const DashboardLayoutWrapper = () => {
    const navigate = useNavigate();
    const [session, setSession] = React.useState(demoSession);

    const authentication = React.useMemo(
        () => ({
            signIn: () => setSession(demoSession),
            signOut: () => {
                setSession(null);
                navigate("/login");
            },
        }),
        [navigate]
    );

    return (
        <AuthenticationContext.Provider value={authentication}>
            <SessionContext.Provider value={session}>
                <DashboardLayout
                    slots={{
                        toolbarAccount: () => (
                            <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
                                <Avatar src={session?.user.image} alt={session?.user.name} />
                                <Box>
                                    <Typography variant="body1">{session?.user.name}</Typography>
                                    <Typography variant="body2" color="textSecondary">
                                        {session?.user.email}
                                    </Typography>
                                </Box>
                                <Button
                                    variant="contained"
                                    startIcon={<LogoutIcon />}
                                    onClick={authentication.signOut}
                                >
                                    Logout
                                </Button>
                            </Box>
                        ),
                    }}
                >
                    <PageContainer breadcrumbs={[]} sx={{ "& .MuiTypography-root": { color: "dodgerblue" } }}>
                        <Outlet />
                    </PageContainer>
                </DashboardLayout>
            </SessionContext.Provider>
        </AuthenticationContext.Provider>
    );
};

export default DashboardLayoutWrapper;
