package com.smart.account.bean;

public class BudgetType {
    String type;
    String UserId;
    String BudegetTypeId;
    String note;

    public String getUserId() {
        return UserId;
    }
    public void setUserId(String userId) {
        UserId = userId;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBudegetTypeId() {
        return BudegetTypeId;
    }

    public void setBudegetTypeId(String budegetTypeId) {
        BudegetTypeId = budegetTypeId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
