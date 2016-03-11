package com.gitrose.bukinghotel.adapter;

/**
 * Created by GITRose on 1/19/2016.
 */
import org.json.JSONObject;

public class EventEntry {
    private String id;
    private String title;
    private String evt_type;
    private String thumb_img;
    private String thumb_link_action;

    public EventEntry(String strID, String strTitle, String strEventType, String strThumbImg, String strLinkAction) {
        this.id = strID;
        this.title = strTitle;
        this.evt_type = strEventType;
        this.thumb_img = strThumbImg;
        this.thumb_link_action = strLinkAction;
    }

    public String getIsEvt() {
        return "Y";
    }

    public String getHid() {
        return this.id;
    }

    public String getTitle() {return this.title == null ? "" : this.title;}

    public String getEventType() {return this.evt_type == null ? "" : this.evt_type;}

    public String getThumbImg() {return this.thumb_img == null ? "" : this.thumb_img;}

    public String getThumbImgAction() {return  this.thumb_link_action == null ? "" : this.thumb_link_action;}
}
