package com.beci.product_service.dto;

public class ReviewResponse {
    private String id;
    private String userName;
    private String productName;
    private Integer rating;
    private String title;
    private String comment;
    private boolean verified;
    private boolean approved;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}
