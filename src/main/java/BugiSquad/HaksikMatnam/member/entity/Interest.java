package BugiSquad.HaksikMatnam.member.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Interest extends Timestamped {

    @Id @GeneratedValue
    @Column(name = "interest_id")
    private Long id;
    private Boolean pubg;
    private Boolean lol;
    private Boolean celebrity;
    private Boolean coffee;
    private Boolean dessert;
    private Boolean game;
    private Boolean popSong;
    private Boolean kPop;
    private Boolean jPop;
    private Boolean drama;
    private Boolean movie;
    private Boolean travel;
    private Boolean study;
    private Boolean hiking;
    private Boolean book;

    public void changePubg(boolean pubg) {
        this.pubg = pubg;
    }

    public void changeLol(boolean lol) {
        this.lol = lol;
    }

    public void changeCelebrity(boolean celebrity) {
        this.celebrity = celebrity;
    }

    public void changeCoffee(boolean coffee) {
        this.coffee = coffee;
    }

    public void changeDessert(boolean dessert) {
        this.dessert = dessert;
    }

    public void changeGame(boolean game) {
        this.game = game;
    }

    public void changePopSong(boolean popSong) {
        this.popSong = popSong;
    }

    public void changeKPop(boolean kPop) {
        this.kPop = kPop;
    }

    public void changeJPop(boolean jPop) {
        this.jPop = jPop;
    }

    public void changeDrama(boolean drama) {
        this.drama = drama;
    }

    public void changeMovie(boolean movie) {
        this.movie = movie;
    }

    public void changeTravel(boolean travel) {
        this.travel = travel;
    }

    public void changeStudy(boolean study) {
        this.study = study;
    }

    public void changeHiking(boolean hiking) {
        this.hiking = hiking;
    }

    public void changeBook(boolean book) {
        this.book = book;
    }

    public Interest(Boolean pubg, Boolean lol, Boolean celebrity, Boolean coffee, Boolean dessert, Boolean game,
                    Boolean popSong, Boolean kPop, Boolean jPop, Boolean drama, Boolean movie, Boolean travel,
                    Boolean study, Boolean hiking, Boolean book) {
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
    }
}
