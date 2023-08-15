package app;

public class State {
    
   private int code;

   private String name;

   public State(int code, String name) {
      this.code = code;
      this.name = name;
   }

   public int getStateCode() {
      return code;
   }

   public String getStateName() {
      return name;
   }
}
