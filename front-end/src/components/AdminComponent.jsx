import React, {useEffect, useState} from "react";
import {approveUser, deleteUser, getAllUsers} from "../api/InvoicingAppApi";
import {DataGrid} from "@mui/x-data-grid";
import {Box, Button, Container, Paper, Typography} from "@mui/material";
import CheckIcon from "@mui/icons-material/Check";
import CloseIcon from "@mui/icons-material/Close";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import DeleteIcon from "@mui/icons-material/Delete";
import LogoutIcon from "@mui/icons-material/Logout";
import backgroundImage from "../background.jpg";
import {useNavigate} from "react-router-dom";

const AdminComponent = () => {
    const [users, setUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const fetchUsers = async () => {
        try {
            setLoading(true);
            const userData = await getAllUsers();
            setUsers(userData);
        } catch (error) {
            console.error("Error fetching users:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    const handleApprove = async () => {
        try {
            await approveUser(selectedUser.id);
            fetchUsers();
        } catch (error) {
            console.error("Failed to approve user: " + error.message);
        }
    };

    const handleDelete = async () => {
        try {
            await deleteUser(selectedUser.id);
            fetchUsers();
        } catch (error) {
            console.error("Failed to delete user: " + error.message);
        }
    };

    const handleLogout = () => {
        sessionStorage.clear();
        navigate("/login");
    };

    const columns = [
        {field: "id", headerName: "ID", width: 100},
        {field: "name", headerName: "Name", width: 200},
        {field: "email", headerName: "Email", width: 250},
        {
            field: "isApproved",
            headerName: "Approved",
            width: 150,
            renderCell: (params) =>
                params.value ? (
                    <CheckIcon style={{color: "green"}}/>
                ) : (
                    <CloseIcon style={{color: "red"}}/>
                ),
        },
    ];

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
                    filter: "blur(4px)",
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
                maxWidth="md"
                sx={{
                    position: "relative",
                    zIndex: 1,
                }}
            >
                <Paper elevation={10} sx={{padding: 3, backdropFilter: "blur(5px)", position: "relative"}}>
                    <Box sx={{display: "flex", justifyContent: "space-between", alignItems: "center", mb: 2}}>
                        <Typography variant="h5">User Approval List</Typography>
                        <Box>
                            <Button
                                variant="contained"
                                color="success"
                                startIcon={<CheckCircleIcon/>}
                                onClick={handleApprove}
                                sx={{mr: 2}}
                                disabled={!selectedUser}
                            >
                                Approve
                            </Button>
                            <Button
                                variant="contained"
                                color="error"
                                startIcon={<DeleteIcon/>}
                                onClick={handleDelete}
                                disabled={!selectedUser}
                            >
                                Delete
                            </Button>
                        </Box>
                    </Box>

                    <Box sx={{height: 400}}>
                        <DataGrid
                            rows={users}
                            columns={columns}
                            pageSize={10}
                            loading={loading}
                            onRowClick={(params) => setSelectedUser(params.row)}
                            getRowId={(row) => row.id}
                            sx={{
                                "& .MuiDataGrid-columnHeaders": {
                                    fontWeight: "bold",
                                },
                            }}
                        />
                    </Box>

                    <Box sx={{display: "flex", justifyContent: "flex-end", mt: 2}}>
                        <Button variant="contained" color="primary" startIcon={<LogoutIcon/>} onClick={handleLogout}>
                            Logout
                        </Button>
                    </Box>
                </Paper>
            </Container>
        </Box>
    );
};

export default AdminComponent;
