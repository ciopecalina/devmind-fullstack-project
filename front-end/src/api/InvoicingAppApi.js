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
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userData),
    });

    if (!response.ok) {
        throw new Error("Registration failed");
    }

    return response.json();
};

//
export const downloadInvoiceDocument = async (invoiceSeries, invoiceNo) => {
    const response = await fetch(`${BASE_API}/download-document?invoiceSeries=${invoiceSeries}&invoiceNo=${invoiceNo}`, {
        method: "GET",
        headers: {
            "Authorization": getAuthHeader(),
        },
    });

    if (!response.ok) {
        throw new Error("Failed to download invoice document");
    }

    return response.blob();
};

export const getUsersByIdOrderedDesc = () => fetch(`${BASE_API}/users/all-users`);

export const getUserDetailsByEmail = (email) => fetch(`${BASE_API}/users/get-user-details/${email}`);

export const deleteUser = (id) => fetch(`${BASE_API}users/delete/${id}`);

export const approveUser = (id) => fetch(`${BASE_API}users/approve/${id}`);
