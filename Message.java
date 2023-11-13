package paxos;
/**
 * The class represent the message type and update
 * Override toString method and handle the invalid message error
 */

public class Message {
    private MessageType messageType;
    private String update;
    int transactionId;

    public Message(MessageType type, int transactionId, String update) {
        this.messageType = type;
        this.update = update;
        this.transactionId = transactionId;
    }

    public Message() {}

    public static Message parseMessage(String message) {
        Message m = new Message();
        String[] arr = message.split(",");
        if (arr.length != 3) {
            System.out.println("message error");
        }
        m.update = arr[2];
        m.transactionId = Integer.valueOf(arr[1]);
        m.messageType = MessageType.valueOf(arr[0]);

        return m;
    }

    public int getTransactionId() {
        return transactionId;
    }

    @Override
    public String toString() {
        String pre = getMessageType().toString();

        return pre + "," + transactionId + "," +  getUpdate();
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getUpdate() {
        return update;
    }
}
