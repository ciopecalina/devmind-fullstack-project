import {useEffect, useState} from "react";
import {previewInvoice} from "../api/InvoicingAppApi.js";
import {Box, CircularProgress, Modal} from "@mui/material";

const PreviewInvoiceComponent = ({invoiceId, open, onClose}) => {
    const [htmlContent, setHtmlContent] = useState("");
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchPreview = async () => {
            const content = await previewInvoice(invoiceId);
            setHtmlContent(content || "No content");
            
            setLoading(false);
        };

        fetchPreview();
    }, [invoiceId]);

    return (
        <Modal open={open} onClose={onClose}>
            <Box sx={{ background: "#fff", padding: 3, margin: "auto", width: "80%", mt: 2 , borderRadius: 2}}>
                {loading ? (
                    <CircularProgress />
                ) : (
                    <Box dangerouslySetInnerHTML={{ __html: htmlContent }}
                         sx={{
                             mt: 2,
                             maxHeight: "650px",
                             overflowY: "auto",
                             background: "#fff"
                         }}
                    />
                )}
            </Box>
        </Modal>
    );
};

export default PreviewInvoiceComponent;
