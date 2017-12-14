package com.example.hamzazamir.food_wheels;

/**
 * Created by HamzaZamir on 12/9/2017.
 */

public class ItemInformation {
    private String itemname;
    private String itemdes;
    private String itemquan;
    private String itemprice;
    private String item_Id;

    public ItemInformation(String itemname, String itemdes, String itemquan, String itemprice, String item_Id) {
        this.itemname = itemname;
        this.itemdes = itemdes;
        this.itemquan = itemquan;
        this.itemprice = itemprice;
        this.item_Id = item_Id;

    }
    public ItemInformation(){

    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public void setItemquan(String itemquan) {
        this.itemquan = itemquan;
    }

    public void setItemdes(String itemdes) {
        this.itemdes = itemdes;
    }
    public void setItem_Id(String item_Id) {
        this.item_Id = item_Id;
    }

    public String getItemdes() {
        return itemdes;
    }

    public String getItemname() {
        return itemname;
    }

    public String getItemquan() {
        return itemquan;
    }

    public String getItemprice() {
        return itemprice;
    }

    public String getItem_Id() {
        return item_Id;
    }
}

