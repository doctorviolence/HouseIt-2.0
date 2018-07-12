package HouseIt.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "task_messages")
public class TaskMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_no", nullable = false)
    private long messageNo;

    @Column(name = "message_text")
    @Type(type = "text")
    private String messageText;

    @ManyToOne
    @JoinColumn(name = "task_no", referencedColumnName = "task_no", nullable = false)
    private Task task;

    public TaskMessage() {

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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}
