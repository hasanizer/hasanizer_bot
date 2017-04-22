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
        init();
        initTelegram();
    }

    private static void initTelegram() {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new AwesomeBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static void init() {
        port(Integer.valueOf(System.getenv("PORT")));
        staticFileLocation("/public");
        PostgreDB.getInstance();
    }


}
