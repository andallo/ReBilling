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

    // wait / paid
    private String status;
    private String tariffId;
    private YandexPayment yandexPayment;

    @Indexed
    private String customerId;
    private String customerName;

    private Double sum;
    private String sumCurrency;

    private Date createdAt;
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

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }

    public String getStatus() {
        return status;
    }

    public String getTariffId() {
        return tariffId;
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

    public Date getPaidAt() {
        return paidAt;
    }

    public YandexPayment getYandexPayment() {
        return yandexPayment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setYandexPayment(YandexPayment yandexPayment) {
        this.yandexPayment = yandexPayment;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public static class YandexPayment {
        private String extTransactionId;
        private String processingCenterId;
        private Double income;
        private String incomeCurrency;
        private String clientPaymentAccount;
        private String paymentType;
        private String paymentGateway;
        private String country;

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

        public String getProcessingCenterId() {
            return processingCenterId;
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

        public void setProcessingCenterId(String processingCenterId) {
            this.processingCenterId = processingCenterId;
        }

    }
}
