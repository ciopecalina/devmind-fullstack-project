import React, {useEffect} from "react";
import {Outlet, useNavigate} from "react-router-dom";
import {DashboardLayout} from "@toolpad/core/DashboardLayout";
import {PageContainer} from "@toolpad/core/PageContainer";
import {AuthenticationContext, SessionContext} from "@toolpad/core/AppProvider";
import {Avatar, Box, Button, Typography} from "@mui/material";
import LogoutIcon from "@mui/icons-material/Logout";

const DashboardLayoutWrapper = () => {
    const navigate = useNavigate();
    const [session, setSession] = React.useState(null);

    useEffect(() => {
        const storedUser = localStorage.getItem("user");
        if (storedUser) {
            setSession({user: JSON.parse(storedUser)});
        }
    }, []);

    const authentication = React.useMemo(
        () => ({
            signIn: (userData) => {
                localStorage.setItem("user", JSON.stringify(userData));
                setSession({user: userData});
            },
            signOut: () => {
                localStorage.removeItem("user");
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
                            <Box sx={{display: "flex", alignItems: "center", gap: 2}}>
                                <Avatar src={session?.user.image} alt={session?.user.name}/>
                                <Box>
                                    <Typography variant="body1">{session?.user.name}</Typography>
                                    <Typography variant="body2" color="textSecondary">
                                        {session?.user.email}
                                    </Typography>
                                </Box>
                                <Button
                                    variant="contained"
                                    startIcon={<LogoutIcon/>}
                                    onClick={authentication.signOut}
                                >
                                    Logout
                                </Button>
                            </Box>
                        ),
                    }}
                >
                    <PageContainer breadcrumbs={[]} sx={{"& .MuiTypography-root": {color: "dodgerblue"}}}>
                        <Outlet/>
                    </PageContainer>
                </DashboardLayout>
            </SessionContext.Provider>
        </AuthenticationContext.Provider>
    );
};

export default DashboardLayoutWrapper;
