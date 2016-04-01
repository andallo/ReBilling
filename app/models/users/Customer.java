package models.users;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.util.List;

/**
 * Created by andrey on 17.10.15.
 */

@Entity(value = "Customer", noClassnameStored = true)
public class Customer {

    @Id
    private ObjectId objectId;

    private String oooName;
    private String oooInn;
    private String oooKpp;
    private String oooAddress;
    private String email;
    private String username;
    private Integer creditLimit;
    private Boolean postpayMode;
    private Boolean allowSharedPack;
    private Boolean allowSharedSubscription;
    private List<String> tariffsCollectionList;
    private Integer balance;

    public String getId() {
        return objectId == null ? null : objectId.toString();
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setOooName(String oooName) {
        this.oooName = oooName;
    }

    public void setOooInn(String oooInn) {
        this.oooInn = oooInn;
    }

    public void setOooKpp(String oooKpp) {
        this.oooKpp = oooKpp;
    }

    public String getOooName() {
        return oooName;
    }

    public String getOooInn() {
        return oooInn;
    }

    public String getOooKpp() {
        return oooKpp;
    }

    public void setOooAddress(String oooAddress) {
        this.oooAddress = oooAddress;
    }

    public String getOooAddress() {
        return oooAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAllowSharedPack(Boolean allowSharedPack) {
        this.allowSharedPack = allowSharedPack;
    }

    public void setAllowSharedSubscription(Boolean allowSharedSubscription) {
        this.allowSharedSubscription = allowSharedSubscription;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Integer getCreditLimit() {
        return creditLimit;
    }

    public Boolean getPostpayMode() {
        return postpayMode;
    }

    public List<String> getTariffsCollectionList() {
        return tariffsCollectionList;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public void setCreditLimit(Integer creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setPostpayMode(Boolean postpayMode) {
        this.postpayMode = postpayMode;
    }

    public Boolean getAllowSharedPack() {
        return allowSharedPack;
    }

    public Boolean getAllowSharedSubscription() {
        return allowSharedSubscription;
    }

    public void setTariffsCollectionList(List<String> tariffsCollectionList) {
        this.tariffsCollectionList = tariffsCollectionList;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
