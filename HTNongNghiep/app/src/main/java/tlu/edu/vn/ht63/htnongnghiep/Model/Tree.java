package tlu.edu.vn.ht63.htnongnghiep.Model;

public class Tree {
    private String reviewerName;
    private String reviewDate;
    private String reviewContent;
    private float rating;


    // Constructor, getters và setters
    public Tree(String reviewerName, String reviewDate, String reviewContent, Float rating) {
        this.reviewerName = reviewerName;
        this.reviewDate = reviewDate;
        this.reviewContent = reviewContent;
        this.rating = rating;

    }

    public void setReviewerName  (String reviewerName) { this.reviewerName = reviewerName; }
    public void  setReviewDate   (String reviewDate)  {this.reviewDate = reviewDate; }
    public void  setRating       (Float rating )  {this.rating = rating; }
    public void  setReviewContent  (String reviewContent )  {this.reviewContent = reviewContent; }

    public String getReviewerName() { return reviewerName; }
    public String getReviewDate() { return reviewDate; }

    public Float getRating() { return rating; }
    public String getReviewContent() { return reviewContent; }
}


