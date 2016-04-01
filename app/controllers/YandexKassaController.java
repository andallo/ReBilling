package controllers;

import controllers.utils.InvoiceUtils;
import controllers.utils.XmlUtils;
import models.mongo.CustomerDS;
import models.mongo.PaymentDS;
import models.mongo.TariffsDS;
import models.payments.Payment;
import models.tariffs.Tariff;
import models.users.Customer;
import models.xml.CheckOrderResponse;
import models.xml.PaymentAvisoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Date;
import java.util.Map;

public class YandexKassaController extends Controller {

    private static final Logger log = LoggerFactory.getLogger("payments.yandex-kassa");

    private static final long RESPONSE_SUCCESS = 0;
    private static final long RESPONSE_MD5_ERROR = 1;
    private static final long RESPONSE_EXACT_ERROR = 100;
    private static final long RESPONSE_ERROR = 200;

    public Result avisoURL() {
        log.info("AvisoURL Request\n" + logRequest());
        String invoiceId = "";

        try {
            String _md5 = "";
            String _shopId = ConfigController.getConfigParam("yandex_kassa_shop_id");
            String _secret = ConfigController.getConfigParam("yandex_kassa_secret");
            Payment _payment = null;

            DynamicForm df = Form.form().bindFromRequest();
            String requestDatetime = df.get("requestDatetime");
            String action = df.get("action");
            String md5 = df.get("md5");
            String shopId = df.get("shopId");
            String shopArticleId = df.get("shopArticleId");
            invoiceId = df.get("invoiceId");
            String orderNumber = df.get("orderNumber");
            String customerNumber = df.get("customerNumber");
            String orderCreatedDatetime = df.get("orderCreatedDatetime");
            String orderSumAmount = df.get("orderSumAmount");
            String orderSumCurrencyPaycash = df.get("orderSumCurrencyPaycash");
            String orderSumBankPaycash = df.get("orderSumBankPaycash");
            String shopSumAmount = df.get("shopSumAmount");
            String shopSumCurrencyPaycash = df.get("shopSumCurrencyPaycash");
            String shopSumBankPaycash = df.get("shopSumBankPaycash");
            String paymentPayerCode = df.get("paymentPayerCode");
            String paymentType = df.get("paymentType");
            String cps_user_country_code = df.get("cps_user_country_code");
            // Свои параметры - ?

            StringBuffer sb = new StringBuffer();
            sb.append(action).append(";");
            sb.append(orderSumAmount).append(";");
            sb.append(orderSumCurrencyPaycash).append(";");
            sb.append(orderSumBankPaycash).append(";");
            sb.append(shopId).append(";");
            sb.append(invoiceId).append(";");
            sb.append(customerNumber).append(";");
            sb.append(_secret);
            _md5 = MD5(sb.toString());

            PaymentAvisoResponse paymentAvisoResponse = new PaymentAvisoResponse();
            paymentAvisoResponse.setPerformedDatetime(XmlUtils.getXsDateTime(new Date()));
            paymentAvisoResponse.setShopId(Long.valueOf(_shopId));
            paymentAvisoResponse.setInvoiceId(Long.valueOf(invoiceId));
            if (md5 == null || md5.equals("") || !md5.equalsIgnoreCase(_md5)) {
                paymentAvisoResponse.setCode(RESPONSE_MD5_ERROR);
            } else {
                paymentAvisoResponse.setOrderSumAmount(orderSumAmount);
                paymentAvisoResponse.setCode(RESPONSE_SUCCESS);

                _payment = PaymentDS.getPaymentByExtTransactionID(invoiceId);
                _payment.setStatus("completed");
                _payment.setCountry(cps_user_country_code);
                _payment.setPaidAt(new Date());
                PaymentDS.save(_payment);
            }

            String response = XmlUtils.getXmlString(PaymentAvisoResponse.class, paymentAvisoResponse);
            log.info("AvisoURL Response\n" + response );
            return ok(response).as("application/xml");
        }catch (Throwable t) {
            log.error("AvisoURL handled with error\ninvoiceId: " + invoiceId, t);
            try {
                PaymentAvisoResponse paymentAvisoResponse = new PaymentAvisoResponse();
                paymentAvisoResponse.setPerformedDatetime(XmlUtils.getXsDateTime(new Date()));
                paymentAvisoResponse.setShopId(Long.valueOf(ConfigController.getConfigParam("yandex_kassa_shop_id")));
                paymentAvisoResponse.setInvoiceId(Long.valueOf(invoiceId));
                paymentAvisoResponse.setCode(RESPONSE_ERROR);

                String response = XmlUtils.getXmlString(PaymentAvisoResponse.class, paymentAvisoResponse);
                log.info("AvisoURL Response\n" + response);
                return ok(response).as("application/xml");
            } catch (Throwable t2) {
                t2.printStackTrace();
                return internalServerError();
            }
        }
    }

