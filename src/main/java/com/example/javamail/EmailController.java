package com.example.javamail;

import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Pattern;

/**
 * @author Ikechi Ucheagwu
 * @created 10/12/2022 - 19:50
 * @project JavaMail
 */

@RestController
public class EmailController {

    @Autowired
    JavaMailSender javaMailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaMailApplication.class);
    private static final Marker IMPORTANT = MarkerFactory.getMarker("IMPORTANT");

    @PostMapping("/send/{email}")
    public ResponseEntity<String> sendMail(@PathVariable String email) {

        if (!isValidEmail(email))
            new ResponseEntity<>("Email is not valid", HttpStatus.BAD_REQUEST);

        isEmailDomainValid(email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSentDate(new Date());
        message.setSubject("Decapay!");
        message.setText("Welcome to decapay: kindly click this link to finish registration");

        try {
            LOGGER.info("Beginning of log *********");
            LOGGER.info(IMPORTANT, "Sending mail to: " + email);
            javaMailSender.send(message);
            return new ResponseEntity<>("Sent", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(IMPORTANT, e.getMessage());
        }

        return new ResponseEntity<>("An Error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public boolean isValidEmail(String email) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    public void isEmailDomainValid(String email) {
        try {
            String domain = email.substring(email.lastIndexOf('@') + 1);
            int result = doLookup(domain);
            LOGGER.info("Domain: " + domain);
            LOGGER.info("Result of domain: " + result);
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public int doLookup(String hostName) throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        DirContext ictx = new InitialDirContext( env );
        Attributes attrs = ictx.getAttributes( hostName, new String[] { "MX" });
        Attribute attr = attrs.get( "MX" );
        if( attr == null ) return( 0 );
        return( attr.size() );
    }
}
