package app;

/**
 * Class represeting a LGA from the Studio Project database
 * In the template, this only uses the code and name for 2016
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 */
public class LGA {
   
   private int code16;

   private String name16;

   private int state_code;

   /**
    * Create an LGA and set the fields
    */
   public LGA(int code16, String name16) {
      this.code16 = code16;
      this.name16 = name16;
   }

   public void LGAbyState(int state_code, String name){
      this.state_code = state_code;
      this.name16 = name;
   }

   public int getStateCode(){
      return state_code;
   }

   public int getCode16() {
      return code16;
   }

   public String getName16() {
      return name16;
   }

}
