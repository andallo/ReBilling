package controllers.utils;

import models.xml.CheckOrderResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andrey on 08.11.15.
 */
public class XmlUtils {

    public static String getXmlString (Class c, Object o) throws Throwable {
        JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        StringWriter sw = new StringWriter();
        sw.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        jaxbMarshaller.marshal(o, sw);
        return sw.toString();
    }

    public static String getXsDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String dateString = simpleDateFormat.format(date);
        return new StringBuilder(dateString).insert(dateString.length()-2, ':').toString();
    }
}
