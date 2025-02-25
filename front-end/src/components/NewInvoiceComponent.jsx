import React, {useEffect, useState} from "react";
import {Autocomplete, Box, Button, Modal, TextField, Typography} from "@mui/material";
import {DatePicker, LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";
import AddIcon from "@mui/icons-material/Add";
import DeleteIcon from "@mui/icons-material/Delete";
import {DataGrid} from "@mui/x-data-grid";
import {getClientsNames, getStockProducts} from "../api/InvoicingAppApi";

const NewInvoiceComponent = ({open, onClose, onSave}) => {
    const user = JSON.parse(sessionStorage.getItem("user"));
    const userId = user.id;
    const userName = user.name;

    const [series, setSeries] = useState("");
    const [number, setNumber] = useState("");
    const [clientName, setClientName] = useState("");
    const [clients, setClients] = useState([]);
    const [date, setDate] = useState(dayjs());
    const [products, setProducts] = useState([]);
    const [stockProducts, setStockProducts] = useState([]);
    const [selectedProductIndex, setSelectedProductIndex] = useState(null);

    const [newProduct, setNewProduct] = useState({
        name: "", unitOfMeasurement: "", quantity: "", unitPrice: ""
    });

    useEffect(() => {
        const fetchData = async () => {
            if (!userId) return;
            try {
                const productData = await getStockProducts(userId);
                setStockProducts(productData);

                const clientData = await getClientsNames(userName);
                setClients(clientData);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };
        fetchData();
    }, [userId]);

    const handleAddProduct = () => {
        const {name, unitOfMeasurement, quantity, unitPrice} = newProduct;
        if (!name || !quantity || !unitPrice || quantity <= 0 || unitPrice <= 0) {
            return;
        }

        const vatRate = 0.19;
        const totalNoVat = (quantity * unitPrice).toFixed(2);
        const vat = (totalNoVat * vatRate).toFixed(2);
        const totalWithVat = (parseFloat(totalNoVat) + parseFloat(vat)).toFixed(2);

        setProducts([...products, {name, unitOfMeasurement, quantity, unitPrice, totalNoVat, vat, totalWithVat}]);
        setNewProduct({name: "", unitOfMeasurement: "", quantity: "", unitPrice: ""});
    };

    const handleDeleteProduct = () => {
        if (selectedProductIndex === null) return;
        setProducts(products.filter((_, index) => index !== selectedProductIndex));
        setSelectedProductIndex(null);
    };

    const handleSaveInvoice = () => {
        if (!series || !number || !clientName || !date || products.length == 0) {
            return;
        }

        const totalNoVat = products.reduce((sum, product) => sum + parseFloat(product.totalNoVat), 0).toFixed(2);
        const vat = products.reduce((sum, product) => sum + parseFloat(product.vat), 0).toFixed(2);
        const totalWithVat = products.reduce((sum, product) => sum + parseFloat(product.totalWithVat), 0).toFixed(2);

        onSave({
            userId,
            series,
            number,
            clientName,
            date: date.format("YYYY-MM-DD"),
            totalWithVat,
            totalNoVat,
            vat,
            products

        });


        onClose();
    };


    return (
        <Modal open={open} onClose={onClose}>
            <Box
                sx={{
                    position: "absolute",
                    top: "50%",
                    left: "50%",
                    transform: "translate(-50%, -50%)",
                    width: 900,
                    bgcolor: "background.paper",
                    p: 3,
                    display: "flex",
                    flexDirection: "column",
                    gap: 2,
                    borderRadius: 2,
                    boxShadow: 24,
                }}
            >
                <Box sx={{display: "flex", flexDirection: "column", gap: 2}}>
                    <Typography variant="h6">New Invoice</Typography>
                    <Box display="flex" gap={2}>
                        <Box width="50%" display="flex" flexDirection="column" gap={1}>
                            <TextField size="small" label="Series" value={series}
                                       onChange={(e) => setSeries(e.target.value)} fullWidth/>
                            <TextField size="small" label="Number" value={number}
                                       onChange={(e) => setNumber(e.target.value)} fullWidth/>
                        </Box>

                        <Box width="50%" display="flex" flexDirection="column" gap={1}>
                            <LocalizationProvider dateAdapter={AdapterDayjs}>
                                <DatePicker
                                    label="Date"
                                    value={date}
                                    onChange={(newValue) => setDate(newValue)}
                                    format="DD/MM/YYYY"
                                    slotProps={{textField: {size: "small", fullWidth: true}}}
                                />
                            </LocalizationProvider>
                            <Autocomplete
                                options={clients}
                                value={clientName}
                                onChange={(event, newValue) => setClientName(newValue)}
                                renderInput={(params) => <TextField {...params} label="Client Name" size="small"
                                                                    fullWidth/>}
                            />
                        </Box>
                    </Box>
                </Box>
                <Box>
                    <Typography variant="h7">Add a new product</Typography>
                    <Box display="flex" gap={2} mb={2}>
                        <Box flex={2}>
                            <Autocomplete
                                options={stockProducts.map((product) => product.name)}
                                value={newProduct.name}
                                onChange={(event, newValue) => setNewProduct({...newProduct, name: newValue})}
                                renderInput={(params) => <TextField {...params} label="Product Name" size="small"
                                                                    fullWidth/>}
                            />
                        </Box>
                        <Box width="80px">
                            <TextField size="small" label="UOM" value={newProduct.unitOfMeasurement}
                                       onChange={(e) => setNewProduct({
                                           ...newProduct,
                                           unitOfMeasurement: e.target.value
                                       })} fullWidth/>
                        </Box>
                        <Box width="100px">
                            <TextField size="small" label="Quantity" type="number" value={newProduct.quantity}
                                       onChange={(e) => setNewProduct({...newProduct, quantity: +e.target.value})}
                                       fullWidth/>
                        </Box>
                        <Box width="120px">
                            <TextField size="small" label="Unit Price" type="number" value={newProduct.unitPrice}
                                       onChange={(e) => setNewProduct({...newProduct, unitPrice: +e.target.value})}
                                       fullWidth/>
                        </Box>
                    </Box>

                    <Box display="flex" justifyContent="flex-end" gap={2} mt={2}>
                        <Button variant="contained" color="primary" startIcon={<AddIcon/>} onClick={handleAddProduct}
                                size="small">
                            Add Product
                        </Button>
                        <Button variant="contained" color="error" startIcon={<DeleteIcon/>}
                                onClick={handleDeleteProduct} size="small">
                            Delete Product
                        </Button>
                    </Box>
                </Box>
                <div style={{height: 320, width: "100%"}}>
                    <DataGrid
                        rows={products.map((product, index) => ({id: index, ...product}))}
                        columns={[
                            {field: "name", headerName: "Product", width: 200},
                            {field: "unitOfMeasurement", headerName: "UOM", width: 60},
                            {field: "quantity", headerName: "Quantity", width: 80},
                            {field: "unitPrice", headerName: "Unit Price", width: 100},
                            {field: "totalNoVat", headerName: "Total (no VAT)", width: 120},
                            {field: "vat", headerName: "VAT (19%)", width: 100},
                            {field: "totalWithVat", headerName: "Total (with VAT)", width: 140},
                        ]}
                        pageSize={5}
                        onRowSelectionModelChange={(selection) => setSelectedProductIndex(selection[0])}
                    />
                </div>
                <Box display="flex" justifyContent="flex-end" gap={2} mt={3}>
                    <Button variant="outlined" onClick={onClose}>Cancel</Button>
                    <Button variant="contained" color="primary" onClick={handleSaveInvoice}>Save Invoice</Button>
                </Box>
            </Box>
        </Modal>
    );
};

export default NewInvoiceComponent;
