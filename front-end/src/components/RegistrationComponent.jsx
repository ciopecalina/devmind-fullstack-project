import React from "react";
import { Avatar, Box, Button, Container, Paper, TextField, Typography } from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { useNavigate } from "react-router-dom";
import backgroundImage from "../background.jpg";

const RegistrationComponent = () => {
    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log("Registration submitted");
        navigate("/user/invoices");
    };

    return (
        <Box
            sx={{
                position: "relative",
                width: "100vw",
                height: "100vh",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                overflow: "hidden",
            }}
        >
            <Box
                sx={{
                    position: "absolute",
                    width: "100%",
                    height: "100%",
                    backgroundImage: `url(${backgroundImage})`,
                    backgroundSize: "cover",
                    backgroundPosition: "center",
                    backgroundRepeat: "no-repeat",
                    filter: "blur(10px)",
                    transform: "scale(1.1)",
                }}
            />
            <Box
                sx={{
                    position: "absolute",
                    width: "100%",
                    height: "100%",
                    backgroundColor: "rgba(0, 0, 0, 0.4)",
                }}
            />
            <Container
                maxWidth="xs"
                sx={{
                    position: "relative",
                    zIndex: 1,
                }}
            >
                <Paper elevation={10} sx={{ padding: 3, backdropFilter: "blur(5px)" }}>
                    <Avatar sx={{ mx: "auto", textAlign: "center", mb: 1 }}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5" sx={{ textAlign: "center" }}>
                        Register
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 2 }}>
                        <TextField label="Email" type="email" fullWidth required sx={{ mb: 2 }} />
                        <TextField label="Password" type="password" fullWidth required sx={{ mb: 2 }} />
                        <TextField label="Company Name" fullWidth required sx={{ mb: 2 }} />
                        <TextField label="Fiscal Code" fullWidth required sx={{ mb: 2 }} />
                        <TextField label="Registration Number" fullWidth required sx={{ mb: 2 }} />
                        <TextField label="IBAN" fullWidth required sx={{ mb: 2 }} />
                        <TextField label="Bank" fullWidth required sx={{ mb: 2 }} />
                        <Button type="submit" variant="contained" fullWidth sx={{ mt: 2 }}>
                            Register
                        </Button>
                    </Box>
                    <Box textAlign="center" mt={1}>
                        <Typography variant="body2">
                            Already have an account?{" "}
                            <span
                                onClick={() => navigate("/signin")}
                                style={{
                                    textDecoration: "underline",
                                    color: "blue",
                                    fontWeight: "bold",
                                    cursor: "pointer",
                                }}
                            >
                                Log in here
                            </span>
                            .
                        </Typography>
                    </Box>
                </Paper>
            </Container>
        </Box>
    );
};

export default RegistrationComponent;
