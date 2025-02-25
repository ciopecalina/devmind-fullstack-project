import React, {useEffect, useState} from "react";
import {DataGrid} from "@mui/x-data-grid";
import {
    deleteInvoice,
    downloadInvoice,
    getInvoices as fetchInvoices,
    saveInvoice,
    sendEmail
} from "../api/InvoicingAppApi";
import {Alert, Box, Button, Snackbar} from "@mui/material";
import DownloadIcon from "@mui/icons-material/Download";
import EmailIcon from "@mui/icons-material/Email";
import AddIcon from "@mui/icons-material/Add";
import DeleteIcon from "@mui/icons-material/Delete";
import VisibilityIcon from "@mui/icons-material/Visibility";
import NewInvoiceComponent from "./NewInvoiceComponent.jsx";
import PreviewInvoiceComponent from "./PreviewInvoiceComponent.jsx";

const InvoicesComponent = () => {
        const [invoices, setInvoices] = useState([]);
        const [selectedInvoice, setSelectedInvoice] = useState(null);
        const [loading, setLoading] = useState(false);
        const [isModalOpen, setIsModalOpen] = useState(false);
        const [isPreviewModalOpen, setIsPreviewModalOpen] = useState(false);
        const [openSnackbar, setOpenSnackbar] = React.useState(false);

        const user = JSON.parse(sessionStorage.getItem("user"));
        const userId = user.id;

        const getInvoices = async () => {
            if (!userId) return;
            try {
                setLoading(true);
                const data = await fetchInvoices(userId);
                setInvoices(data);
            } catch (error) {
                console.error("Error fetching invoices:", error);
            } finally {
                setLoading(false);
            }
        };

        useEffect(() => {
            getInvoices();
        }, [userId]);

        const handleRowClick = (params) => {
            setSelectedInvoice(params.row);
        };

        const handleDownload = async () => {
            if (!selectedInvoice) return;

            setLoading(true);
            await downloadInvoice(selectedInvoice.id, selectedInvoice.series, selectedInvoice.number);
            setLoading(false);
        };

        const handleSendEmail = async () => {
            if (!selectedInvoice || !selectedInvoice.clientName) {
                console.error("No client name found for selected invoice.");
                return;
            }

            try {
                const responseMessage = await sendEmail(selectedInvoice.id, selectedInvoice.clientName);
                console.error(responseMessage);
                setOpenSnackbar(true);
            } catch (error) {
                console.error("Failed to send email", error);
            }
        };

        const handleAdd = () => {
            setIsModalOpen(true);
        };

        const handleClose = () => {
            setIsModalOpen(false);
        };

        const handleDelete = async () => {
            if (!selectedInvoice) return;

            try {
                await deleteInvoice(selectedInvoice.id, userId);
                await getInvoices();
                setSelectedInvoice(null);
            } catch (error) {
                console.error("Failed to delete invoice", error);
            }
        };

        const handleSaveInvoice = async (invoiceData) => {
            try {
                await saveInvoice(invoiceData);
                await getInvoices();
                setIsModalOpen(false);
            } catch (error) {
                console.error("Error saving invoice:", error);
            }
        };

        const handleModalPreview = () => {
            if (selectedInvoice) {
                setIsPreviewModalOpen(true);
            }
        };

        const handleCloseModalPreview = () => {
            setIsPreviewModalOpen(false);
        };

        const handleCloseSnackbar = () => {
            setOpenSnackbar(false);
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
                            disabled={!selectedInvoice} onClick={handleModalPreview}>
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

                <div style={{height: 500, width: '100%'}}>
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
                </div>
                {isModalOpen && (
                    <NewInvoiceComponent open={isModalOpen} onClose={handleClose} onSave={handleSaveInvoice}/>
                )}
                {isPreviewModalOpen && (
                    <PreviewInvoiceComponent invoiceId={selectedInvoice.id} open={isPreviewModalOpen}
                                             onClose={handleCloseModalPreview}/>
                )}
                <Snackbar
                    open={openSnackbar}
                    autoHideDuration={2000}
                    onClose={handleCloseSnackbar}
                    anchorOrigin={{vertical: "top", horizontal: "center"}}
                >
                    <Alert
                        onClose={handleCloseSnackbar}
                        severity="success"
                        variant="filled"
                        sx={{width: '100%'}}
                    >
                        Email sent successfully!
                    </Alert>
                </Snackbar>
            </Box>

        );
    }
;

export default InvoicesComponent;
