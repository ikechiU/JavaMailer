# Guide to sending email with JavaMailer using SpringBoot with your GMAIL

# Getting Started
1. Add the Java mail dependency in your pom.xml

         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
         </dependency>

2. Optional but important add the dotenv dependency in your pom.xml

         <dependency>
            <groupId>io.github.cdimascio</groupId>
            <artifactId>dotenv-java</artifactId>
            <version>2.3.1</version>
         </dependency>

3. Optional but important create a .env file in the project root folder and add the details below in it. Once done, open .gitignore file and add .env (i.e. write .env)
         
         EMAIL_ADDRESS=yourgmail@gmail.com
         PASSWORD=your-password

4. Create a Bean of JavaMailSender (if you choose this option skip no. 5. This is the option i will use) or do add it in your application.properties file (5)

         @Bean
         public JavaMailSender getJavaMailSender() {
            Dotenv dotenv = Dotenv.load(); 
            String email = dotenv.get("EMAIL_ADDRESS"); //you can add your email here if you did not set up .env file
            String password = dotenv.get("PASSWORD");   //you can add your password here if you did not set up .env file

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587);
            mailSender.setUsername(email);
            mailSender.setPassword(password);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            return mailSender;
         }

5. Application.properties file if you do not want to create a Bean of JavaMailerSender

         spring.mail.host=smtp.gmail.com
         spring.mail.port=587
         spring.mail.username=yourgmail@gmail.com
         spring.mail.password=yourpassword@gmail.com
         spring.mail.properties.mail.smtp.auth=true
         spring.mail.properties.mail.smtp.starttls.enable=true

6. The last step is to send email:
      
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("toemail@email.com");
        message.setSentDate(new Date());
        message.setSubject("Your subject");
        message.setText("Your message body");

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
7. If you experience this error: 

         https://support.google.com/mail/?p=BadCredentials
8. This is because Google allows you to generate specific credentials for apps. This is superior to using 'your own' account credentials. This means you will need to enable a two factor verification and create app specific password.

9. Follow this guide [How to Create App-Specific Passwords in Gmail](https://www.lifewire.com/get-a-password-to-access-gmail-by-pop-imap-2-1171882#:~:text=Create%20a%20Gmail%20Application%2DSpecific%20Password&text=Select%20your%20profile%20icon%20in,confirm%20your%20Gmail%20login%20credentials.)


### Reference Documentation
1. [Guide to Spring Email](https://www.baeldung.com/spring-email)
2. [How to Create App-Specific Passwords in Gmail](https://www.lifewire.com/get-a-password-to-access-gmail-by-pop-imap-2-1171882#:~:text=Create%20a%20Gmail%20Application%2DSpecific%20Password&text=Select%20your%20profile%20icon%20in,confirm%20your%20Gmail%20login%20credentials.)


