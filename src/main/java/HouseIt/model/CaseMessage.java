package HouseIt.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "case_messages")
public class CaseMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private long messageNo;
    private String messageText;
    private Case c;

    public CaseMessage() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "case_message_no", nullable = false)
    public long getMessageNo() {
        return messageNo;
    }

    public void setMessageNo(long messageNo) {
        this.messageNo = messageNo;
    }

    @Column(name = "message_text")
    @Type(type = "text")
    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @ManyToOne
    @JoinColumn(name = "case_no", referencedColumnName = "case_no", nullable = false)
    public Case getCase() {
        return c;
    }

    public void setCase(Case c) {
        this.c = c;
    }

}
