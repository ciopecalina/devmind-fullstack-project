import React, { useState, useCallback } from "react";

const DownloadDocumentComponent = () => {
    const [loading, setLoading] = useState(false);

    const invoiceNo = 2;

    const downloadInvoice = useCallback(() => {
        setLoading(true);
        window.location.href = "http://localhost:8080/download-document/" + invoiceNo.toString();
        setLoading(false);
    }, []);

    return (
        <button onClick={downloadInvoice} disabled={loading}>
            {loading ? "Downloading..." : "Download Invoice"}
        </button>
    );
};

export default DownloadDocumentComponent;
