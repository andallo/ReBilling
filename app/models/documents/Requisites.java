package models.documents;

import java.util.Date;

/**
 * Created by andrey on 25.10.15.
 */

public class Requisites {
    private String companyName;
    private String inn;
    private String kpp;
    private String address;
    private String webSite;
    private String telephone;
    private String bik;
    private String corrAccount;
    private String bankAccount;
    private String bankName;
    private String ceoFio;
    private String ceoJobTitle;
    private String buhFio;
    private String buhSignURL;
    private String ceoSignURL;
    private String stampURL;

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setBik(String bik) {
        this.bik = bik;
    }

    public void setCorrAccount(String corrAccount) {
        this.corrAccount = corrAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setCeoFio(String ceoFio) {
        this.ceoFio = ceoFio;
    }

    public void setCeoJobTitle(String ceoJobTitle) {
        this.ceoJobTitle = ceoJobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getInn() {
        return inn;
    }

    public String getKpp() {
        return kpp;
    }

    public String getAddress() {
        return address;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getBik() {
        return bik;
    }

    public String getCorrAccount() {
        return corrAccount;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCeoFio() {
        return ceoFio;
    }

    public String getCeoJobTitle() {
        return ceoJobTitle;
    }

    public String getBuhFio() {
        return buhFio;
    }

    public void setBuhSignURL(String buhSignURL) {
        this.buhSignURL = buhSignURL;
    }

    public void setCeoSignURL(String ceoSignURL) {
        this.ceoSignURL = ceoSignURL;
    }

    public void setStampURL(String stampURL) {
        this.stampURL = stampURL;
    }

    public void setBuhFio(String buhFio) {
        this.buhFio = buhFio;
    }

    public String getBuhSignURL() {
        return buhSignURL;
    }

    public String getCeoSignURL() {
        return ceoSignURL;
    }

    public String getStampURL() {
        return stampURL;
    }
}
