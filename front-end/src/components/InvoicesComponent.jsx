import React, {useCallback, useEffect, useState} from "react";
import {DataGrid} from "@mui/x-data-grid";
import {downloadInvoice, getInvoices, sendEmail} from "../api/InvoicingAppApi";
import {Box, Button} from "@mui/material";
import DownloadIcon from "@mui/icons-material/Download";
import EmailIcon from "@mui/icons-material/Email";
import AddIcon from "@mui/icons-material/Add";
import DeleteIcon from "@mui/icons-material/Delete";
import VisibilityIcon from "@mui/icons-material/Visibility";

const InvoicesComponent = () => {
    const [invoices, setInvoices] = useState([]);
    const [selectedInvoice, setSelectedInvoice] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const user = JSON.parse(sessionStorage.getItem("user"));
        if (user) {
            getInvoices(user.id).then(setInvoices).catch(console.error);
        }
    }, []);

    const handleRowClick = (params) => {
        setSelectedInvoice(params.row);
    };

    const handleDownload = useCallback(async () => {
        if (!selectedInvoice) return;

        setLoading(true);
        await downloadInvoice(selectedInvoice.id, selectedInvoice.series, selectedInvoice.number);
        setLoading(false);
    }, [selectedInvoice]);

    const handleSendEmail = useCallback(async () => {
        if (!selectedInvoice || !selectedInvoice.clientName) {
            alert("No client name found for selected invoice.");
            return;
        }

        try {
            const responseMessage = await sendEmail(selectedInvoice.id, selectedInvoice.clientName);
            alert(responseMessage);
        } catch (error) {
            alert("Failed to send email");
        }
    }, [selectedInvoice]);

    const handleAdd = () => {
        alert("Add invoice clicked!");
    };

    const handleDelete = () => {
        if (!selectedInvoice) {
            alert("Select an invoice to delete!");
            return;
        }
        alert(`Delete invoice ${selectedInvoice.id}`);
    };

    return (
        <Box sx={{width: "100%", height: 400, margin: "auto", mt: 4}}>
            <Box sx={{display: "flex", gap: 2, mb: 2}}>
                <Button
                    variant="contained"
                    color="primary"
                    onClick={handleDownload}
                    disabled={!selectedInvoice || loading}
                    startIcon={<DownloadIcon/>}
                >
                    {loading ? "Downloading..." : "Download"}
                </Button>
                <Button
                    variant="contained"
                    color="primary"
                    onClick={handleSendEmail}
                    disabled={!selectedInvoice}
                    startIcon={<EmailIcon/>}>
                    Send Email
                </Button>
                <Button variant="contained" color="primary" startIcon={<VisibilityIcon/>}
                        disabled={!selectedInvoice}>
                    Preview
                </Button>
                <Button variant="contained" color="primary" startIcon={<AddIcon/>} onClick={handleAdd}>
                    Add
                </Button>
                <Button variant="contained" color="error" startIcon={<DeleteIcon/>} onClick={handleDelete}
                        disabled={!selectedInvoice}>
                    Delete
                </Button>

            </Box>

            <DataGrid
                rows={invoices}
                columns={[
                    {field: "id", headerName: "Id", width: 100},
                    {field: "series", headerName: "Series", width: 100},
                    {field: "number", headerName: "No", width: 100},
                    {field: "clientName", headerName: "Client", width: 200},
                    {field: "date", headerName: "Date", width: 150},
                    {field: "totalWithVat", headerName: "TOTAL", width: 150},
                    {field: "totalNoVat", headerName: "TOTAL (no VAT)", width: 150},
                    {field: "vat", headerName: "VAT", width: 150},
                ]}
                pageSize={10}
                onRowClick={handleRowClick}
                getRowId={(row) => row.id}
                sx={{
                    "& .MuiDataGrid-columnHeaders": {
                        fontWeight: "bold",
                        fontSize: "1rem"
                    }
                }}
            />
        </Box>
    );
};

export default InvoicesComponent;
