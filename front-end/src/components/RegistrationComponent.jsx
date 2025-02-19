import React, { useState } from "react";
import { Avatar, Box, Button, Container, Paper, TextField, Typography } from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { useNavigate } from "react-router-dom";
import backgroundImage from "../background.jpg";

const RegistrationComponent = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        email: "",
        password: "",
        name: "",
        fCode: "",
        regNo: "",
        iban: "",
        bank: ""
    });

    const [error, setError] = useState(null);

    const handleChange = (event) => {
        setFormData({ ...formData, [event.target.name]: event.target.value });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError(null);

        try {
            const response = await fetch("http://localhost:8080/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                throw new Error("Registration failed. Email may already be in use.");
            }

            sessionStorage.clear();
            navigate("/login");
        } catch (error) {
            setError(error.message);
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
            <Container maxWidth="xs" sx={{ position: "relative", zIndex: 1 }}>
                <Paper elevation={10} sx={{ padding: 3, backdropFilter: "blur(5px)" }}>
                    <Avatar sx={{ mx: "auto", textAlign: "center", mb: 1 }}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5" sx={{ textAlign: "center" }}>
                        Register
                    </Typography>

                    {error && <Typography color="error" textAlign="center">{error}</Typography>}

                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 2 }}>
                        <TextField label="Email" name="email" type="email" fullWidth required sx={{ mb: 2 }} value={formData.email} onChange={handleChange} />
                        <TextField label="Password" name="password" type="password" fullWidth required sx={{ mb: 2 }} value={formData.password} onChange={handleChange} />
                        <TextField label="Company Name" name="name" fullWidth required sx={{ mb: 2 }} value={formData.name} onChange={handleChange} />
                        <TextField label="Fiscal Code" name="fCode" fullWidth required sx={{ mb: 2 }} value={formData.fCode} onChange={handleChange} />
                        <TextField label="Registration Number" name="regNo" fullWidth required sx={{ mb: 2 }} value={formData.regNo} onChange={handleChange} />
                        <TextField label="IBAN" name="iban" fullWidth required sx={{ mb: 2 }} value={formData.iban} onChange={handleChange} />
                        <TextField label="Bank" name="bank" fullWidth required sx={{ mb: 2 }} value={formData.bank} onChange={handleChange} />
                        <Button type="submit" variant="contained" fullWidth sx={{ mt: 2 }}>
                            Register
                        </Button>
                    </Box>

                    <Box textAlign="center" mt={1}>
                        <Typography variant="body2">
                            Already have an account?{" "}
                            <span
                                onClick={() => navigate("/login")}
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
