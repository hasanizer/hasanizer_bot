import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import java.util.Base64;
/**
 * Created by hasanizer on 4/21/17.
 */


public class AwesomeBot extends TelegramLongPollingBot {
    private final String user = "aGFzYW5pemVy";
    private final String token = "MzQ4MjQwMjczOkFBR2JwYmszcThiRFNQdzVPWXI3VkJ5LWFIMjdpOUZ4eVFv";

    @Override
    public String getBotUsername() {
        // TODO
        return String.valueOf(Base64.getDecoder().decode(user));
    }

    @Override
    public String getBotToken() {
        // TODO
        return String.valueOf(Base64.getDecoder().decode(token));
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