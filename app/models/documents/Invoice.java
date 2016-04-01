package models.documents;

import models.tariffs.Tariff;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.util.Date;
import java.util.List;

@Entity(value = "Invoice", noClassnameStored = true)
public class Invoice {

    @Id
    private ObjectId objectId;

    private Integer invoiceNumber;
    private Date invoiceDate;
    @Indexed
    private String customerID;
    private String customerName;
    private Date createdAt;

    @Embedded
    private Requisites sellerRequisites;

    @Embedded
    private Requisites customerRequisites;

    @Transient
    List<Tariff> tariffs;
    List<String> tariffIDs;

    public String getId() {
        return objectId == null ? null : objectId.toString();
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Requisites getSellerRequisites() {
        return sellerRequisites;
    }

    public Requisites getCustomerRequisites() {
        return customerRequisites;
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public List<String> getTariffIDs() {
        return tariffIDs;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setSellerRequisites(Requisites sellerRequisites) {
        this.sellerRequisites = sellerRequisites;
    }

    public void setCustomerRequisites(Requisites customerRequisites) {
        this.customerRequisites = customerRequisites;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public void setTariffIDs(List<String> tariffIDs) {
        this.tariffIDs = tariffIDs;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
