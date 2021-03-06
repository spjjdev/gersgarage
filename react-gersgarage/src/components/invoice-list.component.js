import React, { Component } from "react";
import InvoiceDataService from "../services/invoice.service";
import { Link } from "react-router-dom";

export default class InvoiceList extends Component {
  constructor(props) {
    super(props);
    this.onChangeSearchInvoiceId = this.onChangeSearchInvoiceId.bind(this);
    this.retrieveInvoice = this.retrieveInvoice.bind(this);
    this.refreshList = this.refreshList.bind(this);
    this.setActiveInvoice = this.setActiveInvoice.bind(this);
    this.searchInvoiceId = this.searchInvoiceId.bind(this);

    this.state = {
      invoice: [],
      currentInvoice: null,
      currentIndex: -1,
      searchInvoiceId: "",
    };
  }

  componentDidMount() {
    this.retrieveInvoice();
  }

  onChangeSearchInvoiceId(e) {
    const searchInvoiceId= e.target.value;

    this.setState({
      searchInvoiceId: searchInvoiceId,
    });
  }

  retrieveInvoice() {
    InvoiceDataService.getAll()
      .then((response) => {
        this.setState({
          invoice: response.data,
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  refreshList() {
    this.retrieveInvoice();
    this.setState({
      currentInvoice: null,
      currentIndex: -1,
    });
  }

  setActiveInvoice(invoice, index) {
    this.setState({
      currentInvoice: invoice,
      currentIndex: index,
    });
    console.log ("invoice")
  }

  searchInvoiceId() {
    InvoiceDataService.findByInvoiceId(this.state.searchInvoiceId)
      .then((response) => {
        this.setState({
          invoice: response.data,
        });
        console.log(response.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  render() {
    const {
      searchInvoiceId,
      invoice,
      currentInvoice,
      currentIndex,
    } = this.state;
    console.log(invoice);

    return (
      <div className="list row">
        

        <div className="col-md-6">
          <h4>Invoice List</h4>

          <ul className="list-group">
            {invoice &&
              invoice.map((invoice, index) => (
                <li
                  className={
                    "list-group-item " +
                    (index === currentIndex ? "active" : "")
                  }
                  onClick={() => this.setActiveInvoice(invoice, index)}
                  key={index}
                >
                  {invoice.invoice_id }
                </li>
              ))}
          </ul>
        </div>

        <div className="col-md-6">
          {currentInvoice ? (
            <div>
              <h4>Invoice</h4>
              <div>
                <label>
                  <strong>Invoice ID:</strong>
                </label>{" "}
                {currentInvoice.invoice_id}
              </div>
              <div>
                <label>
                  <strong>Supplies:</strong>
                </label>{" "}
                {currentInvoice.invoiceSupplies}
              </div>
              <div>
                <label>
                  <strong>Booking:</strong>
                </label>{" "}
                {currentInvoice.booking.booking_id}
              </div>
              

              
            </div>
          ) : (
            <div>
              <br />
              <p>Please click on a Invoice...</p>
            </div>
          )}
        </div>
      </div>
    );
  }
}