import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.IOException;
import java.io.StringWriter;

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
     render("hello world");
  }
  private static String render(String output) {
      StringWriter stringWriter = new StringWriter();
      stringWriter.append(output);
      return stringWriter.toString();
  }
}
