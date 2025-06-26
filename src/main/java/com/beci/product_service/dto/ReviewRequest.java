package com.beci.product_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReviewRequest {
    @NotNull
    private String userId;

    @NotNull
    private String productId;

    @Min(1)
    @Max(5)
    private Integer rating;

    private String title;
    private String comment;

    private boolean verified;
    private boolean approved;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

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
