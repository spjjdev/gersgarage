
import http from "../http-common";



class BookingDataService {
  getAll() {
    return http.get("/bookings");
  }

  get(booking_id) {
    return http.get(`/customers/${booking_id}`);
  }
}
export default new BookingDataService();