import React, { useState } from "react";
import { Avatar, Box, Button, Container, Paper, TextField, Typography } from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { useNavigate } from "react-router-dom";
import backgroundImage from "../background.jpg";
import { login } from "../api/InvoicingAppApi.js";

const LoginComponent = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError("");

        try {
            const userData = await login(email, password);

            // Create Basic Auth Header and save to sessionStorage
            const basicAuthHeader = "Basic " + btoa(email + ":" + password);
            sessionStorage.setItem("authHeader", basicAuthHeader);
            sessionStorage.setItem("user", JSON.stringify(userData));

            console.log("Saved to sessionStorage:", sessionStorage.getItem("user"));

            navigate("/invoices");
        } catch (err) {
            console.error("Login error:", err.message);
            setError("Invalid email or password");
        }
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
                    filter: "blur(3.5px)",
                    transform: "scale(1.1)",
                }}
            />
            <Box
                sx={{
                    position: "absolute",
                    width: "100%",
                    height: "100%",
                    backgroundColor: "rgba(0, 0, 0, 0.3)",
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
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 2 }}>
                        <TextField
                            placeholder="Enter email"
                            fullWidth
                            required
                            autoFocus
                            sx={{ mb: 2 }}
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                        />
                        <TextField
                            placeholder="Enter password"
                            fullWidth
                            required
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                        {error && (
                            <Typography color="error" variant="body2" textAlign="center" mt={1}>
                                {error}
                            </Typography>
                        )}
                        <Button type="submit" variant="contained" fullWidth sx={{ mt: 2 }}>
                            Log In
                        </Button>
                    </Box>
                    <Box textAlign="center" mt={1}>
                        <Typography variant="body2">
                            If you don't have an account,{" "}
                            <span
                                onClick={() => navigate("/register")}
                                style={{
                                    textDecoration: "underline",
                                    color: "blue",
                                    fontWeight: "bold",
                                    cursor: "pointer",
                                }}
                            >
                                register here
                            </span>
                            .
                        </Typography>
                    </Box>
                </Paper>
            </Container>
        </Box>
    );
};

export default LoginComponent;
