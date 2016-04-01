package models.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import models.tariffs.Tariff;
import models.users.Customer;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.logging.MorphiaLoggerFactory;
import org.mongodb.morphia.logging.slf4j.SLF4JLoggerImplFactory;

import java.net.UnknownHostException;
import java.util.Collections;

/**
 * Created by andrey on 15.10.15.
 */
public class MongoDS {

    private static Datastore datastore = null;

    public static synchronized Datastore ds() {
        if (datastore == null) {
            MorphiaLoggerFactory.reset();
            MorphiaLoggerFactory.registerLogger(SLF4JLoggerImplFactory.class);
            try {
                MongoCredential credential = MongoCredential.createMongoCRCredential("rebilling", "rebilling", "rebilling@123456".toCharArray());

                MongoClient mongoClient = new MongoClient(new ServerAddress("127.0.0.1", 27017), Collections.singletonList(credential));
                Morphia morphia = new Morphia();
                morphia.map(Tariff.class, Customer.class);
                morphia.getMapper().getOptions().setStoreEmpties(true);
                morphia.getMapper().getOptions().setStoreNulls(false);
                datastore = morphia.createDatastore(mongoClient, "rebilling");
                datastore.ensureIndexes(); //creates all defined with @Indexed
                datastore.ensureCaps();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
        return datastore;
    }


}
