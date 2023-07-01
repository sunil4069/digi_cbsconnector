package com.digi.app.component;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digi.app.message.HttpResponses;
import com.digi.app.message.Messages;

@Service
public class CrudReturnService<T> {

	public ResponseEntity<?> controllerReturnForList(List<T> list){
		if(list!=null) {
			if(list.size()>0) {
				return new ResponseEntity<>(HttpResponses.fetched(list), HttpStatus.OK);
				}
				else {
					return new ResponseEntity<>(HttpResponses.notfound(),HttpStatus.NOT_FOUND);
				}		
			}
		else {
			return new ResponseEntity<>(HttpResponses.notfound(),HttpStatus.NOT_FOUND);
		}
	
	}
	
public ResponseEntity<Messages> updateReturn(Object object){
		
		if (object != null) {
			return new ResponseEntity<Messages>(HttpResponses.created(object), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Messages>(HttpResponses.badRequest(), HttpStatus.BAD_REQUEST);
		}
	}

}
