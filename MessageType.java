package paxos;
/**
 * The class is enum class and list four types of message types.
 */

public enum MessageType {
    PREPARE,
    PROMISE,
    ACCEPT,
    ACCEPTED
}
