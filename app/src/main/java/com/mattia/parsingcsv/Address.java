package com.mattia.parsingcsv;

public class Address {
    private String type;
    private String name;
    private String number;
    private String intern;

    public Address(String t, String nam, String num, String in) {
        type = t;
        name = nam;
        number = num;
        if(in.length() > 0)
            intern = "interno " + in;
        else
            intern = "";
    }
    public Address(){
        type = null;
        name = null;
        number = null;
        intern = null;
    }

    public String toString(){
        return type+" "+name+" "+number+" "+intern;
    }

    public String csvFormat(){
        return type+";"+name+";"+number+";"+intern;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getIntern() {
        return intern;
    }
    public void setIntern(String intern) {
        this.intern = intern;
    }

    public String getAddressNoSpace(){
        return (type+name+number).replace(" ", "");
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Address){
            Address temp = (Address)other;
            return temp.getAddressNoSpace().equals(this.getAddressNoSpace());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return (type+name+number).replace(" ", "").hashCode();
    }
}
