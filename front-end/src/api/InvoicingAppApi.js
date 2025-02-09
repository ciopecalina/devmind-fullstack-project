const BASE_API = "http://localhost:8080/";

export const getUsersByIdOrderedDesc = () => fetch(`${BASE_API}/users/all-users`);

export const getUserByEmailAndName = (email, name) => fetch(`${BASE_API}/users/get-user/${email}/${name}`);

export const deleteUser = (id) => fetch(`${BASE_API}users/delete/${id}`);

export const approveUser = (id) => fetch(`${BASE_API}users/approve/${id}`);