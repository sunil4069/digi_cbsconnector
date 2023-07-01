package com.digi.app.fundtransfer.service;

import com.digi.app.constants.ErrorMessageConstants;
import com.digi.app.exception.CustomValidationException;
import com.digi.app.fundtransfer.entities.FundTransferTransaction;
import com.digi.app.fundtransfer.repo.FundTransferTransactionRepository;
import com.digi.app.message.HttpResponses;
import com.digi.app.message.Messages;
import com.digi.app.telnet.component.TelnetConnectionComponent;
import com.digi.app.user.Users;
import com.digi.app.user.enums.RolesEnum;
import com.digi.app.user.service.UsersService;
import com.digi.app.util.CommandsGenerator;
import com.digi.app.util.UtilitiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FundTransferTransactionServiceImpl implements FundTransferTransactionService {
	private FundTransferTransactionRepository fundTransferTransactionRepository;
	private TelnetConnectionComponent telnetConnectionComponent;
	private CommandsGenerator commandsGenerator;
	private UtilitiesService utilitiesService;
	private UsersService usersService;

	@Autowired
	public void setCommandsGenerator(CommandsGenerator commandsGenerator) {
		this.commandsGenerator = commandsGenerator;
	}

	@Autowired
	public void setFundTransferTransactionRepository(
			FundTransferTransactionRepository fundTransferTransactionRepository) {
		this.fundTransferTransactionRepository = fundTransferTransactionRepository;
	}

	@Autowired
	public void setTelnetConnectionComponent(TelnetConnectionComponent telnetConnectionComponent) {
		this.telnetConnectionComponent = telnetConnectionComponent;
	}

	@Autowired
	public void setUtilitiesService(UtilitiesService utilitiesService) {
		this.utilitiesService = utilitiesService;
	}

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@Override
	public Optional<FundTransferTransaction> saveFundTransferTransaction(
			FundTransferTransaction fundTransferTransaction, Principal principal) {
		String crac="ERF-"+fundTransferTransaction.getLoanAccountNumber();
		fundTransferTransaction.setDrNarrative(crac);
		fundTransferTransaction.setTransactionDate(utilitiesService.getCurrentDateInDigiDateFormat());
		fundTransferTransaction.setCreatedBy(principal.getName());
		return Optional.ofNullable(fundTransferTransactionRepository.save(fundTransferTransaction));
	}

	/**
     * This method
     * 1. saves authorized by in FundTransfer detail
     * 2. generates command
     * 3. sends command through telnet
     * 4. gets response as true or false
     *
     * @param digiTransactionId
     * @param principal
     * @return
     */
    @Override
    public boolean getTelnetFundTransferTransaction(@PathVariable String digiTransactionId, Principal principal) {
        boolean transactionStatus = false;
        Optional<FundTransferTransaction> fundTransferTransactionOptional = fundTransferTransactionRepository.findById(digiTransactionId);
        if (fundTransferTransactionOptional.isPresent()) {
            FundTransferTransaction fundTransferTransaction = fundTransferTransactionOptional.get();
            double txnAmount = Double.parseDouble(fundTransferTransaction.getAmount());
            Optional<Users> usersOptional = usersService.getUser(principal.getName());
            if (usersOptional.isPresent()) {
                Users user = usersOptional.get();
                if (txnAmount <= user.getTxnlimit()) {
                    if (!fundTransferTransaction.getCreatedBy().equals(principal.getName())) {
                        String command = commandsGenerator.genFundTransferTransactionCommand(fundTransferTransaction);
                        log.info("Generated Fund Transfer Command\n" + command);
                        transactionStatus = telnetConnectionComponent.telnetConnectionAndResponseForFundTransfer(principal.getName(), fundTransferTransaction, command);
                        if (transactionStatus) {
                            fundTransferTransaction.setAuthorizedBy(principal.getName());
                            fundTransferTransaction.setAuthorized(1);
                            fundTransferTransactionRepository.save(fundTransferTransaction);
                        }
                    }
                }
                else{
                    throw  new CustomValidationException(Collections.singletonList(ErrorMessageConstants.USER_TXN_LIMIT_EXCEEDED +user.getTxnlimit()));
                }
            }
        }
        return transactionStatus;
    }

	/**
	 * Transaction inside core-banking system is performed after authorization
	 * Steps: 1. get command 2. send command with input data 3. get response 4.
	 * parse response 5. check response status -1 or 1 and return true or false
	 *
	 * @param digiTransactionId
	 * @param principal
	 * @return
	 */
	@Override
	public ResponseEntity<Messages> authorizeFundTransferTransaction(String digiTransactionId, Principal principal) {
		List<String> roles = utilitiesService.currentUserRoles(principal);
		if (roles.contains(RolesEnum.AUTHORIZER.getName())) {
			if (digiTransactionId != null) {
				boolean telnetResponse = getTelnetFundTransferTransaction(digiTransactionId, principal);
				if (telnetResponse) {
					return new ResponseEntity<>(HttpResponses.received(), HttpStatus.ACCEPTED);
				} else {
					return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
				}
			}
		}
		return new ResponseEntity<>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
	}

	public Optional<FundTransferTransaction> findByDigiTransactionId(String digiTransactionId) {
		return fundTransferTransactionRepository.findByDigiTransactionId(digiTransactionId);
	}

}
