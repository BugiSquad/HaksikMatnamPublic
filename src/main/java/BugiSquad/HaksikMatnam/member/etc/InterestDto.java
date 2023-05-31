package BugiSquad.HaksikMatnam.member.etc;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class InterestDto {

    public Long interestId;
    public boolean pubg;
    public boolean lol;
    public boolean celebrity;
    public boolean coffee;
    public boolean dessert;
    public boolean game;
    public boolean popSong;
    public boolean kPop;
    public boolean jPop;
    public boolean drama;
    public boolean movie;
    public boolean travel;
    public boolean study;
    public boolean hiking;
    public boolean book;
    public LocalDateTime modifiedAt;

    public InterestDto(Long interestId, boolean pubg, boolean lol, boolean celebrity, boolean coffee, boolean dessert,
                       boolean game, boolean popSong, boolean kPop, boolean jPop, boolean drama, boolean movie,
                       boolean travel, boolean study, boolean hiking, boolean book, LocalDateTime modifiedAt) {
        this.interestId = interestId;
        this.pubg = pubg;
        this.lol = lol;
        this.celebrity = celebrity;
        this.coffee = coffee;
        this.dessert = dessert;
        this.game = game;
        this.popSong = popSong;
        this.kPop = kPop;
        this.jPop = jPop;
        this.drama = drama;
        this.movie = movie;
        this.travel = travel;
        this.study = study;
        this.hiking = hiking;
        this.book = book;
        this.modifiedAt = modifiedAt;
    }
}
