package com.example.javamail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * @author Ikechi Ucheagwu
 * @created 10/12/2022 - 19:50
 * @project JavaMail
 */

@RestController
public class JavaMailController {

    @Autowired
    JavaMailService javaMailService;

    @PostMapping("/send/{email}")
    public ResponseEntity<String> sendMail(@PathVariable String email) {
        return javaMailService.sendMail(email);
    }


}
