package tlu.edu.vn.ht63.htnongnghiep.Model;

import java.io.Serializable;
import java.util.Date;

public class Revenue implements Serializable {
    private String id;
    private String idBuyer;
    private String idProduct;
    private String productImage;
    private String nameBuyer;
    private String adress;
    private Date date;
    private int status;
    private String nameProduct;
    private int total;
    private Float productCost;
    private Float totalPayment;

    public Revenue() {
    }

    public Revenue(String id, String idBuyer, String idProduct, String productImage, String nameBuyer, String adress, Date date, int status, String nameProduct, int total, Float productCost, Float totalPayment) {
        this.id = id;
        this.idBuyer = idBuyer;
        this.idProduct = idProduct;
        this.productImage = productImage;
        this.nameBuyer = nameBuyer;
        this.adress = adress;
        this.date = date;
        this.status = status;
        this.nameProduct = nameProduct;
        this.total = total;
        this.productCost = productCost;
        this.totalPayment = totalPayment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdBuyer() {
        return idBuyer;
    }

    public void setIdBuyer(String idBuyer) {
        this.idBuyer = idBuyer;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getNameBuyer() {
        return nameBuyer;
    }

    public void setNameBuyer(String nameBuyer) {
        this.nameBuyer = nameBuyer;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Float getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Float totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Float getProductCost() {
        return productCost;
    }

    public void setProductCost(Float productCost) {
        this.productCost = productCost;
    }
}
