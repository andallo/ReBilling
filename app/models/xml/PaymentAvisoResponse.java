package models.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by andrey on 08.11.15.
 */

@XmlRootElement
public class PaymentAvisoResponse {
    private String performedDatetime;
    private Long code;
    private Long shopId;
    private String orderSumAmount;
    private String message;
    private String techMessage;
    private Long invoiceId;

    public void setPerformedDatetime(String performedDatetime) {
        this.performedDatetime = performedDatetime;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTechMessage(String techMessage) {
        this.techMessage = techMessage;
    }

    public void setOrderSumAmount(String orderSumAmount) {
        this.orderSumAmount = orderSumAmount;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    @XmlAttribute
    public String getPerformedDatetime() {
        return performedDatetime;
    }
    @XmlAttribute
    public Long getCode() {
        return code;
    }
    @XmlAttribute
    public Long getShopId() {
        return shopId;
    }
    @XmlAttribute
    public String getOrderSumAmount() {
        return orderSumAmount;
    }
    @XmlAttribute
    public String getMessage() {
        return message;
    }
    @XmlAttribute
    public String getTechMessage() {
        return techMessage;
    }
    @XmlAttribute
    public Long getInvoiceId() {
        return invoiceId;
    }
}
