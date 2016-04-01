package controllers.api;

import controllers.utils.DateUtils;
import controllers.utils.InvoiceUtils;
import controllers.utils.PDFcrowd;
import models.FileStore;
import models.documents.Invoice;
import models.documents.Requisites;
import models.mongo.*;
import models.tariffs.Tariff;
import models.users.Customer;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



// todo: security
// авторизация через OAuth

public class InvoiceAPI extends Controller {

    // создать счёт для клиента (существующего или нового), счёт закрепится за клиентом и откроется в pdf-формате
    public Result invoiceCreate() throws Exception {
        Map<String, String> config = ConfigDS.getConfigParams();
        DynamicForm df = Form.form().bindFromRequest();
        String tariffIDs = df.get("tariff_ids");
        String username = df.get("username");
        Customer customer = CustomerDS.getCustomerByUsername(username);

        if (customer == null) {
            customer = new Customer();
        }
        if(df.get("customer_ooo_address") != null && !df.get("customer_ooo_address").equals("")) {
            customer.setOooAddress(df.get("customer_ooo_address"));
        }
        if(df.get("customer_ooo_inn") != null && !df.get("customer_ooo_inn").equals("")) {
            customer.setOooInn(df.get("customer_ooo_inn"));
        }
        if(df.get("customer_ooo_kpp") != null && !df.get("customer_ooo_kpp").equals("")) {
            customer.setOooKpp(df.get("customer_ooo_kpp"));
        }
        if(df.get("customer_ooo_name") != null && !df.get("customer_ooo_name").equals("")) {
            customer.setOooName(df.get("customer_ooo_name"));
        }
        if(df.get("customer_email") != null && !df.get("customer_email").equals("")) {
            customer.setEmail(df.get("customer_email"));
        }
        String customerId = CustomerDS.save(customer);

        List<Tariff> tariffs = new ArrayList<Tariff>();
        // если в пост запросе передать поле "balance_topup_sum", то будет созданы тариф и счёт для пополнения баланса
        if(df.get("balance_topup_sum") != null && !df.get("balance_topup_sum").equals("")) {
            Integer topUpSum = Integer.valueOf(df.get("balance_topup_sum"));
            Tariff tariff = new Tariff();
            tariff.setCreated(new Date());
            tariff.setCustomerID(customerId);
            tariff.setCustomerName(customer.getOooName());
            tariff.setDiscount(0);
            tariff.setEndsum(topUpSum);
            tariff.setSum(topUpSum);
            tariff.setCurrency("RUB");
            tariff.setName(ConfigDS.getConfigParams().get("ooo_balance_invoice"));
            tariff.setStatus("paid_waiting");
            tariffs.add(tariff);
        } else {
            if (tariffIDs == null || tariffIDs.equals("")) {
                return badRequest();
            }
            for (String tariffID : tariffIDs.split(",")) {
                tariffs.add(TariffsDS.getTariff(tariffID));
            }
        }

        Requisites sellerRequisites = InvoiceUtils.config2requisites();
        Requisites customerRequisites = InvoiceUtils.customer2requisites(customer);
        Integer invoiceNumber = ConfigDS.getNextInvoiceNumber();
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setCreatedAt(new Date());
        invoice.setInvoiceDate(new Date());
        invoice.setCustomerID(customerId);
        invoice.setCustomerName(customer.getOooName());
        invoice.setTariffs(tariffs);
        invoice.setCustomerRequisites(customerRequisites);
        invoice.setSellerRequisites(sellerRequisites);
        InvoiceDS.save(invoice);

        return redirect("/api/invoice-html/" + DateUtils.getCurrentYear() + "/" + invoiceNumber);
    }

    public Result invoicePDF(Integer year, Integer invoiceNumber, boolean download){
        Invoice invoice = InvoiceDS.getInvoice(invoiceNumber, year);
        if (invoice == null) {

            // todo: security
            //if (invoice.getCustomerID() != getRequest().getCustomer().getId();)

            return badRequest("Счёта с таким номером не существует");
        }

        try {
            String invoiceKey = "invoice-" + year + "-" + invoiceNumber;
            FileStore fileStore = FileStoreDS.getFile(invoiceKey);
            byte[] bytes = null;
            if (fileStore == null) {
                String invoiceHtml = views.html.document_templates.invoice_ru.render(invoice, true).toString();
                bytes = PDFcrowd.html2pdf(invoiceHtml);

                fileStore = new FileStore();
                fileStore.setContentType("application/pdf");
                fileStore.setFileName(invoiceKey);
                fileStore.setBytes(bytes);
                FileStoreDS.save(fileStore);
            }

            if (download) {
                File invoicePDF = null;
                FileOutputStream fos = null;
                try {
                    invoicePDF = File.createTempFile(invoiceKey, ".pdf");
                    fos = new FileOutputStream(invoicePDF);
                    fos.write(bytes);
                    return ok(invoicePDF);
                } finally {
                    if (invoicePDF != null) {
                        invoicePDF.delete();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }
            } else {
                return ok(bytes).as("application/pdf");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return badRequest("Что-то пошло не так, обратитесь в службу поддержки.");
        }
    }

    public Result invoicePrint() {
        DynamicForm df = Form.form().bindFromRequest();
        Integer year = Integer.valueOf(df.get("year"));
        Integer invoiceNumber = Integer.valueOf(df.get("invoiceNumber"));
        return invoicePDF(year, invoiceNumber, false);
    }

    public Result invoiceDownload() {
        DynamicForm df = Form.form().bindFromRequest();
        Integer year = Integer.valueOf(df.get("year"));
        Integer invoiceNumber = Integer.valueOf(df.get("invoiceNumber"));
        return invoicePDF(year, invoiceNumber, false);
    }

    public Result invoiceHtml() {
        DynamicForm df = Form.form().bindFromRequest();
        Integer year = Integer.valueOf(df.get("year"));
        Integer invoiceNumber = Integer.valueOf(df.get("invoiceNumber"));

        Invoice invoice = InvoiceDS.getInvoice(invoiceNumber, year);
        if (invoice == null) {
            return badRequest("Счёта с таким номером не существует");
        }
        return ok(views.html.document_templates.invoice_ru.render(invoice, false));
    }
}
