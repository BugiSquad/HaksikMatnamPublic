package BugiSquad.HaksikMatnam.menu.etc;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class NewMenuDto {

    private Long newMenuId;
    private String name;
    private String detail;
    private String url;
    private int votes;
    private String category;
    private LocalDateTime modifiedAt;

    public NewMenuDto(Long newMenuId, String name, String detail, String url, int votes, String category, LocalDateTime modifiedAt) {
        this.newMenuId = newMenuId;
        this.name = name;
        this.detail = detail;
        this.url = url;
        this.votes = votes;
        this.category = category;
        this.modifiedAt = modifiedAt;
    }

    public NewMenuDto changeId(Long id) {
        this.newMenuId = id;
        return this;
    }

    public NewMenuDto changeName(String name) {
        this.name = name;
        return this;
    }

    public NewMenuDto changeDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public NewMenuDto changeUrl(String url) {
        this.url = url;
        return this;
    }

    public NewMenuDto changeVotes(int votes) {
        this.votes = votes;
        return this;
    }

    public NewMenuDto changeCategory(String category) {
        this.category = category;
        return this;
    }

    public NewMenuDto changeModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }
}
