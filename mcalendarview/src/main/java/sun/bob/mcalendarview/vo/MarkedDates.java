package sun.bob.mcalendarview.vo;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by bob.sun on 15/8/28.
 */
public class MarkedDates extends Observable {
    private static MarkedDates staticInstance;
    private ArrayList<DateData> data;

    private MarkedDates(){
        super();
        data = new ArrayList<>();
    }

    public static MarkedDates getInstance(){
        if (staticInstance == null)
            staticInstance = new MarkedDates();
        return staticInstance;
    }

    public boolean check(DateData date){
        return data.contains(date);
    }

    public boolean remove(DateData date){
        return data.remove(date);
    }

    public MarkedDates add(DateData dateData){
        data.add(dateData);
        this.setChanged();
        return this;
    }

    public ArrayList<DateData> getAll(){
        return data;
    }

    public MarkedDates removeAdd(){
        data.clear();
        return this;
    }
}
