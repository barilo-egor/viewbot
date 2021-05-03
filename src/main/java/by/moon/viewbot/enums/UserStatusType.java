package by.moon.viewbot.enums;

public enum UserStatusType {
    NONE("none"),
    NOT_ACTIVE("Неактивный"),
    EXECUTOR("Исполнитель"),
    ADVERTISER("Рекламодатель"),
    PLAYER("Игрок");

    String status;

    UserStatusType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static UserStatusType fromString(String statusType){
        for(UserStatusType userStatusType : UserStatusType.values()){
            if(userStatusType.getStatus().equals(statusType)) return userStatusType;
        }
        return NONE;
    }
}
