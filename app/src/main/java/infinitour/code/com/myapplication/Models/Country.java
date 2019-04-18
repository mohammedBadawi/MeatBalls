package infinitour.code.com.myapplication.Models;

import java.util.ArrayList;

public class Country{
private String id;
private String name;
private String flag_url;



    public Country(String id, String name,String flag_url) {
        this.id = id;
        this.name = name;
        this.flag_url=flag_url;

    }


    public String getFlag_url() {
        return flag_url;
    }

    public void setFlag_url(String flag_url) {
        this.flag_url = flag_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
