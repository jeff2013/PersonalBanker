package Entries;

/**
 * Created by jeff on 2014-12-25.
 */
public class Entry {

    private String name;
    private double amount;
    private DateC date;
    private int id;
    private String category;

    public Entry(String name, double amount, String type, DateC date){
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.category = type;

    }

    public Entry(String category, float amount){
        this.category = category;
        this.amount = (double)amount;
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


    public double getAmount() {
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

