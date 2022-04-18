package com.abbp.istockmobilesalenew;

public  abstract class Row { }

class Section extends Row {
    public final String text;

    public Section(String text) {
        this.text = text;
    }
}

class Item extends Row {
    public final String text;

    public Item(String text) {
        this.text = text;
    }
}
