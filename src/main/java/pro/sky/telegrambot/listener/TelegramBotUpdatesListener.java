package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.ChatService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    final private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    final private ChatService chatService;

    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Processing update: {}", update);
                Message message = update.message();
                if (message == null) {
                    logger.error("message is null");
                    return;
                }
                Long chatId = update.message().chat().id();
                String text = update.message().text();
                logger.info("Processing update: text={}, chat-id={}", text, chatId);
                if(text == null) {
                    text = "";
                }
                // Process your updates here
                String resultText;
                switch (text) {
                    case "/start":
                    case "/ушедомс":
                        resultText = chatService.handleStart(update);
                        break;
                    case "/reset":
                        resultText = chatService.handleReset(update);
                        break;
                    case "/list":
                        resultText = chatService.handleList(update);
                        break;
                    case "/create":
                        resultText = chatService.handleCreate(update);
                        break;
                    case "/author":
                        resultText = "My author is Sergei Gots,\n"
                                + "and my source code could be found somewhere on https://github.com/Sergei-Gots";
                        break;
                    default:
                        resultText = chatService.handle(update);
                }
                SendResponse sendResponse = telegramBot.execute(
                        new SendMessage(chatId, resultText)
                );
                if (!sendResponse.isOk()) {
                    logger.error("Some error during telegramBot.execute; sendResponse.errorCode()={},",
                            sendResponse.errorCode()
                    );
                }
            });
        } catch (Exception e) {
            logger.error("process(udpate): some expression e with message e.getMessage() = \"{}\" was thrown.",
                    e.getMessage());
            e.printStackTrace();
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;

    }

}
