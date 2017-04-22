import com.google.common.io.BaseEncoding;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by hasanizer on 4/21/17.
 */


public class AwesomeBot extends TelegramLongPollingBot {
    private final String user = "aGFzYW5pemVy";
    private final String token = "MzQ4MjQwMjczOkFBR2JwYmszcThiRFNQdzVPWXI3VkJ5LWFIMjdpOUZ4eVFv";
    private Employee employee;
    private boolean stillAsking = false;
    private boolean start = false;

    @Override
    public String getBotUsername() {
        String decode = "";
        try {
            decode = new String(BaseEncoding.base64().decode(user), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return decode;
    }

    @Override
    public String getBotToken() {
        String decode = "";
        try {
            decode = new String(BaseEncoding.base64().decode(token), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return decode;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getText().startsWith("/")) {
                processCommand(update.getMessage());
            } else {
                if (start) {
                    processJawaban(update.getMessage());
                } else {
                    sendMessage(update.getMessage().getChatId(), "Ok, fine \uD83D\uDE0A");
                }
            }
        }
    }

    private void processCommand(Message message) {
        String command = message.getText();
        Long id = message.getChatId();
        System.out.println("cmd: " + command);
        if (command.equalsIgnoreCase("/start")) {
            start = true;
            sendMessage(id, "Silakan tebak nama, setelah ketik perintah /lanjut. Have fun \uD83D\uDE0A");
        } else if (command.equalsIgnoreCase("/lanjut") && start) {
            if (stillAsking) {
                sendMessage(id, "Tebak dulu nama yang barusan ya...");
            } else {
                stillAsking = true;
                employee = PostgreDB.getInstance().getRandom();
                sendMessage(id, employee.getPhoto());
            }
        } else if (command.equalsIgnoreCase("/nyerah") && start) {
            sendMessage(id, "Nama doi itu " + employee.getFullName());
            stillAsking = false;
        } else if (command.equalsIgnoreCase("/stop")) {
            start = false;
            stillAsking = false;
            sendMessage(id, "/start lagi kalau mau maen.");
        } else {
            sendMessage(id, "Ok, fine \uD83D\uDE0A");
        }
    }

    private void processJawaban(Message message) {
        String answer = message.getText();
        Long id = message.getChatId();
        System.out.println("jawab: " + answer);
        if (employee.getFullName().toLowerCase().contains(answer.toLowerCase())) {
            sendMessage(id, "Ya, betul betul betul.");
            stillAsking = false;
        } else {
            sendMessage(id, "Salah, tebak lagi ya. /nyerah? ");
        }
    }

    private void sendMessage(final Message message) {
        SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(message.getChatId())
                .setText(message.getText());
        try {
            sendMessage(sendMessage); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Long id, String message) {
        SendMessage sendMessage = new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(id)
                .setText(message);
        try {
            sendMessage(sendMessage); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}