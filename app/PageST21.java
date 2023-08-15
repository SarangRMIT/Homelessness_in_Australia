package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageST21 implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
       
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
        "<title>Ranking and List of LGAs</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='pageST21.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='page6.html'>Change in <br> Homelessness</a>
                <a href='page5.html'>Comparing <br> Homelessness</a>
                <a href='page4.html'>Number of <br> Homeless People</a>
                <a href='page3.html'>Ranking & List <br> of LGAs</a>
                <a href='mission.html'>Our Mission</a>
                <a href='/'>Home</a>
                <a class='split' href='/'><img src='website logo.png' alt='Website logo' height='40'></a>
                <a class='split' href='/'>Australian <br> Homelessness</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
                <br>
                <h1>Ranking & List of Homeless / At-Risk <br> People of LGAs</h1>
                <br>
            </div>
        """;

        // Add for page Content
        html = html + "<div class='content'>";

        //Part 1 - Ranking
        html = html + """
                <h2 id = 'result'>Rank LGAs of a State With the<br> Most Homeless & At-Risk <br> People By Category</h2>
            """;
        
        // Look up some information from JDBC and therefore, using the JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        //Web form
        html = html + "<br>";

        html = html + "<form action='#result' method='post'>";
        
        //STATE DROPDOWN
        html = html + "   <div class='form-group1'>";
        //html = html + "      <label for='statetype_drop'><b>CHOOSE A STATE OF THE LGA YOU WISH TO SEE:</b></label>";
        html = html + "      <select id='statetype_drop' name='statetype_drop'>";
        html = html + "         <option>Choose a State*</option>";

        // Gettting the Name of the States from the Database
        ArrayList<State> states = jdbc.getStates();
        for (State stateName : states){
            html = html + "     <option>" + stateName.getStateName() + "</option>";
        }

        //Continuing the HTML
        html = html + "      </select>";
        html = html + "   </div>";

        String[] sex_options = {"Male","Female"};
        String[] age_options = {"0-9 years","10-19 years","20-29 years","30-39 years", "40-49 years","50-59 years","60+ years"};
        String[] status = {"Homeless","At-risk"};
        
        //DROPDOWN FOR SEX CATEGORIES
        html = html + "<br>";
        html = html + "   <div class='form-group2'>";
        html = html + "      <select id='choosesex_drop' name='choosesex_drop'>";
        html = html + "         <option>Choose a Sex*</option>";

        for (String i : sex_options){
            html = html + "     <option>" + i + "</option>";
        }

        html = html + "      </select>";
        html = html + "   </div>";

        //DROPDOWN FOR AGE CATEGORIES
        html = html + "   <br>";
        html = html + "   <div class='form-group4'>";
        html = html + "      <select id='chooseage_drop' name='chooseage_drop'>";
        html = html + "         <option>Choose an Age Group*</option>";

        for (String i : age_options){
            html = html + "     <option>" + i + "</option>";
        }

        html = html + "      </select>";
        html = html + "   </div>";

        //DROPDOWN FOR STATUS CATEGORIES
        html = html + "   <br>";
        html = html + "   <div class='form-group5'>";
        html = html + "      <select id='choosestatus_drop' name='choosestatus_drop'>";
        html = html + "         <option>Choose a Status*</option>";

        for (String i : status){
            html = html + "     <option>" + i + "</option>";
        }

        html = html + "      </select>";
        html = html + "   </div>";

        //Search Button
        html = html + "<div class = 'button1'>";
        html = html + "<br>";
        html = html + "   <button type='submit' class='button1' onclick = 'ButtonClick()'>Search</button>";
        html = html + "</div>";

        //Closing the Form
        html = html + "</form>";

        //Getting the information from the dropdowns
        String state100 = context.formParam("statetype_drop");
        String stat = context.formParam("choosestatus_drop");
        String sex = context.formParam("choosesex_drop");
        String age = context.formParam("chooseage_drop");

        String sex_final = "";
        String status_final = "";
        String age_group_final = "";
        int state = 0;
        
        //Converting the dropdown options so that the database can understand
        if(sex!=null){
            switch(sex){
                case "Male":
                    sex_final = "m";
                    break;
                case "Female":
                    sex_final = "f";
                    break;
            }
        }

        if(stat!=null){
            switch(stat){
                case "Homeless":
                    status_final = "homeless";
                    break;
                case "At-risk":
                    status_final = "at_risk";
            }
        }

        if(age!=null){
            switch(age){
                case "0-9 years":
                    age_group_final = "_0_9";
                    break;
                case "10-19 years":
                    age_group_final = "_10_19";
                    break;
                case "20-29 years":
                    age_group_final = "_20_29";
                    break;
                case "30-39 years":
                    age_group_final = "_30_39";
                    break;
                case "40-49 years":
                    age_group_final = "_40_49";
                    break;
                case "50-59 years":
                    age_group_final = "_50_59";
                    break;
                case "60+ years":
                    age_group_final = "_60_plus";
                    break;
            }   
        }

        if(state100!=null){
            switch(state100){
                case "NSW":
                    state = 1;
                    break;
                case "Victoria":
                    state =2;
                    break;
                case "QLD":
                    state =3;
                    break;
                case "South Australia":
                    state = 4;
                    break;
                case "Western Australia":
                    state = 5;
                    break;
                case "Tasmania":
                    state = 6;
                    break;
                case "Northern Territory":
                    state = 7;
                    break;
                case "ACT":
                    state = 8;
                    break;
                case "Other Australian Territories":
                    state = 9;
                    break;
            }
        }

        html = html + "<div class = 'result'>";

        if(state100 != null && stat != null && sex != null && age != null){
            //Adding a error message
            if (state100.equals("Choose a State*") || stat.equals("Choose a Status*") || sex.equals("Choose a Sex*") || age.equals("Choose an Age Group*") ){
                html = html + "<p><b> To view the ranking of the most homeless / at-risk people, <br> by selected categories, please choose a valid value for all the dropdowns.</b></p>"; 
            }
            else{
                html = html + "<p>The top 5 LGAs in <b>" + state100 + "</b> with the most <b>"+ stat.toLowerCase() +" "+ sex.toLowerCase() +"s</b> between <b>" + age + "</b> are:<p></b>";

                ArrayList<toplga> top = jdbc.top(sex_final,  age_group_final, state, status_final);

                html = html + "<ol>";
                for (toplga t : top){
                    html = html + "<li>" + t.getLGAName()+ " - " + t.getCount() + "</li>" ;
                } 
                html = html + "</ol>";
            }
        }

        html = html + "</div>"; 

        html = html + "   <hr class='hr1' color='black'>";

        //__________________________________________________________________________________________________________________________________
        //PART 2
        html = html + "<h2 id = 'result1'>List of the Total Number of Homeless <br> or At-Risk People of Different LGAs</h2>";
        html = html + "<br>";
        String[] status_options = {"Homeless","At-risk"};

        html = html + "<form action='#result1' method='post'>";
        
        html = html + "   <div class='form-group2'>";
        html = html + "      <label for='status'></label>";
        html = html + "      <select id='status' name='status'>";
        html = html + "         <option>Choose a Status*</option>";

        for (String i : status_options){
            html = html + "     <option>" + i + "</option>";
        }

        html = html + "   </div>";
        html = html + "      </select>";
        
        html = html + "   <div class='form-group2'>";
        html = html + "<br>";
        html = html + "      <label for='lgabystatetype_drop'><b>CHOOSE A LOCAL GOVERNMENT AREA (optional): </b></label>";
        html = html + "      <select id='lgabystatetype_drop' name='lgabystatetype_drop'>";
        html = html + "         <option>Choose a LGA</option>";

        // Gettting the Name of the LGAs according to the State Chosen from the Database
        ArrayList<LGA> getLGAs = jdbc.getLGAs(2018);
        for (LGA lgaName : getLGAs){
            html = html + "     <option>" + lgaName.getName16() + "</option>";
        }

        html = html + "      </select>";
        html = html + "   </div>";
        
        html = html + "<div class = 'button2'>";
        html = html + "<br>";
        html = html + "   <button type='submit' class='button1'>Search</button>";
        html = html + "</div>";
        html = html +"</form>";

        html = html + "<br>";

        String choosestatus_drop = context.formParam("status");

       String lga = context.formParam("lgabystatetype_drop");
       html = html + "<div class = 'result1'>";

        if (choosestatus_drop != null){

            if (!choosestatus_drop.equals("Choose a Status*")) {

                ArrayList<String> names = new ArrayList<String>();
                ArrayList<Integer> year = new ArrayList<Integer>();
                ArrayList<Integer> countPeople = new ArrayList<Integer>();

                ArrayList<State> stateName = jdbc.getStatebyLGA(lga);
                String nameState = "";
        
                if( !lga.equals("Choose a LGA")  && choosestatus_drop!= null && choosestatus_drop.equals("Homeless")){

                    for (State s : stateName){
                        nameState = s.getStateName();
                    }

                    html = html + "<p><b>" + lga + ", " + nameState + "</b></p>";
                    html = html + "<p>Status Chosen: <b>" + choosestatus_drop + "</b></p>";

                    ArrayList<table1> table1 = jdbc.gettable1("homeless", lga);
        
                    html = html + """   
                        <table>
                            <tr>
                    """;
                            
                    html = html +    "<th rowspan = '2'> LGA </th>";
                    html = html +    "<th rowspan = '2'> Year </th>";   

                    html = html + "<th colspan = '2'> 0 - 9 years   </th>";
                    html = html + "<th colspan = '2'> 10 - 19 years </th>";
                    html = html + "<th colspan = '2'> 20 - 29 years </th>";
                    html = html + "<th colspan = '2'> 30 - 39 years </th>";
                    html = html + "<th colspan = '2'> 40 - 49 years </th>";
                    html = html + "<th colspan = '2'> 50 - 59 years </th>";
                    html = html + "<th colspan = '2'> 60+ years     </th>";
                    html = html + "</tr>";

                    html = html + "<tr>";
                    for (int i = 0; i < 7; i++){
                        html = html + "<th> F </th>";
                        html = html + "<th> M </th>";
                    }
                    html = html + "</tr>";

                    for (table1 f3 : table1){
                        names.add(f3.getname());
                        year.add(f3.getyear());
                        countPeople.add(f3.getcount());
                    }

                    for (int j = 0; j < names.size(); j = j + 28){
                        html = html + "<tr>";
                        html = html +     "<td>" + names.get(j) + "</td>";
                        html = html +     "<td>" + year.get(j) + "</td>";
                        int l = j;
                        for (int k = 0; k < 14; k++){
                            html = html + "<td>" + countPeople.get(l) + "</td>";
                            l++;
                        }
                        html = html + "</tr>";
                    }

                    html = html + """
                                
                            </table>
                    """;
                }
        

                else if( !lga.equals("Choose a LGA") && choosestatus_drop!= null && choosestatus_drop.equals("At-risk")){

                    for (State s : stateName){
                        nameState = s.getStateName();
                    }

                    html = html + "<p><b>" + lga + ", " + nameState + "</b></p>";
                    html = html + "<p>Status Chosen: <b>" + choosestatus_drop + "</b></p>";

                    ArrayList<table1> table1 = jdbc.gettable1("at_risk",lga);
                
                    html = html + """
                            <table>
                                <tr>
                                """;
                                
                                html = html +    "<th rowspan = '2'> LGA </th>";
                                html = html +    "<th rowspan = '2'> Year </th>";   
            
                                html = html + "<th colspan = '2'> 0 - 9 years   </th>";
                                html = html + "<th colspan = '2'> 10 - 19 years </th>";
                                html = html + "<th colspan = '2'> 20 - 29 years </th>";
                                html = html + "<th colspan = '2'> 30 - 39 years </th>";
                                html = html + "<th colspan = '2'> 40 - 49 years </th>";
                                html = html + "<th colspan = '2'> 50 - 59 years </th>";
                                html = html + "<th colspan = '2'> 60+ years     </th>";
                                html = html + "</tr>";
            
                                html = html + "<tr>";
                                for (int i = 0; i < 7; i++){
                                    html = html + "<th> F </th>";
                                    html = html + "<th> M </th>";
                                }
                                html = html + "</tr>";


                    for (table1 f3 : table1){
                        names.add(f3.getname());
                        year.add(f3.getyear());
                        countPeople.add(f3.getcount());
                    }

                    for (int j = 0; j < names.size(); j = j + 28){
                        html = html + "<tr>";
                        html = html +    "<td>" + names.get(j) + "</td>";
                        html = html +    "<td>" + year.get(j) + "</td>";
                        int l = j;
                        for (int k = 0; k < 14; k++){
                            html = html +    "<td>" + countPeople.get(l) + "</td>";
                            l++;
                        }
                        html = html + "</tr>";
                    }

                    html = html + """  
                            </table>
                    """;
                }
            
                else if(lga.equals("Choose a LGA") && choosestatus_drop!= null && choosestatus_drop.equals("Homeless")){

                    html = html + "<p>Status Chosen: <b>" + choosestatus_drop + "</b></p>";

                    ArrayList<table> table = jdbc.gettable("homeless");
            
                    html = html + """         
                            <table>
                                <tr>
                    """;
                                    
                    html = html +    "<th rowspan = '2'> LGA </th>";
                    html = html +    "<th rowspan = '2'> Year </th>";   

                    html = html + "<th colspan = '2'> 0 - 9 years   </th>";
                    html = html + "<th colspan = '2'> 10 - 19 years </th>";
                    html = html + "<th colspan = '2'> 20 - 29 years </th>";
                    html = html + "<th colspan = '2'> 30 - 39 years </th>";
                    html = html + "<th colspan = '2'> 40 - 49 years </th>";
                    html = html + "<th colspan = '2'> 50 - 59 years </th>";
                    html = html + "<th colspan = '2'> 60+ years     </th>";
                    html = html + "</tr>";

                    html = html + "<tr>";
                    for (int i = 0; i < 7; i++){
                        html = html + "<th> F </th>";
                        html = html + "<th> M </th>";
                    }
                    html = html + "</tr>";

                    for (table f3 : table){
                        names.add(f3.getname());
                        year.add(f3.getyear());
                        countPeople.add(f3.getcount());
                    }

                    for (int j = 0; j < names.size(); j = j + 28){
                        html = html + "<tr>";
                        html = html +    "<td>" + names.get(j) + "</td>";
                        html = html +    "<td>" + year.get(j) + "</td>";
                        int l = j;
                        for (int k = 0; k < 14; k++){
                            html = html +    "<td>" + countPeople.get(l) + "</td>";
                            l++;
                        }
                        html = html + "</tr>";
                    }

                    html = html + """                
                            </table>
                    """;
                }

                else if(lga.equals("Choose a LGA") && choosestatus_drop!= null && choosestatus_drop.equals("At-risk")){
                    
                    html = html + "<p>Status Chosen: <b>" + choosestatus_drop + "</b></p>";

                    ArrayList<table> table = jdbc.gettable("at_risk");
                    
                    html = html + """
                            <table>
                                <tr>
                    """;
                                    
                    html = html +    "<th rowspan = '2'> LGA </th>";
                    html = html +    "<th rowspan = '2'> Year </th>";   

                    html = html + "<th colspan = '2'> 0 - 9 years   </th>";
                    html = html + "<th colspan = '2'> 10 - 19 years </th>";
                    html = html + "<th colspan = '2'> 20 - 29 years </th>";
                    html = html + "<th colspan = '2'> 30 - 39 years </th>";
                    html = html + "<th colspan = '2'> 40 - 49 years </th>";
                    html = html + "<th colspan = '2'> 50 - 59 years </th>";
                    html = html + "<th colspan = '2'> 60+ years     </th>";
                    html = html + "</tr>";

                    html = html + "<tr>";
                    for (int i = 0; i < 7; i++){
                        html = html + "<th> F </th>";
                        html = html + "<th> M </th>";
                    }
                    html = html + "</tr>";


                    for (table f3 : table){
                        names.add(f3.getname());
                        year.add(f3.getyear());
                        countPeople.add(f3.getcount());
                    }

                    for (int j = 0; j < names.size(); j = j + 28){
                        html = html + "<tr>";
                        html = html +    "<td>" + names.get(j) + "</td>";
                        html = html +    "<td>" + year.get(j) + "</td>";
                        int l = j;
                        for (int k = 0; k < 14; k++){
                            html = html +    "<td>" + countPeople.get(l) + "</td>";
                            l++;
                        }
                        html = html + "</tr>";
                    }

                    html = html + """                
                            </table>
                    """;
                }
        
            }

            else{
                html = html + "<p> Please choose a status.</p>";
            }
        }

        html = html + "</div>";
    
        html = html + "</div>";

        // Close Content div
        html = html + "</div>";

        html = html + """
            <div class = 'findOutMore'>
                <br>
                <h2> FIND OUT MORE </h2>
                <table>
                <tr>
                <td colspan = '2'><a class='page1' href='page4.html'><b>Number of Homeless <br> People in Particular Areas</b></a></td>
                </tr>
                <tr>
                <td class='blankrow' colspan = '2'></td>
                </tr>
                <tr>
                <td><a class='page3' href='page5.html'><b>Comparing Homelessness <br> with Other Factors</b></a></td>
                <td><a class='page4' href='page6.html'><b>Change in Homelessness <br> over time</b></a></td>
                </tr>
                </table>
                <br>
            </div>
        """;

        // Footer
        html = html + """
            <div class='footer'>
                
            <br>
            <img src = 'flag1.png' class = 'column1' alt='flag'>
            <img src = 'flag2.png' class = 'column1' alt='flag'>
            <img src = 'flag3.png' class = 'column1' alt='flag'>
            <br>

            <p> Australian Homelessness acknowledges the Traditional <br>
            Custodians of lands upon which we meet and work on. We <br>
            recognize their continuing connection to land, waters and <br>
            culture. We pay our respects to their people and elders past, <br>
            present and future. </p>
            <br>

            </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }
}
