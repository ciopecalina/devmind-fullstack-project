const BASE_API = "http://localhost:8080";

export const login = async (email, password) => {
    const response = await fetch("http://localhost:8080/me", {
        method: "GET",
        headers: {
            "Authorization": "Basic " + btoa(`${email}:${password}`),
            "Content-Type": "application/json",
        },
    });

    if (!response.ok) {
        throw new Error("Login failed");
    }
    return response.json();
};

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


export const getUsersByIdOrderedDesc = () => fetch(`${BASE_API}/users/all-users`);

export const getUserByEmailAndName = (email, name) => fetch(`${BASE_API}/users/get-user/${email}/${name}`);

export const deleteUser = (id) => fetch(`${BASE_API}users/delete/${id}`);

export const approveUser = (id) => fetch(`${BASE_API}users/approve/${id}`);