package com.gitrose.bukinghotel.adapter;

public class MyBookingListEntry {
    private String mBid;
    private String mBimg;
    private String mBookedTxt;
    private String mCheckinDate;
    private String mCheckoutDate;
    private String mHotelName;
    private String mProgressTxt;
    private String mRoomName;
    private String mStatus;

    public MyBookingListEntry(String id, String status, String bookedTxt, String progressTxt, String hotel_name, String room_name, String checkin_date, String checkout_date, String bimg) {
        this.mBid = id;
        this.mStatus = status;
        this.mBookedTxt = bookedTxt;
        this.mProgressTxt = progressTxt;
        this.mHotelName = hotel_name;
        this.mRoomName = room_name;
        this.mCheckinDate = checkin_date;
        this.mCheckoutDate = checkout_date;
        this.mBimg = bimg;
    }

    public String getId() {
        return this.mBid;
    }

    public String getStatus() {
        return this.mStatus;
    }

    public String getBookedTxt() {
        return this.mBookedTxt;
    }

    public String getProgressTxt() {
        return this.mProgressTxt;
    }

    public String getHotelName() {
        return this.mHotelName;
    }

    public String getRoomName() {
        return this.mRoomName;
    }

    public String getCheckinDate() {
        return this.mCheckinDate;
    }

    public String getCheckoutDate() {
        return this.mCheckoutDate;
    }

    public String getHotelImg() {
        return this.mBimg;
    }
}
