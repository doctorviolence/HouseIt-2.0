package HouseIt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "case_messages")
public class CaseMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_no", nullable = false)
    private long messageNo;

    @Column(name = "message_text")
    @Type(type = "text")
    private String messageText;

    @JsonIgnore // temporary fix
    @ManyToOne
    @JoinColumn(name = "case_no", referencedColumnName = "case_no", nullable = false)
    private Case c;

    public CaseMessage() {

    }

    public long getMessageNo() {
        return messageNo;
    }

    public void setMessageNo(long messageNo) {
        this.messageNo = messageNo;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Case getCase() {
        return c;
    }

    public void setCase(Case c) {
        this.c = c;
    }

}
