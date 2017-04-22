import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import javax.ws.rs.POST;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import static spark.Spark.get;

/**
 * Created by hasanizer on 4/22/2017.
 */
public class PostgreDB {
    private static PostgreDB instance = null;
    private  HikariConfig config;
    private  HikariDataSource dataSource;
    private  List<Employee> employees;
    private  static String queryAllEmployees = "SELECT id, full_name, username, photo, smile_at FROM employee";

    private PostgreDB() {
        config = new HikariConfig();
        //config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(Config.JDBC_DB_URL);
        dataSource = (config.getJdbcUrl() != null) ?
                new HikariDataSource(config) : new HikariDataSource();

        employees = getAll();

    }

    public static PostgreDB getInstance() {
        if (instance == null) {
            instance = new PostgreDB();
        }
        return instance;
    }

    private static void render(String output) {
        get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", output);

            return new ModelAndView(attributes, "error.ftl");
        }, new FreeMarkerEngine());
    }

    private ArrayList<Employee> getAll() {
        ArrayList<Employee> output = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryAllEmployees);
            while (rs.next()) {
                Employee employee = new Employee(rs.getInt("id"), rs.getString("full_name"), rs.getString("username"), rs.getString("photo"), rs.getString("smileAt"));
                output.add(employee);
            }
            return output;
        } catch (Exception e) {
            return output;
        }
    }
    public  Employee getRandom(){
        if(employees.size()==0){
            return new Employee();
        }
        return employees.get(new Random().nextInt(employees.size()-1));
    }
}
