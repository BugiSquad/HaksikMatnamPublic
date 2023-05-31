package BugiSquad.HaksikMatnam.member.entity;

import BugiSquad.HaksikMatnam.common.Timestamped;
import BugiSquad.HaksikMatnam.matching.entity.MemberNoteRoomRelation;
import BugiSquad.HaksikMatnam.member.etc.Gender;
import BugiSquad.HaksikMatnam.member.etc.MemberType;
import BugiSquad.HaksikMatnam.order.entity.Orders;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends Timestamped implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String phone;
    private int studentId;
    private String email;
    private String profileUrl; // 23.04.17 홍석준 추가
    private int grade; //23.05.03 학년 추가
    @Enumerated(EnumType.STRING)
    private Gender gender;  //23.05.03 성별 추가
    private String department;
    @Enumerated(EnumType.STRING)
    private MemberType memberType;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "interest_id")
    private Interest interest;
    @OneToMany(mappedBy = "member")
    private List<Review> reviewList = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Orders> ordersList = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<MemberNoteRoomRelation> groupList = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<ShoppingCart> shoppingCartList = new ArrayList<>();
    @JsonIgnore
    @Column(name = "password_code")
    private String password;
    private LocalDateTime voteTime;
    private Long voteMenuId;

    public void changeVoteTime(LocalDateTime now) {
        this.voteTime = now;
    }

    public void changeVoteMenuId(Long id) {
        this.voteMenuId = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changePhone(String phone) {
        this.phone = phone;
    }

    public void changeDepartment(String department) {
        this.department = department;
    }

    public void changeMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public void changeProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void changeGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Member(String name, String phone, int studentId, String email, String profileUrl, int grade, Gender gender, String department, MemberType memberType, Interest interest, String password) {
        this.name = name;
        this.phone = phone;
        this.studentId = studentId;
        this.email = email;
        this.profileUrl = profileUrl;
        this.grade = grade;
        this.gender = gender;
        this.department = department;
        this.memberType = memberType;
        this.interest = interest;
        this.password = password;
    }
}
