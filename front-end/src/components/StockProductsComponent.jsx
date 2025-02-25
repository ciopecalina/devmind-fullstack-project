import React, {useEffect, useState} from "react";
import {Box, Button} from "@mui/material";
import {DataGrid} from "@mui/x-data-grid";
import {Add, Delete, Edit} from "@mui/icons-material";
import {addStockProduct, deleteStockProduct, getStockProducts} from "../api/InvoicingAppApi";
import NewProductComponent from "./NewProductComponent.jsx";

const StockProductsComponent = () => {
    const user = JSON.parse(sessionStorage.getItem("user"));
    const userId = user.id;

    const [products, setProducts] = useState([]);
    const [selectedProduct, setSelectedProduct] = useState(null);
    const [loading, setLoading] = useState(true);
    const [isModalOpen, setIsModalOpen] = useState(false);

    const fetchProducts = async () => {
        if (!userId) return;
        try {
            setLoading(true);
            const data = await getStockProducts(userId);
            setProducts(data);
        } catch (error) {
            console.error("Error fetching stock products:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchProducts();
    }, [userId]);

    const handleDelete = async () => {
        try {
            await deleteStockProduct(selectedProduct.id, user.id);
            fetchProducts();
        } catch (error) {
            console.error("Failed to delete product: ", error);
        }
    };

    const handleSaveProduct = async (newProduct) => {
        await addStockProduct(userId, newProduct);
        fetchProducts();
        setIsModalOpen(false);
    };

    const columns = [
        {field: "id", headerName: "ID", width: 70},
        {field: "name", headerName: "Product Name", width: 250},
        {field: "uom", headerName: "UOM", width: 100},
        {field: "quantity", headerName: "Quantity", width: 100},
        {field: "unitPrice", headerName: "Unit Price", width: 120},
        {field: "totalWithVat", headerName: "Total (With VAT)", width: 150},
        {field: "totalNoVat", headerName: "Total (No VAT)", width: 150},
        {field: "vat", headerName: "VAT", width: 100},
    ];

    return (
        <Box sx={{width: "100%", height: "100vh", padding: 3}}>
            <Box display="flex" justifyContent="flex-end" gap={2} mb={2}>
                <Button
                    variant="contained"
                    color="primary"
                    startIcon={<Add/>}
                    onClick={() => {
                        setIsModalOpen(true);
                    }}
                >
                    Add
                </Button>
                <Button variant="contained" color="primary" startIcon={<Edit/>} disabled={!selectedProduct}>
                    Edit
                </Button>
                <Button variant="contained" color="error" startIcon={<Delete/>} disabled={!selectedProduct}
                        onClick={handleDelete}>
                    Delete
                </Button>
            </Box>
            <div style={{height: 500, width: '100%'}}>
                <DataGrid
                    rows={products}
                    columns={columns}
                    loading={loading}
                    onRowSelectionModelChange={(newSelection) => {
                        if (newSelection.length > 0) {
                            setSelectedProduct(products.find((p) => p.id == newSelection[0]) || null);
                        } else {
                            setSelectedProduct(null);
                        }
                    }}
                    disableSelectionOnClick
                    sx={{
                        "& .MuiDataGrid-columnHeaders": {
                            fontWeight: "bold",
                            fontSize: "1rem"
                        }

                    }}
                />
            </div>
                {isModalOpen && (<NewProductComponent
                        open={isModalOpen}
                        onClose={() => {
                            setIsModalOpen(false);
                        }}
                        onSave={handleSaveProduct}
                    />
                )}
        </Box>
);
};

export default StockProductsComponent;

