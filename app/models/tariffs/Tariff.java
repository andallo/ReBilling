package models.tariffs;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.util.Date;
import java.util.List;

/**
 * Created by andrey on 15.10.15.
 */
@Entity(value = "Tariff", noClassnameStored = true)
public class Tariff {

    @Id
    private ObjectId objectId;

    @Indexed
    // template-active / template-inactive / paid_waiting / active / expired /
    private String status;

    @Indexed
    private String customerID;
    private String customerName;
    private String project;
    private Boolean shared;

    private String name;
    // subscription / packet / oneoff
    private String type;
    private String currency;
    private Integer sum;
    private Integer discount;
    private Integer endsum;
    // период подписки (месяцев)
    private Integer subscriptionPeriod;
    List<Operation> operations;
    private String tariffsCollection;

    private Date created;
    private Date expired;

    public String getId() {
        return objectId == null ? null : objectId.toString();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public String getCurrency() {
        return currency;
    }

    public Integer getSum() {
        return sum;
    }

    public Integer getDiscount() {
        return discount;
    }

    public Date getCreated() {
        return created;
    }

    public Date getExpired() {
        return expired;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTariffsCollection(String tariffCollection) {
        this.tariffsCollection = tariffCollection;
    }

    public String getTariffsCollection() {
        return tariffsCollection;
    }

    public Integer getEndsum() {
        return endsum;
    }

    public void setEndsum(Integer endsum) {
        this.endsum = endsum;
    }

    public Integer getSubscriptionPeriod() {
        return subscriptionPeriod;
    }

    public void setSubscriptionPeriod(Integer subscriptionPeriod) {
        this.subscriptionPeriod = subscriptionPeriod;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }
}
