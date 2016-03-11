package com.gitrose.bukinghotel.adapter;

/**
 * Created by GITRose on 1/19/2016.
 */
public class MainListEntry {
    private String category;
    private String mCode;
    private String distance;
    private String hotel_id;
    private String mIsEvent;
    private int items_quantity;
    private String landscape;
    private String name;
    private String normal_price;
    private String mNormalPriceAvg;
    private String product_id;
    private String sale_price;
    private String sale_rate;
    private int sold_out;
    private String street1;
    private String street2;
    private String mThumb1;
    private String mThumb2;
    private String fixed;

    public MainListEntry(String category, int items_quantity, int sold_out, String landscape, String name, String normal_price, String sale_price, String normal_price_avg, String sale_rate, String street1, String street2, String is_event, String distance, String hid, String pid, String code, String thumb1, String thumb2, String fixed) {
        this.category = category;
        this.items_quantity = items_quantity;
        this.sold_out = sold_out;
        this.landscape = landscape;
        this.name = name;
        this.normal_price = normal_price;
        this.sale_price = sale_price;
        this.mNormalPriceAvg = normal_price_avg;
        this.sale_rate = sale_rate;
        this.street1 = street1;
        this.street2 = street2;
        this.mIsEvent = is_event;
        this.distance = distance;
        this.hotel_id = hid;
        this.product_id = pid;
        this.mCode = code;
        this.mThumb1 = thumb1;
        this.mThumb2 = thumb2;
        this.fixed = fixed;
    }

    public String getCategory() {
        return this.category == null ? "" : this.category;
    }

    public int getItemsQuantity() {
        return this.items_quantity;
    }

    public int getSoldout() {
        return this.sold_out;
    }

    public String getLandscape() {
        return this.landscape == null ? "" : this.landscape;
    }

    public String getName() {
        return this.name == null ? "" : this.name;
    }

    public String getNormalPrice() {
        return this.normal_price == null ? "" : this.normal_price;
    }

    public String getSalePrice() {
        return this.sale_price == null ? "" : this.sale_price;
    }

    public String getNormalPriceAvg() {
        return this.mNormalPriceAvg == null ? "" : this.mNormalPriceAvg;
    }

    public String getSaleRate() {
        return this.sale_rate == null ? "" : this.sale_rate;
    }

    public String getStreet1() {
        return this.street1 == null ? "" : this.street1;
    }

    public String getStreet2() {
        return this.street2 == null ? "" : this.street2;
    }

    public String getIsEvent() {
        return this.mIsEvent == null ? "" : this.mIsEvent;
    }

    public String getDistance() {
        return this.distance == null ? "" : this.distance;
    }

    public String getHid() {
        return this.hotel_id == null ? "" : this.hotel_id;
    }

    public String getPid() {
        return this.product_id == null ? "" : this.product_id;
    }

    public String getCode() {
        return this.mCode == null ? "" : this.mCode;
    }

    public String getThumb1() {
        return this.mThumb1 == null ? "" : this.mThumb1;
    }

    public String getThumb2() {
        return this.mThumb2 == null ? "" : this.mThumb2;
    }

    public String getFixed() {return this.fixed == null ? "" : this.fixed; }
}
