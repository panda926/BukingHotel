package com.gitrose.bukinghotel.adapter;

public class CouponEntry {
    private String mCode;
    private String mEnd;
    private String mExpiration;
    private String mId;
    private String mName;
    private int mPrice;
    private String mStart;
    private String mUseyn;

    public CouponEntry(String start, String end, String expiration, String id, String code, String name, String useyn, int price) {
        this.mStart = start;
        this.mEnd = end;
        this.mExpiration = expiration;
        this.mId = id;
        this.mCode = code;
        this.mName = name;
        this.mUseyn = useyn;
        this.mPrice = price;
    }

    public String getStart() {
        return this.mStart;
    }

    public String getEnd() {
        return this.mEnd;
    }

    public String getExpiration() {
        return this.mExpiration;
    }

    public String getId() {
        return this.mId;
    }

    public String getCode() {
        return this.mCode;
    }

    public String getName() {
        return this.mName;
    }

    public String getUseyn() {
        return this.mUseyn;
    }

    public int getPrice() {
        return this.mPrice;
    }
}
