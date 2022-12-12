package com.example.javamail;

import org.springframework.http.ResponseEntity;

/**
 * @author Ikechi Ucheagwu
 * @created 13/12/2022 - 00:05
 * @project JavaMail
 */

public interface JavaMailService {
    ResponseEntity<String> sendMail(String receiverEmail);
}
