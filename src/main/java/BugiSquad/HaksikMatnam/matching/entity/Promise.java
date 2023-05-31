package BugiSquad.HaksikMatnam.matching.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Promise extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "promise_id")
    private Long id;
    //private String title;
    //private LocalDateTime updatedTime;
    private LocalDateTime promisedTime;
    private String location;

//    @Enumerated(EnumType.STRING)
//    private GroupType groupType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_room_id")
    private NoteRoom noteRoom;

    @Builder

    public Promise(LocalDateTime promisedTime, String location, NoteRoom noteRoom) {
        this.promisedTime = promisedTime;
        this.location = location;
        this.noteRoom = noteRoom;
    }
}
