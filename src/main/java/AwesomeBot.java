import com.google.common.io.BaseEncoding;
import org.telegram.telegrambots.api.methods.send.SendMessage;
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
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText(update.getMessage().getText());
            try {
                sendMessage(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}