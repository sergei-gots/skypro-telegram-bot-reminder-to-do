package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Date;

@Entity
@Table(name="chats")
public class ChatEntry {
    @Id
    //Value should be retrieved from telegram update.message.chat.id() in a listener.
    private Long id;


    public enum ChatStates {
        INITIAL_STATE,
        START,
        INPUT_EVENT,
        INPUT_TIME, INPUT_DATE
    }


    public enum Languages {
        ENG,
        MOK
    }


    private ChatStates state;

    private Languages lang;

    @Column(name = "user_first_name")
    private String userFirstName;

    private String message;


    @Column(name = "target_date")
    private Date date;

    @Column(name = "target_time")
    private Time time;

    public ChatEntry() {
    }

    public ChatEntry(Long id,
                     ChatStates state,
                     Languages lang,
                     String userFirstName,
                     String message,
                     Date date,
                     Time time) {
        this.id = id;
        this.state = state;
        this.lang = lang;
        this.userFirstName = userFirstName;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public ChatEntry(Long id, String userFirstName) {
        this(id, ChatStates.INITIAL_STATE, Languages.ENG, userFirstName, "", null, null);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ChatStates getState() {
        return state;
    }

    public void setState(ChatStates state) {
        this.state = state;
    }

    public Languages getLang() {
        return lang;
    }

    public void setLang(Languages lang) {
        this.lang = lang;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", state=" + state +
                ", lang=" + lang +
                ", userFirstName='" + userFirstName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
