package tlu.edu.vn.ht63.htnongnghiep.Model;

import java.io.Serializable;

public class InforTreeFeature implements Serializable {
    private String name;
    private String content;
    public InforTreeFeature(String name,String content){
        this.name = name;
        this.content = content;
    }
    public String getName(){
        return this.name;
    }
    public String getContent(){
        return this.content;
    }
}
