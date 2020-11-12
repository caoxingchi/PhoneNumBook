package site.xingchi.pojo;

import java.io.Serializable;

public class Contacts implements Serializable {

    private int id;
    private String name;
    private String phone_num;
    private String info;

    public Contacts() {
        super();
    }

    public Contacts(String name, String phone_num, String info) {
        this.name = name;
        this.phone_num = phone_num;
        this.info=info;
    }

    public Contacts(int id, String name, String phone_num, String info) {
        this.id = id;
        this.name = name;
        this.phone_num = phone_num;
        this.info = info;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
