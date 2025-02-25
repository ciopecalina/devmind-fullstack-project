const BASE_API = "http://localhost:8080";

import {renderAsync} from "docx-preview";

// Get header from sessionStorage
const getAuthHeader = () => {
    return sessionStorage.getItem("authHeader") || "";
};

// Login
export const login = async (email, password) => {
    const authHeader = "Basic " + btoa(`${email}:${password}`);

    const response = await fetch(`${BASE_API}/me`, {
        method: "GET",
        headers: {
            "Authorization": authHeader,
            "Content-Type": "application/json",
        },
    });

    if (!response.ok) {
        throw new Error("Login failed");
    }

    const userData = await response.json();

    sessionStorage.setItem("user", JSON.stringify(userData));
    sessionStorage.setItem("authHeader", authHeader);

    return userData;
};

// Invoices
export const getInvoices = async (userId) => {
    const response = await fetch(`${BASE_API}/invoices/all/${userId}`, {
        method: "GET",
        headers: {
            "Authorization": getAuthHeader(),
            "Content-Type": "application/json",
        },
    });

    if (!response.ok) {
        throw new Error("Failed to fetch invoices");
    }

    return response.json();
};

// Register user
export const register = async (userData) => {
    const response = await fetch(`${BASE_API}/register`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(userData),
    });

    if (!response.ok) {
        throw new Error("Registration failed");
    }

    return response.json();
};

// Send Email
export const sendEmail = async (invoiceId, clientName) => {
    try {
        const response = await fetch(
            `${BASE_API}/email/send?invoiceId=${invoiceId}&clientName=${encodeURIComponent(clientName)}`,
            {
                method: "POST",
                headers: {
                    "Authorization": getAuthHeader(),
                    "Content-Type": "application/json",
                },
            }
        );

        if (!response.ok) {
            throw new Error("Failed to send email");
        }

        return await response.text();
    } catch (error) {
        console.error("Error sending email:", error);
        throw error;
    }
};

//Get all users
export const getAllUsers = async () => {
    try {
        const response = await fetch(`${BASE_API}/admin/all`, {
            method: "GET",
            headers: {
                "Authorization": getAuthHeader(),
                "Content-Type": "application/json",
            },
        });

        if (!response.ok) {
            throw new Error("Failed to fetch users");
        }

        return response.json();
    } catch (error) {
        console.error("Error fetching users:", error);
        throw error;
    }
};

//User approval
export const approveUser = (id) => {
    return fetch(`${BASE_API}/admin/approve/${id}`, {
        method: "PUT",
        headers: {
            "Authorization": getAuthHeader(),
            "Content-Type": "application/json",
        },
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to approve user");
            }
            return response.text();
        });
};

//Delete user
export const deleteUser = async (id) => {
    const response = await fetch(`${BASE_API}/admin/delete/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": getAuthHeader(),
        },
    });

    if (!response.ok) throw new Error("Failed to delete user");
};

//Get all stock products
export const getStockProducts = async (userId) => {
    const response = await fetch(`${BASE_API}/stock/all/${userId}`, {
        method: "GET",
        headers: {
            "Authorization": getAuthHeader(),
            "Content-Type": "application/json",
        },
    });

    if (!response.ok) throw new Error("Failed to fetch stock products");
    return response.json();
};

//Delete stock product
export const deleteStockProduct = async (productId, userId) => {
    const response = await fetch(`${BASE_API}/stock/delete/${productId}/${userId}`, {
        method: "DELETE",
        headers: {
            Authorization: getAuthHeader(),
        },
    });

    if (!response.ok) {
        throw new Error("Failed to delete product");
    }
};

//Add stock product
export const addStockProduct = async (userId, newProduct) => {

    const response = await fetch(`${BASE_API}/stock/add/${userId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": getAuthHeader(),
        },
        body: JSON.stringify(newProduct),
    });

    if (!response.ok) {
        throw new Error("Failed to add product");
    }

    return await response.json();

};

//Get clients names
export const getClientsNames = async (userName) => {
    const response = await fetch(`${BASE_API}/clients/${encodeURIComponent(userName)}`, {
        method: "GET",
        headers: {
            "Authorization": getAuthHeader(),
            "Content-Type": "application/json"
        }
    });

    if (!response.ok) {
        throw new Error("Failed to fetch clients names");
    }

    return await response.json();
};

//Add invoice
export const saveInvoice = async (invoiceData) => {
    const response = await fetch(`${BASE_API}/invoices/add`, {
        method: "POST",
        headers: {
            "Authorization": getAuthHeader(),
            "Content-Type": "application/json"
        },
        body: JSON.stringify(invoiceData),
    });

    if (!response.ok) {
        throw new Error("Failed to save invoice");
    }

    return response.json();
};

//Delete invoice
export const deleteInvoice = async (invoiceId, userId) => {
    const response = await fetch(`${BASE_API}/invoices/delete/${invoiceId}/${userId}`, {
        method: "DELETE",
        headers: {
            Authorization: getAuthHeader(),
        },
    });

    if (!response.ok) {
        throw new Error("Failed to delete invoice");
    }
};
// Download Invoice
export const downloadInvoice = async (invoiceId, invoiceSeries, invoiceNo) => {
    try {
        const response = await fetch(`${BASE_API}/document/download-document/${invoiceId}`, {
            method: "GET",
            headers: {
                "Authorization": getAuthHeader(),
            },
        });

        if (!response.ok) {
            throw new Error("Failed to download invoice document");
        }

        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement("a");
        a.href = url;
        a.download = `Invoice_${invoiceSeries}-${invoiceNo}.docx`;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    } catch (error) {
        console.error("Error downloading document:", error);
    }
};


//Preview
export const previewInvoice = async (invoiceId) => {
    try {
        const response = await fetch(`${BASE_API}/document/download-document/${invoiceId}`, {
            method: "GET",
            headers: {
                "Authorization": getAuthHeader(),
            },
        });

        if (!response.ok) {
            throw new Error("Failed to fetch invoice document");
        }

        const blob = await response.blob();
        const div = document.createElement("div");

        await renderAsync(blob, div);

        return div.innerHTML;

    } catch (error) {
        console.error("Error previewing document: ", error);
    }
};
