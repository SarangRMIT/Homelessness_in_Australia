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
public class PageST22 implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page4.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Number of Homeless People</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='pageST22.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='page6.html'>Change in <br> Homelessness</a>
                <a href='page5.html'>Comparing <br> Homelessness</a>
                <a href='page4.html' class = 'active'>Number of <br> Homeless People</a>
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
                <h1>A Glance at the Number of Homeless <br> People in Particular Areas</h1>
                <br>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Look up some information from JDBC and therefore, using the JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        //Web form
        html = html + "   <br>";

        html = html + "<form action='#statetype_drop' method='post'>";
        
        html = html + "   <div class='form-group1'>";
        html = html + "      <label for='statetype_drop'><b>CHOOSE A STATE OF THE LGA YOU WISH TO SEE:</b></label>";
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

        html = html + "<div class = 'button1'>";
        html = html + "<br>";
        html = html + "   <button type='submit' class='button1'>Click here to obtain <br> LGA options</button>";
        html = html + "</div>";

        html = html + "</form>";

        //Obtain the data recieved by this form
        String statetype_drop = context.formParam("statetype_drop");
        //Choose a state
        //Tell the user which state they have chosen, as it does not get stored in the dropdown itself
        if (statetype_drop != null){
            if (statetype_drop.equals("Choose a state*")){
                html = html + "<p class = 'stateChosen' id = 'stateChosen'><b>Please choose a valid dropdown option to obtain LGA options.</b></p>";    
            }
            else{
                //html = html + "<br>";
                html = html + "<div class = 'stateChosen' id = 'stateChosen'><h4>State Chosen: " + statetype_drop + "</h4></div>";
            }
        }
        else{
            html = html + "<h3 class = 'stateChosenStatement'> Please submit the 'Click Here to Obtain LGA options' button to choose a LGA. </h3>"; 
        }

        //Options for the dropdowns
        String[] sex_options = {"Male","Female"};
        String[] age_options = {"0-9 years","10-19 years","20-29 years","30-39 years", "40-49 years","50-59 years","60+ years"};
        String[] status_options = {"Homeless","At-risk"};
        String[] year_options = {"2016", "2018"};
        String[] sizeofpopulation_options = {"LGA","State","Australia"};

        //Second Form Starts
        html = html + "<form action='#result' method='post'>";

        if (statetype_drop != null){
            html = html + "   <div class='form-group2'>";
            html = html + "      <label for='lgabystatetype_drop'><b>CHOOSE A LOCAL GOVERNMENT AREA (LGA):</b></label>";
            html = html + "      <select id='lgabystatetype_drop' name='lgabystatetype_drop'>";
            html = html + "         <option>Choose a LGA*</option>";

            // Gettting the Name of the LGAs according to the State Chosen from the Database
            ArrayList<LGA> lgaByState = jdbc.getLGAbyState(statetype_drop);
            for (LGA lgaName : lgaByState){
                html = html + "     <option>" + lgaName.getName16() + "</option>";
            }

            //Continuing the HTML
            html = html + "      </select>";
            html = html + "   </div>";

        }

        html = html + "   <br>";
        html = html + "   <hr class='hr1' color='black'>";

        html = html + "<h2>Total Number of Homeless/At-Risk People <br> of Different Categories</h2>";

        //DROPDOWN FOR SEX CATEGORIES
        html = html + "   <br>";
        html = html + "   <div class='form-group3'>";
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

        for (String i : status_options){
            html = html + "     <option>" + i + "</option>";
        }

        html = html + "      </select>";
        html = html + "   </div>";

        //DROPDOWN FOR YEAR CATEGORIES
        html = html + "   <br>";
        html = html + "   <div class='form-group6'>";
        html = html + "      <select id='chooseyear_drop' name='chooseyear_drop'>";
        html = html + "         <option >Choose a Year*</option>";

        for (String i : year_options){
            html = html + "     <option>" + i + "</option>";
        }

        html = html + "      </select>";
        html = html + "   </div>";

        html = html + "   <br>";

        html = html + "   <hr class='hr1' color='black'>";

        html = html + "<h2 id = 'result'>Percentage of Selected Category against <br> Various Proportions of Population</h2>";

        //DROPDOWN FOR SIZE OF POPULATION OPTIONS
        html = html + "   <br>";
        html = html + "   <div class='form-group7'>";
        html = html + "      <select id='choosepopulationsize_drop' name='choosepopulationsize_drop'>";
        html = html + "         <option>Choose a Size of Population</option>";

        for (String i : sizeofpopulation_options){
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

        //Retrieving the data collected by the Form
        String lgabystatetype_drop = context.formParam("lgabystatetype_drop");
        String choosesex_drop = context.formParam("choosesex_drop");
        String chooseage_drop = context.formParam("chooseage_drop");
        String choosestatus_drop = context.formParam("choosestatus_drop");
        String chooseyear_drop = context.formParam("chooseyear_drop");  
        String choosepopulationsize_drop = context.formParam("choosepopulationsize_drop");

        //Storing the LGA chosen by the user in a different String
        String LGAChosen = lgabystatetype_drop;

        ArrayList<State> StateName = jdbc.getStatebyLGA(LGAChosen);
        ArrayList<LGAProperties> LGAproperties = jdbc.getLGAProperties(LGAChosen);

        //A 'result' div to format the results
        html = html + "<div class = 'result'>";

        //Get the name of the State in which the LGA resides
        String nameState = "";
        for (State name : StateName){
            nameState = name.getStateName();
        }

        if (LGAChosen != null){
            if (LGAChosen.equals("Choose a LGA*")){
                html = html + "<p> Please choose a valid LGA. </p>"; 
            }
            else{
                html = html + "   <hr class='hr1' color='black'>";
                //Output the chosen LGA and their corresponding State
                html = html + "<h3>" + LGAChosen + ", " + nameState + "</h3>";
                for (LGAProperties property : LGAproperties){
                    //Output the Properties (Type, Area and Population) of the chosen LGA
                    html = html + "Type: " + property.getLGAType() + "<br>";
                    html = html + "Area: " + property.getLGAArea() + " sq. km <br>";
                    html = html + "Total Population (2018): " + property.getLGAPopulation() + "<br>";
                    html = html + "   <hr class='hr2' color='#1B2355'>";
                }
            }
        }

       //If all the dropdown fields are chosen, ouput the number of homeless/at-risk for the categories chosen by the user 
       if (LGAChosen != null && chooseyear_drop != null && choosesex_drop != null && choosestatus_drop != null & chooseage_drop != null){
           if (LGAChosen.equals("Choose a LGA*") || chooseyear_drop.equals("Choose a Year*")  || choosesex_drop.equals("Choose a Sex*") || choosestatus_drop.equals("Choose a Status*") || chooseage_drop.equals("Choose an Age Group*")){
                html = html + "<p> To view the total number of homeless / at-risk people, <br> please choose a value for all the dropdowns.</p>"; 
                html = html + "   <hr class='hr2' color='#1B2355'>";
           } 
           else{
                html = html + "<p> The total number of <b>" + choosestatus_drop + " " + choosesex_drop + "s</b> in <b>" + LGAChosen + " </b>between <b>" + chooseage_drop + ",</b> in the year <b>" + chooseyear_drop + ",</b> were: </p>";
                html = html + "<h3>" + outputCount(LGAChosen, chooseyear_drop, choosesex_drop, choosestatus_drop, chooseage_drop) + "</h3>";
                html = html + "   <hr class='hr2' color='#1B2355'>";
           }
        }

       //html = html + "   <hr class='hr2' color='#1B2355'>";

       String populationSize = "";

       if (choosepopulationsize_drop != null){
           switch (choosepopulationsize_drop){
               case "LGA":
                    populationSize = LGAChosen;
                    break;
                case "State":
                    populationSize = nameState;
                    break;
                case "Australia":
                    populationSize = "Australia";
            }
       }

       int year = 0;

       if (chooseyear_drop != null){
           switch (chooseyear_drop){
               case "2016":
                    year = 2016;
                    break;
                case "2018":
                    year = 2018;
                    break;
            }
        }

       //If all the dropdown fields are chosen, ouput the percentage of homeless/at-risk for the categories chosen by the user against different sizes of population
       if (LGAChosen != null && choosepopulationsize_drop != null && chooseyear_drop != null && choosesex_drop != null && choosestatus_drop != null & chooseage_drop != null){
            if (LGAChosen.equals("Choose a LGA*") || choosepopulationsize_drop.equals("Choose a Size of Population") || chooseyear_drop.equals("Choose a Year*")  || choosesex_drop.equals("Choose a Sex*") || choosestatus_drop.equals("Choose a Status*") || chooseage_drop.equals("Choose an Age Group*") ){
                html = html + "<p> To view the percentage of a selected category, <br> please choose a value for all the dropdowns.</p>"; 
            } 
            else{

                html = html + "<p> The percentage of <b>" + choosestatus_drop + " " + choosesex_drop + "s</b> in <b>" + LGAChosen + " </b>between <b>" + chooseage_drop + ",</b> in the year <b>" + chooseyear_drop + ",</b><br> against the total homeless/at-risk population of <b>" + populationSize + " </b>is: </p>";
           
                String count = outputCount(LGAChosen, chooseyear_drop, choosesex_drop, choosestatus_drop, chooseage_drop);
           
                //Convert the obtained count of homeless/at-risk people to an integer
                int countNum = Integer.parseInt(count);
           
                //Find out which populationSize the user chose and output the result accordingly
                if (countNum == 0){
                    html = html + "<h3> 0% </h3>";
                }
                else{
                    if (populationSize == LGAChosen){
                        int sum = jdbc.SumOfCountByLGA(LGAChosen, year);
                        String percentage = String.format("%.2f", ((float)(countNum) / sum) * 100);
                        html = html + "<h3>" + percentage + "%</h3>";
                    }
                    else if(populationSize == nameState){
                        int sum = jdbc.SumOfCountByState(nameState, year);
                        String percentage = String.format("%.4f", ((float)(countNum) / sum) * 100);
                        html = html + "<h3>" + percentage + "%</h3>";
                    }
                    else if(populationSize == "Australia"){
                        int sum = jdbc.SumOfCountByCountry(year);
                        String percentage = String.format("%.6f", ((float)(countNum) / sum) * 100);
                        html = html + "<h3>" + percentage + "%</h3>";
                    }
                }
            }
        }

        //Close result class div
        html = html + "</div>"; 

        // Close Content div
        html = html + "</div>";

        html = html + """
                <div class = 'findOutMore'>
                <br>
                <h2> FIND OUT MORE </h2>
                <table>
                <tr>
                <td colspan = '2'><a class='page1' href='page3.html'><b>Ranking and List<br> of LGAs</b></a></td>
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


    //To find the count of homeless/at-risk people as per the categories chosen by the user
    public String outputCount(String lga_name, String year, String sex, String status, String age_group){

        String html = "";

        JDBCConnection jdbc = new JDBCConnection();

        //Converting the categories chosen by the users into the format in which the information is stored in the database
        int year_final = 0;
        String sex_final = "";
        String status_final = "";
        String age_group_final = "";

        switch (year){
            case "2016":
                year_final = 2016;
                break;
            case "2018":
                year_final = 2018;
                break;
        }

        switch(sex){
            case "Male":
                sex_final = "m";
                break;
            case "Female":
                sex_final = "f";
                break;
        }

        switch(status){
            case "Homeless":
                status_final = "homeless";
                break;
            case "At-risk":
                status_final = "at_risk";
        }

        switch(age_group){
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

        ArrayList<HomelessGroup> count = jdbc.getNumPeople(lga_name, year_final, sex_final, status_final, age_group_final);

       for (HomelessGroup num : count){
            html = html + num.getCount();
        } 

        //Return the count as a string
        return html;

    }

}
