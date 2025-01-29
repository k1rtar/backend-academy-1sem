package backend.academy.game.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Отвечает за предоставление сообщений из ресурсов.
 */
public class MessageProvider {
    private static final ResourceBundle MESSAGES;

    static {
        MESSAGES = ResourceBundle.getBundle("messages");
    }

    public String getMessage(String key, Object... params) {
        String pattern = MESSAGES.getString(key);
        return MessageFormat.format(pattern, params);
    }
}
