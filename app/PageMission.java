package app;

import java.util.ArrayList;

//import java.util.ArrayList;

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
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Our Mission</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='mission page.css' />";
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
                <h1>Our Mission</h1>
                <br>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        //Add the mission statement
        html = html + """
            <h1> Our Goal </h1>
            <p><b>'Australian Homelessness' aims to raise awareness on the issue of homelessness and help<br>
             people
            receive information so that they can assess the scale and graveness of <br> homelessness in Australia.<br>
            We hope to be able to present data in a way that caters to every and all types of needs of <br> the people that visit our website.</b>
            </p> 
        """;

        //The personas
        html = html + "<h1> Who Do We Service </h1> ";

        html = html + """
            <p><b>
            At 'Australian Homelessness,' we aim to cater to every range of user groups which may <br> 
            include people who wish to learn about this social challenge from scratch, or people who <br> 
            are looking to dive deeper into data and facts about homelessness in Australia. 
            <br>
            <br>
            Below are two personas which capture two different user groups that <br> may want
            to make use of our website:
            </b></p>
        """;

        JDBCConnection jdbc = new JDBCConnection();

        String name1 = "";
        int age1 = 0;
        String desc1 = "";
        String name2 = "";
        int age2 = 0;
        String desc2 = "";

        ArrayList<persona> persona = jdbc.getpersona1();
        for (persona p : persona){
            name1 = p.getname(); 
            age1 = p.getage();
            desc1 = p.getdesc();
        }

        ArrayList<persona> persona2 = jdbc.getpersona2();
        for (persona p1 : persona2){
            name2 = p1.getname();
            age2 = p1.getage();
            desc2 = p1.getdesc();
        }

        html = html + "<table>";

        html = html + "<tr>";
        html = html + "<td><br><b>" + name2 + "</b>";
        html = html + "     <br>Age: " + age2 + "<br>";
        html = html +  "    <br>" + desc2 + "</td>";
        html = html + "<td colspan = '3'><br> <img class = 'andrew' src='Andrew Simmons.png' alt='Andrew' height = '200' width = '290'></td>";
        html = html + "</tr>";

        html = html + "<tr>";
        html = html + "<td><br><b>" + name1 + "</b>";
        html = html + "     <br>Age: " + age1 + "<br>";
        html = html +  "    <br>" + desc1 + "</td>";
        html = html + "<td colspan = '3'><br><img class = 'choi' src='Connor Choi.png' alt='Connor' height = '200' width = '290'></td>";
        html = html + "</tr>";

        html = html + "</table>";


        html = html + "<br>";

        html = html + """
            <p><b>
                Through these personas we want to inform our users that we have pages that cater towards having <br> 
                shallow glances at the data on homelessness, like Connor, and we also have pages that suit <br> 
                the needs of people who would like to dive deeper into the facts and data, like Andrew.
            </p></b>
        """;

        //Student information
        html = html + "<h1> Who Are We </h1> ";

        String name3 = "";
        String num = "";
        String desc3 = "";

        ArrayList<student> student = jdbc.getstudent1("S3933878");
        for (student p : student){
            name3 = p.getname(); 
            num = p.getnum();
            desc3 = p.getdesc();
        }

        html = html + "<h1 class = 'name'><b> " + name3 + " (" + num + ") </b></h1>";
        html = html + "<p>" + desc3 + "</p>";
        
        html = html + "</br>";

        ArrayList<student> stud = jdbc.getstudent1("S3914175");
        for (student p : stud){
            name3 = p.getname(); 
            num = p.getnum();
            desc3 = p.getdesc();
        }

        html = html + "<h1 class = 'name'><b> " + name3 + " (" + num + ") </b></h1>";
        html = html + "<p>" + desc3 + "</p>"; 

        // Close Content div
        html = html + "</div>";
        
        //Find Out More section
        html = html + """
            <div class = 'findOutMore'>
                <br>
                <h2> FIND OUT MORE </h2>
                <table>
                <tr>
                <td><a class='page1' href='page3.html'><b>Ranking and List<br> of LGAs</b></a></td>
                <td><a class='page2' href='page4.html'><b>Number of Homeless <br> People in Particular Areas</b></a></td>
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
