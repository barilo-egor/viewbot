package by.moon.viewbot.enums;

public enum SystemMessage {
    NONE("none"),
    ADMIN_MENU("Вы в админ панели."),
    ADMIN_BOT_BENEFIT_MENU("Вы в панели изменения \"Польза бота\""),
    USER_BOT_BENEFIT_MENU("Что вы хотели бы узнать подробнее?"),
    EMPTY("Пусто."),
    ENTER_NEW_QUESTION("Введите текст вопроса."),
    ENTER_NEW_ANSWER("Введите текст ответа."),
    SAVED("Сохранено."),
    CHOOSE_FREQ_QUESTION_FOR_DELETE("Выберите вопрос для удаления."),
    ENTER_NEW_GREETING("Введите новое приветствие."),
    ENTER_NEW_ABOUT_US("Введите новый текст для \"О нас\"."),
    ENTER_NEW_CONNECT_WITH_MANAGER("Введите новый текст для связи с менеджером."),
    OUR_PROJECTS_MENU("Вы в панели изменения \"Наши проекты\""),
    ENTER_NAME_FOR_OUR_PROJECT("Введите название проекта."),
    ENTER_LINK_FOR_OUR_PROJECT("Введите ссылку на проект."),
    ENTER_DESCRIPTION_FOR_OUR_PROJECT("Введите описание проекта."),
    READ_OUR_PROJECT("Нажав на проект, вы сможете прочитать о нем дополнительную информацию."),
    CHOOSE_OUR_PROJECT_FOR_DELETE("Выберите проект для удаления."),
    ENTER_DESCRIPTION_FOR_REQUEST("Опишите, пожалуйста, вкратце, " +
            "для каких целей Вам нужен бот?"),
    SEND_CONTACT_FOR_REQUEST("Для того, чтобы мы могли с вами связаться, отправьте пожалуйста Ваш контакт."),
    REQUEST_CREATED("Спасибо за Ваше обращение. Скоро с Вами свяжется наш менеджер."),
    ENTER_NEW_USER_MENU_MESSAGE("Введите текст сообщения."),
    NEW_REQUEST("Поступила новая заявка.");

    String command;

    SystemMessage(String command) {
        this.command = command;
    }

    public String getSystemMessage() {
        return command;
    }

    public static SystemMessage fromString(String message) {
        for (SystemMessage systemMessage : SystemMessage.values()) {
            if (systemMessage.getSystemMessage().equals(message)) return systemMessage;
        }
        return NONE;
    }
}
