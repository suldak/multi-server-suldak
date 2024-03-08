package com.sulsul.suldaksuldak.domain.admin;

import com.sulsul.suldaksuldak.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@ToString
@Table(
        name = "tb_admin_user"
)
@EntityListeners(AutoCloseable.class)
@Entity(name = "adminUser")
public class AdminUser extends BaseEntity {
    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String adminId;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String adminPw;

    @Setter
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String adminNm;

    protected AdminUser() {}

    protected AdminUser(
            Long id,
            String adminId,
            String adminPw,
            String adminNm
    ) {
        this.id = id;
        this.adminId = adminId;
        this.adminPw = adminPw;
        this.adminNm = adminNm;
    }

    public static AdminUser of (
            Long id,
            String adminId,
            String adminPw,
            String adminNm
    ) {
        return new AdminUser(
                id,
                adminId,
                adminPw,
                adminNm
        );
    }
}
