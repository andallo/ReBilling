package controllers;

import models.Config;
import models.FileStore;
import models.mongo.ConfigDS;
import models.mongo.FileStoreDS;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

@Security.Authenticated(Secured.class)
public class ConfigController extends Controller {
    public Result save() {
        DynamicForm df = Form.form().bindFromRequest();
        Map<String,String> postParams = df.data();

        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart ceo_sign_image = body.getFile("ooo_ceo_sign_image");
        Http.MultipartFormData.FilePart buh_sign_image = body.getFile("ooo_buh_sign_image");
        Http.MultipartFormData.FilePart stamp_image = body.getFile("ooo_stamp_image");
        Http.MultipartFormData.FilePart logo_image = body.getFile("ooo_logo_image");

        if (ceo_sign_image != null) {
            try {
                String contentType = ceo_sign_image.getContentType();
                File file = ceo_sign_image.getFile();
                byte[] bytes = Files.readAllBytes(file.toPath());
                FileStore fileStore = new FileStore();
                fileStore.setBytes(bytes);
                fileStore.setContentType(contentType);
                fileStore.setFileName("ooo_ceo_sign_image");
                FileStoreDS.save(fileStore);
                postParams.put("ooo_ceo_sign_image", "/file-store/ooo_ceo_sign_image");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        if (buh_sign_image != null) {
            try {
                String contentType = buh_sign_image.getContentType();
                File file = buh_sign_image.getFile();
                byte[] bytes = Files.readAllBytes(file.toPath());
                FileStore fileStore = new FileStore();
                fileStore.setBytes(bytes);
                fileStore.setContentType(contentType);
                fileStore.setFileName("ooo_buh_sign_image");
                FileStoreDS.save(fileStore);
                postParams.put("ooo_buh_sign_image", "/file-store/ooo_buh_sign_image");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        if (stamp_image != null) {
            try {
                String contentType = stamp_image.getContentType();
                File file = stamp_image.getFile();
                byte[] bytes = Files.readAllBytes(file.toPath());
                FileStore fileStore = new FileStore();
                fileStore.setBytes(bytes);
                fileStore.setContentType(contentType);
                fileStore.setFileName("ooo_stamp_image");
                FileStoreDS.save(fileStore);
                postParams.put("ooo_stamp_image", "/file-store/ooo_stamp_image");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        if (logo_image != null) {
            try {
                String contentType = logo_image.getContentType();
                File file = logo_image.getFile();
                byte[] bytes = Files.readAllBytes(file.toPath());
                FileStore fileStore = new FileStore();
                fileStore.setBytes(bytes);
                fileStore.setContentType(contentType);
                fileStore.setFileName("ooo_logo_image");
                FileStoreDS.save(fileStore);
                postParams.put("ooo_logo_image", "/file-store/ooo_logo_image");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        String redirectURL = postParams.remove("redirectURL");
        ConfigDS.saveConfigParams(postParams);
        return redirect(redirectURL);
    }

    public static String getConfigParam(String paramName) {
        Config config = ConfigDS.getConfig(paramName);
        if (config != null) {
            return config.getParameterValue();
        } else {
            return null;
        }
    }
}
