package com.digi.app.statement.component;

import com.digi.app.message.HttpResponses;
import com.digi.app.message.Messages;
import com.digi.app.statement.dto.MiniStatementDto;
import com.digi.app.statement.dto.StatementDto;
import com.digi.app.statement.enums.StatementTypeEnum;
import com.digi.app.telnet.component.TelnetConnectionComponent;
import com.digi.app.util.CommandsGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StatementComponent {
    private TelnetConnectionComponent telnetConnectionComponent;
    private CommandsGenerator commandsGenerator;

    @Autowired
    public void setCommandsGenerator(CommandsGenerator commandsGenerator) {
        this.commandsGenerator = commandsGenerator;
    }

    @Autowired
    public void setTelnetConnectionComponent(TelnetConnectionComponent telnetConnectionComponent) {
        this.telnetConnectionComponent = telnetConnectionComponent;
    }

    public ResponseEntity<Messages> getMiniStatementSearchResult(String username, String accountNumber) {
        if (!StringUtils.isBlank(accountNumber)) {
            String command = commandsGenerator.genMiniStatementCommand(accountNumber);
            log.info("Generated Mini Statement Command\n" + command);
            List<MiniStatementDto> miniStatementDtoList = (List<MiniStatementDto>) telnetConnectionComponent.telnetConnectionAndResponseList(username, command, StatementTypeEnum.MINI_STATEMENT);
            if (miniStatementDtoList != null && !miniStatementDtoList.isEmpty()) {
                return new ResponseEntity<>(HttpResponses.fetched(miniStatementDtoList), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Messages> getStatementSearchResult(String username, String accountNumber, String dateFrom, String dateTo) {
        if (!StringUtils.isBlank(accountNumber) && !StringUtils.isBlank(dateFrom) && !StringUtils.isBlank(dateTo)) {
            String command = commandsGenerator.genStatementCommand(accountNumber, dateFrom, dateTo);
            log.info("Generated Statement Command\n" + command);
            List<StatementDto> statementDtoList = (List<StatementDto>) telnetConnectionComponent.telnetConnectionAndResponseList(username, command, StatementTypeEnum.STATEMENT);
            if (!statementDtoList.isEmpty()) {
                return new ResponseEntity<>(HttpResponses.fetched(statementDtoList), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpResponses.notfound(), HttpStatus.NOT_FOUND);

    }
}
