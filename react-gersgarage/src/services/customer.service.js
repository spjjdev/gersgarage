// code from https://bezkoder.com/react-crud-web-api/

import http from "../http-common";



class CustomerDataService {
  getAll() {
    return http.get("/customers");
  }

  get(email) {
    return http.get(`/customers/${email}`);
  }

  create(data) {
    return http.post("/add-customer", data);
  }

  update(email, data) {
    return http.put(`/customers/${email}`, data);
  }

  delete(email) {
    return http.delete(`/customers/${email}`);
  }

  deleteAll() {
    return http.delete(`/customers`);
  }

  findByEmail(email) {
    return http.get(`/customers?email=${email}`);
  }
}

export default new CustomerDataService();
