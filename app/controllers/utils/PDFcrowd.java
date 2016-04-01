package controllers.utils;

import com.pdfcrowd.Client;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PDFcrowd {
    private static Client client = new Client("ovido", "20b03a01d73b9ea8306f6348f370b27a");

    public static byte[] html2pdf(String html) throws IOException {
        // convert an HTML string and store the PDF into a byte array
        ByteArrayOutputStream memStream  = new ByteArrayOutputStream();
        client.convertHtml(html, memStream);
        byte[] bytes = memStream.toByteArray();
        memStream.close();
        return bytes;
    }

    public static byte[] web2pdf(String website) throws IOException {
        ByteArrayOutputStream memStream  = new ByteArrayOutputStream();
        client.convertURI(website, memStream);
        byte[] bytes = memStream.toByteArray();
        memStream.close();
        return bytes;
    }

    public static void main(String[] args) throws Exception {
        PDFcrowd.web2pdf("http://carbay.ru");
        // retrieve the number of tokens in your account
        Integer ntokens = client.numTokens();
        System.out.println(ntokens);
    }
}
