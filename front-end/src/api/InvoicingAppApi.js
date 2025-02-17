const BASE_API = "http://localhost:8080";

// Get header from sessionStorage
const getAuthHeader = () => {
    return sessionStorage.getItem("authHeader") || "";
};

// Users
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


export const getUserDetailsByEmail = (email) => fetch(`${BASE_API}/users/get-user-details/${email}`);

export const deleteUser = (id) => fetch(`${BASE_API}users/delete/${id}`);

export const approveUser = (id) => fetch(`${BASE_API}users/approve/${id}`);
