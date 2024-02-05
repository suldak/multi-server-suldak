package com.sulsul.suldaksuldak.domain.user;

import com.sulsul.suldaksuldak.domain.BaseEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_reservation_user"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "reservationUser")
public class ReservationUser extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    protected ReservationUser () {}

    protected ReservationUser (
            Long id,
            String userEmail
    ) {
        this.id = id;
        this.userEmail = userEmail;
    }

    public static ReservationUser of (
            Long id,
            String userEmail
    ) {
        return new ReservationUser(
                id,
                userEmail
        );
    }
}
