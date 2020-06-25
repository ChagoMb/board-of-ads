package com.avito.models;

import com.avito.models.posting.Posting;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String text;

    @NonNull
    private LocalDateTime date;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    private User author;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Posting posting;

    @NonNull
    private ReadStatus readStatus = ReadStatus.NOT_READ;

    public User getRecipient() {
        return posting.getUser();
    }

    public enum ReadStatus {
        READ,
        NOT_READ
    }

}
