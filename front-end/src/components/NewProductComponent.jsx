import React, {useState} from "react";
import {Box, Button, Modal, TextField, Typography} from "@mui/material";

const NewProductComponent = ({open, onClose, onSave}) => {
    const [name, setName] = useState("");
    const [uom, setUom] = useState("");
    const [quantity, setQuantity] = useState("");
    const [unitPrice, setUnitPrice] = useState("");

    const vatRate = 0.19;
    const totalNoVat = quantity && unitPrice ? (quantity * unitPrice).toFixed(2) : "0.00";
    const vat = (totalNoVat * vatRate).toFixed(2);
    const totalWithVat = (parseFloat(totalNoVat) + parseFloat(vat)).toFixed(2);

    const handleSave = () => {
        if (!name || !uom || !quantity || !unitPrice) {
            return;
        }

        const newProduct = {name, uom, quantity, unitPrice, totalNoVat, vat, totalWithVat};
        onSave(newProduct);
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
                    width: 400,
                    bgcolor: "background.paper",
                    boxShadow: 50,
                    p: 4,
                    display: "flex",
                    flexDirection: "column",
                    gap: 2,
                    borderRadius: 2,
                }}
            >
                <Typography variant="h7">Add a new product</Typography>
                <TextField label="Product Name" fullWidth value={name} onChange={(e) => setName(e.target.value)}/>
                <TextField label="Unit of Measure" fullWidth value={uom} onChange={(e) => setUom(e.target.value)}/>
                <TextField label="Quantity" type="number" fullWidth value={quantity}
                           onChange={(e) => setQuantity(e.target.value)}/>
                <TextField label="Unit Price" type="number" fullWidth value={unitPrice}
                           onChange={(e) => setUnitPrice(e.target.value)}/>
                <Typography>Total (No VAT): {totalNoVat}</Typography>
                <Typography>VAT: {vat}</Typography>
                <Typography>Total (With VAT): {totalWithVat}</Typography>
                <Box display="flex" justifyContent="space-between" mt={2}>
                    <Button variant="contained" color="primary" onClick={onClose}>
                        Cancel
                    </Button>
                    <Button variant="contained" color="primary" onClick={handleSave}>
                        Save
                    </Button>
                </Box>
            </Box>
        </Modal>
    );
};

export default NewProductComponent;
