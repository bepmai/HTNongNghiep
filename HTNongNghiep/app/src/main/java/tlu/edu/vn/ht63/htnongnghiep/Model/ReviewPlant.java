package tlu.edu.vn.ht63.htnongnghiep.Model;

public class ReviewPlant {
    private String nameuser;

    private String nameplant;
    private String date;
    private String statrating;
    private String review;

    public ReviewPlant(){}

    public ReviewPlant(String nameuser, String nameplant, String date, String statrating, String review) {
        this.nameuser = nameuser;
        this.nameplant = nameplant;
        this.date = date;
        this.statrating = statrating;
        this.review = review;
    }

    public String getNameuser() {
        return nameuser;
    }
    public String getNameplant() {
        return nameplant;
    }
    public String getDate() {
        return date;
    }

    public String getStatrating() {
        return statrating;
    }

    public String getReview() {
        return review;
    }
}
