package models.payments;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.util.Date;

/**
 * Created by andrey on 08.11.15.
 */

@Entity(value = "Payment", noClassnameStored = true)
public class Payment {

    @Id
    private ObjectId objectId;

    // authorized / completed
    private String status;
    private String tariffId;

    private String extTransactionId;
    private String processingCenterId;

    @Indexed
    private String customerId;
    private String customerName;

    private Double sum;
    private String sumCurrency;
    private Double income;
    private String incomeCurrency;
    private String clientPaymentAccount;

    /*------- Яндекс.Касса
                        AB - Альфа-Клик
                        AC - банковская карта
                        GP - наличные через терминал
                        MA - MasterPass
                        MC - мобильная коммерция
                        PB  -интернет-банк Промсвязьбанка
                        PC - кошелек Яндекс.Денег
                        SB - Сбербанк Онлайн
                        WM - кошелек WebMoney
     */
    private String paymentType;
    private String paymentGateway;
    private String country;

    private Date authorizedAt;
    private Date paidAt;

    public String getId() {
        return objectId == null ? null : objectId.toString();
    }

    public ObjectId getObjectId() {
        return objectId;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setTariffId(String tariffId) {
        this.tariffId = tariffId;
    }

    public void setProcessingCenterId(String processingCenterId) {
        this.processingCenterId = processingCenterId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setSumCurrency(String sumCurrency) {
        this.sumCurrency = sumCurrency;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public void setIncomeCurrency(String incomeCurrency) {
        this.incomeCurrency = incomeCurrency;
    }

    public void setClientPaymentAccount(String clientPaymentAccount) {
        this.clientPaymentAccount = clientPaymentAccount;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setPaymentGateway(String paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void setAuthorizedAt(Date authorizedAt) {
        this.authorizedAt = authorizedAt;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }

    public String getStatus() {
        return status;
    }

    public String getTariffId() {
        return tariffId;
    }

    public String getProcessingCenterId() {
        return processingCenterId;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getSumCurrency() {
        return sumCurrency;
    }

    public Double getIncome() {
        return income;
    }

    public String getIncomeCurrency() {
        return incomeCurrency;
    }

    public String getClientPaymentAccount() {
        return clientPaymentAccount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getPaymentGateway() {
        return paymentGateway;
    }

    public Date getAuthorizedAt() {
        return authorizedAt;
    }

    public Date getPaidAt() {
        return paidAt;
    }

    public String getExtTransactionId() {
        return extTransactionId;
    }

    public void setExtTransactionId(String extTransactionId) {
        this.extTransactionId = extTransactionId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
