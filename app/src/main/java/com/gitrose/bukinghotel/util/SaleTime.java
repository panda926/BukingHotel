package com.gitrose.bukinghotel.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SaleTime implements Parcelable {
    public static final Creator CREATOR;
    public static final long SECONDS_IN_A_DAY = 86400;
    private static final SimpleDateFormat simpleDateFormat;
    private Date openTime;
    private Date closeTime;
    private Date currentTime;
    private Date dailyTime;
    private int offsetDailyDay;

    static final class C13871 implements Creator {
        C13871() {
        }

        public SaleTime createFromParcel(Parcel parcel) {
            return new SaleTime(parcel);
        }

        public SaleTime[] newArray(int i) {
            return new SaleTime[i];
        }
    }

    static {
        CREATOR = new C13871();
        simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.KOREA);
    }

    public SaleTime() {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public SaleTime(Parcel parcel) {
        m2500a(parcel);
    }

    public static String attachCurrentDate(String str, String str2, String str3, String str4) {
        return "-" + str2 + "-" + str3 + " " + str4;
    }

    public static Date stringToDate(String str) throws ParseException {
        return simpleDateFormat.parse(str);
    }

    public static String dateToString(Date date) {
        return simpleDateFormat.format(date);
    }

    public String getDailyDayOftheWeek() {
        return getTimezonedDateFormat("EEE").format(getDayOfDaysHotelDate());
    }

    public String getDailyDay() {
        return getTimezonedDateFormat("d").format(getDayOfDaysHotelDate());
    }

    public String getDailyDateFormat(String str) {
        return getTimezonedDateFormat(str).format(getDayOfDaysHotelDate());
    }

    public Date getDayOfDaysHotelDate() {
        return new Date(this.dailyTime.getTime() + ((SECONDS_IN_A_DAY * ((long) this.offsetDailyDay)) * 1000));
    }

    public String getDayOfDaysHotelDateFormat(String str) {
        return getTimezonedDateFormat(str).format(getDayOfDaysHotelDate());
    }

    public SaleTime getClone(int i) {
        SaleTime saleTime = new SaleTime();
        saleTime.offsetDailyDay = i;
        saleTime.openTime = new Date(this.openTime.getTime());
        saleTime.closeTime = new Date(this.closeTime.getTime());
        saleTime.currentTime = new Date(this.currentTime.getTime());
        saleTime.dailyTime = new Date(this.dailyTime.getTime());
        return saleTime;
    }

    public SimpleDateFormat getTimezonedDateFormat(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, Locale.KOREA);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat;
    }

    public long getCurrentTime() {
        return this.currentTime.getTime();
    }

    public void setCurrentTime(long j) {
        this.currentTime = new Date(j);
    }

    public long getOpenTime() {
        return this.openTime.getTime();
    }

    public void setOpenTime(long j) {
        this.openTime = new Date(j);
    }

    public long getCloseTime() {
        return this.closeTime.getTime();
    }

    public void setCloseTime(long j) {
        this.closeTime = new Date(j);
    }

    public long getDailyTime() {
        return this.dailyTime.getTime();
    }

    public void setDailyTime(long j) {
        this.dailyTime = new Date(j);
    }

    public int getOffsetDailyDay() {
        return this.offsetDailyDay;
    }

    public void setOffsetDailyDay(int i) {
        this.offsetDailyDay = i;
    }

    public boolean isSaleTime() {
        if (this.currentTime == null || this.openTime == null || this.closeTime == null || this.currentTime.compareTo(this.openTime) < 0 || this.currentTime.compareTo(this.closeTime) >= 0) {
            return false;
        }
        return true;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(this.openTime);
        parcel.writeValue(this.closeTime);
        parcel.writeValue(this.currentTime);
        parcel.writeValue(this.dailyTime);
        parcel.writeInt(this.offsetDailyDay);
    }

    private void m2500a(Parcel parcel) {
        this.openTime = (Date) parcel.readValue(Date.class.getClassLoader());
        this.closeTime = (Date) parcel.readValue(Date.class.getClassLoader());
        this.currentTime = (Date) parcel.readValue(Date.class.getClassLoader());
        this.dailyTime = (Date) parcel.readValue(Date.class.getClassLoader());
        this.offsetDailyDay = parcel.readInt();
    }
}
