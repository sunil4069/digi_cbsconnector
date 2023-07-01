package com.digi.app.masteraccounts;

import com.digi.app.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "master_accounts")
public class MasterAccounts extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String refStaffCode;
    private String accountName;
    @Column(unique = true)
    private String accountNumber;
    private String accountType;

}
