package sg.edu.np.onetokenocbc;

public class Requests {

    private String UIDUser;
    private String MessageID;
    private String Message;
    private Boolean Replied;
    private Boolean Authorise;

    public Requests(){

    }

    public Requests(String id, String message, String MessageID ,Boolean authorise, Boolean replied){
        this.UIDUser = id;
        this.MessageID = MessageID;
        this.Message = message;
        this.Authorise = authorise;
        this.Replied = replied;
    }

    public String getMessageID() {
        return MessageID;
    }

    public void setMessageID(String messageID) {
        MessageID = messageID;
    }

    public Boolean getReplied() {
        return Replied;
    }

    public void setReplied(Boolean replied) {
        Replied = replied;
    }

    public String getUIDUser() {
        return UIDUser;
    }

    public void setUIDUser(String UIDUser) {
        this.UIDUser = UIDUser;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Boolean getAuthorise() {
        return Authorise;
    }

    public void setAuthorise(Boolean authorise) {
        Authorise = authorise;
    }
}
