import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;

public class Main {

  public static void main(String[] args) {
    testDrive();
  }
  private static void initTelegram(){
    ApiContextInitializer.init();
    TelegramBotsApi botsApi = new TelegramBotsApi();

    try {
      botsApi.registerBot(new AwesomeBot());
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
  private static void testDrive(){
      init();
     render("helloworld");
     renderDB();
  }
  private static void init(){
      port(Integer.valueOf(System.getenv("PORT")));
      staticFileLocation("/public");
  }
  private static void render(String output) {
      get("/", (request, response)-> {
          Map<String, Object> attributes = new HashMap<>();
          attributes.put("message", output);

          return new ModelAndView(attributes, "error.ftl");
      }, new FreeMarkerEngine());
  }
  private static void renderDB(){
      HikariConfig config = new  HikariConfig();
      //config.setDriverClassName("org.postgresql.Driver");
      config.setJdbcUrl(Config.JDBC_DB_URL );
      final HikariDataSource dataSource = (config.getJdbcUrl() != null) ?
              new HikariDataSource(config) : new HikariDataSource();
      get("/db", (req, res) -> {
          Map<String, Object> attributes = new HashMap<>();
          try(Connection connection = dataSource.getConnection()) {
              Statement stmt = connection.createStatement();
              ResultSet rs = stmt.executeQuery("SELECT id, full_name, username, photo, smile_at FROM employee");

              ArrayList<String> output = new ArrayList<String>();
              while (rs.next()) {
                  output.add( "Read from DB: " + rs.getString("full_name"));
              }

              attributes.put("results", output);
              return new ModelAndView(attributes, "db.ftl");
          } catch (Exception e) {
              attributes.put("message", "There was an error: " + e);
              return new ModelAndView(attributes, "error.ftl");
          }
      }, new FreeMarkerEngine());
  }
}
