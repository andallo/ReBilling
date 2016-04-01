package controllers;

import models.FileStore;
import models.mongo.FileStoreDS;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


/**
 * Created by andrey on 22.10.15.
 */
@Security.Authenticated(Secured.class)
public class FileStoreController extends Controller {

    public static String getVersionURL(String fileName) {
        FileStore fileStore = FileStoreDS.getFile(fileName);

        if (fileStore == null) {
            return "";
        } else {
            return "/file-store/" + fileName + "/" + fileStore.getVersion();
        }
    }

    public Result getFile(String fileName, Integer version) {
        FileStore fileStore = FileStoreDS.getFile(fileName, version);
        if (fileStore != null) {
            byte[] bytes = fileStore.getBytes();
            return ok(bytes).as(fileStore.getContentType());
        } else {
            return badRequest();
        }
    }

    public Result getFileLastVersion(String fileName) {
        FileStore fileStore = FileStoreDS.getFile(fileName);
        if (fileStore != null) {
            byte[] bytes = fileStore.getBytes();
            return ok(bytes).as(fileStore.getContentType());
        } else {
            return badRequest();
        }
    }
}
