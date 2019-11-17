
package org.openmore.cms.entity;

import org.openmore.cms.entity.enums.MessageDataType;
import org.openmore.cms.entity.enums.MessageType;

/**聊天消息实体类*/
public class ChatMessage {
    private MessageType type;
    private MessageDataType datatype;
    private String content;
    /**消息发送人*/
    private String sender;
    private String senderTo;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderTo() {
        return senderTo;
    }

    public void setSenderTo(String senderTo) {
        this.senderTo = senderTo;
    }

    public MessageDataType getDatatype() {
        return datatype;
    }

    public void setDatatype(MessageDataType datatype) {
        this.datatype = datatype;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                "datatype=" + datatype +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", senderTo='" + senderTo + '\'' +
                '}';
    }
}