package com.sulsul.suldaksuldak.domain.user;

import com.sulsul.suldaksuldak.constant.auth.Gender;
import com.sulsul.suldaksuldak.constant.auth.Registration;
import com.sulsul.suldaksuldak.domain.BaseEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_user"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Integer birthdayYear;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Registration registration;

    protected User () {}

    protected User (
            Long id,
            String email,
            String password,
            String nickname,
            Gender gender,
            Integer birthdayYear,
            Registration registration
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.birthdayYear = birthdayYear;
        this.registration = registration;
    }

    public static User of (
            Long id,
            String email,
            String password,
            String nickName,
            Gender gender,
            Integer birthdayYear,
            Registration registration
    ) {
        return new User(
                id,
                email,
                password,
                nickName,
                gender,
                birthdayYear,
                registration
        );
    }
}
