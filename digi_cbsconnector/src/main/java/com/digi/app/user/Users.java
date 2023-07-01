package com.digi.app.user;

import com.digi.app.user.enums.YesNoEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "staffs_code"})
})
public class Users {

    @Id
    @NotNull
    @Size(min = 4, max = 20)
    private String username;

    @NotNull
    private String password;

    @NotNull
    private boolean status = false;

    @OneToOne
    @JoinColumn(updatable = false)
    private Staffs staffs;

    //doesn't create a new column
    @Transient
    private String confirmpassword;

    @Transient
    private String newpassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_username", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Roles> roles;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    protected Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    protected Date updatedAt;

    private String lastUpdatedBy;
    private String authorized = YesNoEnum.NO.getValue();
    private String authorizedBy;
    private double txnlimit;
}
