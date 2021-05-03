package by.moon.viewbot.enums;

public enum Command {
    NONE("none"),
    START("/start"),

    WANT_BOT("\uD83D\uDCBB Хочу свой чат-бот"),
    FAQ_BOT("\uD83D\uDCCA FAQ о чат-ботах"),
    ABOUT_US("ℹ️ О нас"),
    CONNECT_WITH_MANAGER("☎️ Связь с менеджером"),
    OUR_PROJECTS("\uD83D\uDCCB Наши проекты"),
    CHANGE_GREETING("Изменить приветствие"),
    CHANGE_ABOUT_US("Изменить \"О нас\""),
    CHANGE_CONNECT_WITH_MANAGER("Изменить \"Св. с мен-ром\""),
    BOT_BENEFIT_EDIT_MENU("Редактирование FAQ"),
    OUT_OF_ADMIN("Выйти"),
    PASSWORD("1111"),
    ADD_FREQUENTLY_QUESTION("Добавить вопрос"),
    DELETE_FREQUENTLY_QUESTION("Удалить вопрос"),
    BACK_TO_ADMIN_MENU("Вернуться"),
    READ_FREQ_QUESTION("READ_FREQ_QUESTION"),
    CANCEL("Отмена"),
    BACK_TO_BOT_BENEFIT("BACK_TO_BOT_BENEFIT"),
    DELETE_FREQ_QUESTION("DELETE_FREQ_QUESTION"),
    OUR_PROJECTS_EDIT_MENU("Редактирование проектов"),
    ADD_OUR_PROJECT("Добавить проект"),
    DELETE_OUR_PROJECT("Удалить проект"),
    READ_OUR_PROJECT("READ_OUR_PROJECT"),
    DELETE_PROJECT("DELETE_PROJECT"),
    BACK_TO_OUR_PROJECT("BACK_TO_OUR_PROJECT"),
    NEW_REQUESTS("Новые заявки"),
    CHANGE_USER_MENU_MESSAGE("Изменить сообщение гл.меню"),
    HIDE_REQUEST("HIDE_REQUEST");
    String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Command fromString(String command) {
        for (Command botCommand : Command.values()) {
            if (botCommand.getCommand().equals(command)) return botCommand;
        }
        return NONE;
    }
}
