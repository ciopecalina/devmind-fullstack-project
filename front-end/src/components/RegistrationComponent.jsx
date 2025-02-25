import React, {useState} from "react";
import {Avatar, Box, Button, Container, Paper, TextField, Typography} from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import {useNavigate} from "react-router-dom";
import backgroundImage from "../background.jpg";
import {register} from "../api/InvoicingAppApi";

const RegistrationComponent = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: "",
        fCode: "",
        regNo: "",
        iban: "",
        bank: ""
    });

    const [errors, setErrors] = useState({});

    const validateFields = () => {
        const newErrors = {
            name: !formData.name.trim(),
            email: !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email.trim()),
            password: !(formData.password.trim().length >= 5 && formData.password.trim().length <= 15),
            fCode: !(formData.fCode.trim().length >= 2 && formData.fCode.trim().length <= 10),
            regNo: !(formData.regNo.trim().length >= 8 && formData.regNo.trim().length <= 14),
            iban: formData.iban.trim().length !== 24,
            bank: !formData.bank.trim()
        };

        setErrors(newErrors);
        return !Object.values(newErrors).includes(true);
    };

    const handleChange = (event) => {
        setFormData({...formData, [event.target.name]: event.target.value});
        setErrors({...errors, [event.target.name]: false});
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!validateFields()) return;

        try {
            await register(formData);
            sessionStorage.clear();
            navigate("/login");
        } catch (error) {
            console.error("Registration error:", error.message);
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
                    backgroundColor: "rgba(0, 0, 0, 0.4)",
                }}
            />
            <Container maxWidth="xs" sx={{position: "relative", zIndex: 1}}>
                <Paper elevation={10} sx={{padding: 3, backdropFilter: "blur(5px)"}}>
                    <Avatar sx={{mx: "auto", textAlign: "center", mb: 1}}>
                        <LockOutlinedIcon/>
                    </Avatar>
                    <Typography component="h1" variant="h5" sx={{textAlign: "center"}}>
                        Register
                    </Typography>

                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt: 2}}>
                        <TextField label="Email" name="email" type="email" fullWidth required sx={{mb: 2}}
                                   value={formData.email} onChange={handleChange} error={errors.email}/>
                        <TextField label="Password" name="password" type="password" fullWidth required sx={{mb: 2}}
                                   value={formData.password} onChange={handleChange} error={errors.password}/>
                        <TextField label="Company Name" name="name" fullWidth required sx={{mb: 2}}
                                   value={formData.name} onChange={handleChange} error={errors.name}/>
                        <TextField label="Fiscal Code (CUI)" name="fCode" fullWidth required sx={{mb: 2}}
                                   value={formData.fCode} onChange={handleChange} error={errors.fCode}/>
                        <TextField label="Registration Number" name="regNo" fullWidth required sx={{mb: 2}}
                                   value={formData.regNo} onChange={handleChange} error={errors.regNo}/>
                        <TextField label="IBAN" name="iban" fullWidth required sx={{mb: 2}}
                                   value={formData.iban} onChange={handleChange} error={errors.iban}/>
                        <TextField label="Bank" name="bank" fullWidth required sx={{mb: 2}}
                                   value={formData.bank} onChange={handleChange} error={errors.bank}/>
                        <Button type="submit" variant="contained" fullWidth sx={{mt: 2}}>
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
