package com.abs.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
	 private String recipient;
	    private String msgBody;
	    private String subject;
}
