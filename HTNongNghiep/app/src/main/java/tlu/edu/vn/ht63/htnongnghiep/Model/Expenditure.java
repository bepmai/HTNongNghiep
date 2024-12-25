package tlu.edu.vn.ht63.htnongnghiep.Model;

import java.io.Serializable;
import java.util.Date;

public class Expenditure implements Serializable {
    public static final int TYPE_BUY = 0;
    public static final int TYPE_PRODUCT = 1;

    private int id;
    private int viewType; // Phân loại kiểu View
    private String productImage;
    private String nameSeller;
    private String adress;
    private Date date;
    private String status;
    private String nameProduct;
    private int total;
    private int idProduct;
    private Float productCost;
    private Float totalPayment;

    public Expenditure(int viewType, String productImage, String nameSeller, String adress, Date date, String status, String nameProduct, int total, int idProduct, Float productCost, Float totalPayment) {
        this.viewType = viewType;
        this.productImage = productImage;
        this.nameSeller = nameSeller;
        this.adress = adress;
        this.date = date;
        this.status = status;
        this.nameProduct = nameProduct;
        this.total = total;
        this.idProduct = idProduct;
        this.productCost = productCost;
        this.totalPayment = totalPayment;
    }

    public Expenditure() {
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSeller() {
        return nameSeller;
    }

    public void setNameSeller(String nameSeller) {
        this.nameSeller = nameSeller;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
