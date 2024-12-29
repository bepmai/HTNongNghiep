package tlu.edu.vn.ht63.htnongnghiep.Model;

import java.io.Serializable;
import java.util.Date;

public class Expenditure implements Serializable {
    public static final int TYPE_BUY = 0;
    public static final int TYPE_PRODUCT = 1;

    private String id;
    private int viewType; // Phân loại kiểu View
    private String idSeller;
    private String idProduct;
    private String productImage;
    private String nameSeller;
    private String adress;
    private Date date;
    private int status;
    private String nameProduct;
    private int total;
    private Float productCost;
    private Float totalPayment;

    public Expenditure(){}

    public Expenditure(String id, int viewType, String idSeller, String idProduct, String productImage, String nameSeller, String adress, Date date, int status, String nameProduct, int total, Float productCost, Float totalPayment) {
        this.id = id;
        this.viewType = viewType;
        this.idSeller = idSeller;
        this.idProduct = idProduct;
        this.productImage = productImage;
        this.nameSeller = nameSeller;
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

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getIdSeller() {
        return idSeller;
    }

    public void setIdSeller(String idSeller) {
        this.idSeller = idSeller;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameSeller() {
        return nameSeller;
    }

    public void setNameSeller(String nameSeller) {
        this.nameSeller = nameSeller;
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

    public Float getProductCost() {
        return productCost;
    }

    public void setProductCost(Float productCost) {
        this.productCost = productCost;
    }

    public Float getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Float totalPayment) {
        this.totalPayment = totalPayment;
    }
}
