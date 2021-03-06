package Entries;

/**
 * Created by jeff on 2014-12-25.
 */
public class Entry {

    private String name;
    private int amount;
    private DateC date;
    private int id;
    private String category;

    public Entry(String name, int amount, String type, DateC date){
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.category = type;

    }

    public Entry(String category, int amount){
        this.category = category;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public int getAmount() {
        return amount;
    }


    public DateC getDate() {
        return date;
    }

    public String getCategory(){
        return this.category;
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format(name + " " + amount+ " " + date);
    }


}

