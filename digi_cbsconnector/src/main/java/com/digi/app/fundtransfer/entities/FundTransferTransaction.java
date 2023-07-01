package com.digi.app.fundtransfer.entities;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.digi.app.masteraccounts.MasterAccounts;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Table
@Data
public class FundTransferTransaction {
    @Id
    private String digiTransactionId;

    @NotNull
    @NotEmpty
    private String drAccountNo;
    @NotNull
    @NotEmpty
    private String crAccountNo;
    private String transactionDate;
    private String drNarrative;
    private String crNarrative;
    @NotNull
    @NotEmpty
    private String amount;
    private String loanAccountNumber;
    private String transactionId;
    private String createdBy;
    private String authorizedBy;
    private int authorized;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;

    public FundTransferTransaction() {
        setDigiTransactionIdNewValue();
    }

    private void setDigiTransactionIdNewValue() {
        if (this.getDigiTransactionId() == null || this.getDigiTransactionId().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String newTransactionId = "ER".concat(uuid.toString().replaceAll("-", "").substring(0, 11));
            this.setDigiTransactionId(newTransactionId);
        }
    }
}
