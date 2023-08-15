package app;

public class top5age {

    private String sex;
    private String age;
    private int count;

public top5age(String age, String sex,int count){
    this.sex = sex;
    this.age = age;
    this.count = count ;
}
public String getsex(){
    return sex;
} 
public String getage(){
    return age;
} 
public int getcount(){
    return count;
} 

}
