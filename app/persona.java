package app;

public class persona {
    private String name;
    private int age;
    private String desc;

    public persona(String name,int age,String desc){
        this.name = name;
        this.age = age;
        this.desc = desc ;
    }

    public String getname(){
        return name;
    } 

    public int getage(){
        return age;
    }
     
    public String getdesc(){
        return desc;
    } 

}