    public Result checkOrderURL() {
        log.info("CheckOrderURL Request\n" + logRequest());

        String invoiceId = "";

        try {
            String _md5 = "";
            String _shopId = ConfigController.getConfigParam("yandex_kassa_shop_id");
            String _secret = ConfigController.getConfigParam("yandex_kassa_secret");
            Customer _customer = null;
            Tariff _tariff = null;
            Payment _payment = null;

            DynamicForm df = Form.form().bindFromRequest();
            String requestDatetime = df.get("requestDatetime");
            String action = df.get("action");
            String md5 = df.get("md5");
            String shopId = df.get("shopId");
            String shopArticleId = df.get("shopArticleId");
            invoiceId = df.get("invoiceId");
            String orderNumber = df.get("orderNumber");
            String customerNumber = df.get("customerNumber");
            String orderCreatedDatetime = df.get("orderCreatedDatetime");
            String orderSumAmount = df.get("orderSumAmount");
            String orderSumCurrencyPaycash = df.get("orderSumCurrencyPaycash");
            String orderSumBankPaycash = df.get("orderSumBankPaycash");
            String shopSumAmount = df.get("shopSumAmount");
            String shopSumCurrencyPaycash = df.get("shopSumCurrencyPaycash");
            String shopSumBankPaycash = df.get("shopSumBankPaycash");
            String paymentPayerCode = df.get("paymentPayerCode");
            String paymentType = df.get("paymentType");
            // Свои параметры - ?

            StringBuffer sb = new StringBuffer();
            sb.append(action).append(";");
            sb.append(orderSumAmount).append(";");
            sb.append(orderSumCurrencyPaycash).append(";");
            sb.append(orderSumBankPaycash).append(";");
            sb.append(shopId).append(";");
            sb.append(invoiceId).append(";");
            sb.append(customerNumber).append(";");
            sb.append(_secret);

            _md5 = MD5(sb.toString());
            if (orderNumber != null && orderNumber.equals("order777")) {
                // тестовый режим
                _tariff = new Tariff();
                _tariff.setCurrency("RUB");
                _tariff.setEndsum(100);
                _customer = new Customer();
            }

            CheckOrderResponse checkOrderResponse = new CheckOrderResponse();
            checkOrderResponse.setPerformedDatetime(XmlUtils.getXsDateTime(new Date()));
            checkOrderResponse.setShopId(Long.valueOf(_shopId));
            checkOrderResponse.setInvoiceId(Long.valueOf(invoiceId));
            if (md5 == null || md5.equals("") || !md5.equalsIgnoreCase(_md5)) {
                checkOrderResponse.setCode(RESPONSE_MD5_ERROR);
            } else if (orderNumber == null || orderNumber.equals("") || _tariff == null) {
                checkOrderResponse.setMessage("Не верный номер заказа.");
                checkOrderResponse.setCode(RESPONSE_EXACT_ERROR);
            } else if ((_tariff == null && (_tariff = TariffsDS.getTariff(orderNumber)) == null) ||
                                !InvoiceUtils.getCurrencyName(orderSumCurrencyPaycash).equals(_tariff.getCurrency()) ||
                                _tariff.getEndsum().doubleValue() != Double.valueOf(orderSumAmount).doubleValue()) {
                checkOrderResponse.setMessage("Не верная сумма заказа.");
                checkOrderResponse.setCode(RESPONSE_EXACT_ERROR);
            } else if ((_payment = PaymentDS.getPaymentByExtTransactionID(invoiceId)) != null && _payment.getStatus().equals("completed")) {
                checkOrderResponse.setTechMessage("Данный платёж уже авторизован (check order) и проведён (aviso).");
                checkOrderResponse.setMessage("Данный платёж уже был обработан ранее.");
                checkOrderResponse.setCode(RESPONSE_EXACT_ERROR);
            } else if (_customer == null && (_customer = CustomerDS.getCustomerByID(customerNumber)) == null) {
                checkOrderResponse.setTechMessage("Покупатель не найден.");
                checkOrderResponse.setMessage("Отказано в платеже.");
                checkOrderResponse.setCode(RESPONSE_EXACT_ERROR);
            } else {
                checkOrderResponse.setOrderSumAmount(orderSumAmount);
                checkOrderResponse.setCode(RESPONSE_SUCCESS);

                if (_payment == null) {
                    _payment = new Payment();
                }
                _payment.setStatus("authorized");
                _payment.setAuthorizedAt(new Date());
                _payment.setCustomerId(_customer.getId());
                _payment.setCustomerName(_customer.getUsername());
                _payment.setClientPaymentAccount(paymentPayerCode);
                _payment.setExtTransactionId(invoiceId);
                _payment.setIncome(Double.valueOf(shopSumAmount));
                _payment.setIncomeCurrency(InvoiceUtils.getCurrencyName(shopSumCurrencyPaycash));
                _payment.setPaymentGateway("yandex_kassa");
                _payment.setPaymentType(paymentType);
                _payment.setProcessingCenterId(shopSumBankPaycash);
                _payment.setSum(Double.valueOf(orderSumAmount));
                _payment.setSumCurrency(InvoiceUtils.getCurrencyName(orderSumCurrencyPaycash));
                _payment.setTariffId(_tariff.getId());

                PaymentDS.save(_payment);
            }

            String response = XmlUtils.getXmlString(CheckOrderResponse.class, checkOrderResponse);
            log.info("CheckOrderURL Response\n" + response);
            return ok(response).as("application/xml");
        } catch (Throwable t) {
            log.error("CheckOrderURL handled with error, invoiceId: " + invoiceId, t);
            try {
                CheckOrderResponse checkOrderResponse = new CheckOrderResponse();
                checkOrderResponse.setPerformedDatetime(XmlUtils.getXsDateTime(new Date()));
                checkOrderResponse.setShopId(Long.valueOf(ConfigController.getConfigParam("yandex_kassa_shop_id")));
                checkOrderResponse.setInvoiceId(Long.valueOf(invoiceId));
                checkOrderResponse.setCode(RESPONSE_ERROR);

                String response = XmlUtils.getXmlString(CheckOrderResponse.class, checkOrderResponse);
                log.info("CheckOrderURL Response\n" + response);
                return ok(response).as("application/xml");
            } catch (Throwable t2) {
                t2.printStackTrace();
                return internalServerError();
            }
        }
    }

    private String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    private String logRequest() {
        Map<String, String> postParams = Form.form().bindFromRequest().data();

        StringBuilder sb = new StringBuilder("");
        if (postParams != null) {
            int i = 0;
            for (String paramName : postParams.keySet()) {
                sb.append(paramName).append(": ").append(postParams.get(paramName));

                ++i;
                if (i != postParams.keySet().size()) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }
}
