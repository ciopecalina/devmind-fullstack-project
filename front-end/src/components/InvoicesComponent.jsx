import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { useNavigate } from "react-router-dom";
import { getInvoices } from "../api/InvoicingAppApi";
import { Button, Box, Typography } from "@mui/material";

const InvoiceComponent = () => {
    const [invoices, setInvoices] = useState([]);
    const [selectedInvoice, setSelectedInvoice] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const user = JSON.parse(sessionStorage.getItem("user"));
        if (user) {
            getInvoices(user.id).then(setInvoices).catch(console.error);
        }
    }, []);

    const handleRowClick = (params) => {
        setSelectedInvoice(params.row);
    };

    return (
        <Box sx={{ width: "80%", margin: "auto", mt: 4 }}>
            <Typography variant="h4" sx={{ mb: 2 }}>Invoices</Typography>
            <Button
                variant="contained"
                color="primary"
                sx={{ mb: 2 }}
                onClick={() => navigate("/preview", { state: { invoice: selectedInvoice } })}
                disabled={!selectedInvoice}
            >
                Preview Invoice
            </Button>

            <DataGrid
                rows={invoices}
                columns={[
                    { field: "series", headerName: "Series", width: 100 },
                    { field: "number", headerName: "Number", width: 100 },
                    { field: "clientName", headerName: "Client", width: 200 },
                    { field: "date", headerName: "Date", width: 150 },
                    { field: "totalWithVat", headerName: "Total (€)", width: 150 },
                ]}
                pageSize={5}
                onRowClick={handleRowClick}
                getRowId={(row) => row.id}
            />
        </Box>
    );
};

export default InvoiceComponent;
