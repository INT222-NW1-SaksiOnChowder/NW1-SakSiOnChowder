package sit.int222.nw1apisas.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int222.nw1apisas.config.JwtTokenUtil;
import sit.int222.nw1apisas.dtos.subscriptions.SubRequest;
import sit.int222.nw1apisas.entities.Announcement;
import sit.int222.nw1apisas.entities.Category;
import sit.int222.nw1apisas.entities.Subscription;
import sit.int222.nw1apisas.exceptions.ItemNotFoundException;
import sit.int222.nw1apisas.exceptions.UnAuthorizationException;
import sit.int222.nw1apisas.properties.JwtProperties;
import sit.int222.nw1apisas.repositories.SubscriptionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtProperties jwtProperties;

    private String generateOTP() {
        String numbers = "0123456789";
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (numbers.length() * Math.random());
            otp.append(numbers.charAt(index));
        }
        return otp.toString();
    }


    public String subscribeCategoryAndSendOtp(SubRequest subRequest) throws MessagingException {
        String trimmedEmail = subRequest.getEmail().trim();
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        if (subscriptions.stream().anyMatch(subscription -> subscription.getCategoryId().getCategoryId().equals(subRequest.getCategoryId())
                && subscription.getSubscriberEmail().equals(trimmedEmail))) {
            String subject = "You have been already subscribed category name: " + categoryService.getCategoryById(subRequest.getCategoryId()).getCategoryName();
            String body = "You are subscribing category name: " + "\n" + " - " + categoryService.getCategoryById(subRequest.getCategoryId()).getCategoryName();
            mailSender(trimmedEmail, subject, body);
            System.out.println("Mail successfully sent");
            return "You have been already subscribed";
        }

        String otp = generateOTP();
        String subject = "OTP";
        String body = "OTP is: " + otp;
        mailSender(trimmedEmail, subject, body);
        System.out.println("Sent OTP successfully");

        return jwtTokenUtil.generateOtpToken(trimmedEmail, otp, subRequest.getCategoryId());
    }

    public String verifyOTP(String otpToken, String enteredOTP) throws MessagingException {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(otpToken).getBody();
        } catch (Exception e) {
            return "Invalid OtpToken";
        }
        String trimmedEmail = claims.getSubject();
        Integer categoryId = claims.get("categoryId", Integer.class);
        // Verify the entered OTP
        if (enteredOTP.equals(claims.get("otp"))) {
            createSubscription(trimmedEmail, categoryId);
            List<String> subscribedCategories = getSubscribedCategories(trimmedEmail);
            String subject = "Subscription is successful";
            String body = "You just subscribed to the following categories:\n";
            for (String category : subscribedCategories) {
                body += "- " + category + "\n";
            }
            mailSender(trimmedEmail, subject, body);
            System.out.println("Subscription is successfully");
            return "OTP verification successful";
        } else {
            return "OTP verification failed";
        }
    }


    public void createSubscription(String trimmedEmail, Integer categoryId) {
        Subscription subscription = new Subscription();
        subscription.setSubscriberEmail(trimmedEmail);
        subscription.setCategoryId(categoryService.getCategoryById(categoryId));
        subscriptionRepository.saveAndFlush(subscription);
        System.out.println("Create Subscription Successful");
    }

    private List<String> getSubscribedCategories(String trimmedEmail) {
        List<String> subscribedCategories = new ArrayList<>();
        List<Subscription> subscriptions = subscriptionRepository.findAllBySubscriberEmailOrderByCategoryIdAsc(trimmedEmail);
        for (Subscription subscription : subscriptions) {
            subscribedCategories.add(subscription.getCategoryId().getCategoryName());
        }
        return subscribedCategories;
    }

    public void mailSender(String trimmedEmail, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setFrom("nw1chowder@gmail.com");
        mimeMessageHelper.setTo(trimmedEmail);
        mimeMessageHelper.setSubject(subject);

        // Set HTML content directly without additional tags
        mimeMessageHelper.setText(body, true);

        // Send the email
        javaMailSender.send(mimeMessage);
        System.out.println("Mail successfully sent");
    }


    public void sendNewAnnouncementToSubscribers(Announcement announcement) throws MessagingException {
        System.out.println("Send function is work " + announcement.getAnnouncementTitle());
        String localLink = "http://localhost:5173/announcement/";
        String sasLink = "https://intproj22.sit.kmutt.ac.th/nw1/announcement/";
        String announcementLink = "https://intproj22.sit.kmutt.ac.th/nw1/announcement/" + announcement.getId();
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        if (subscriptions.isEmpty()) {
            System.out.println("No subscribers for the category. Announcement not sent.");
            return;
        }
        String subject = announcement.getAnnouncementTitle();
        String body = announcement.getAnnouncementDescription() + "\n\n"
                + "Announcement link : " + "\n" + announcementLink + "\n\n";
        System.out.println(subscriptions.size());
        for (Subscription subscription : subscriptions) {
            if (subscription.getCategoryId().getCategoryId().equals(announcement.getCategoryId().getCategoryId())) {
                System.out.println("Here");
                String subscriberEmail = subscription.getSubscriberEmail();

                String tokenLink = jwtTokenUtil.generateSecureLink(subscriberEmail, subscription.getCategoryId().getCategoryId());
                String unSubScribeLink = localLink + "unsubscription?token=" + tokenLink;

                body += "To unsubscribe: \n" +
                        "If you no longer wish for " + subscriberEmail + " to receive any email announcement messages from SAS, please click the following link "
                        + unSubScribeLink;
                mailSender(subscriberEmail, subject, body);
                System.out.println("Mail successfully sent to " + subscriberEmail);
            }
        }
    }

    public String unsubscribeCategory(String unsubToken) throws MessagingException {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(unsubToken).getBody();
        } catch (Exception e) {
           throw new UnAuthorizationException(e.getMessage(), "token");
        }
        String email = claims.getSubject();
        Integer categoryId = claims.get("categoryId", Integer.class);
        Category category = categoryService.getCategoryById(categoryId);
        Subscription subscription = subscriptionRepository.findByCategoryIdAndSubscriberEmail(category, email);
        if (subscription != null) {
            subscriptionRepository.deleteById(subscription.getId());
            String subject = "Unsubscribe category name: " + category.getCategoryName();
            String body = "You unsubscribe category name: " + category.getCategoryName() + " is successfully";
            mailSender(email, subject, body);
            return "Unsubscription is successfully";
        }
        throw new ItemNotFoundException("Can't find this subscription");
    }

//    public String unsubscribeCategory(SubRequest subRequest) {
//        String trimmedEmail = subRequest.getEmail().trim();
//        Category category = categoryService.getCategoryById(subRequest.getCategoryId());
//        if (category == null) {
//            throw new ItemNotFoundException("Can't find this category");
//        }
//        Subscription subscription = subscriptionRepository.findByCategoryIdAndSubscriberEmail(category,
//                trimmedEmail);
//        System.out.println("Here");
//        if (subscription != null) {
//            subscriptionRepository.deleteById(subscription.getId());
//            String subject = "Unsubscribe category name: " + category.getCategoryName();
//            String body = "You unsubscribe category name: " + category.getCategoryName() + " is successfully";
//            mailSender(trimmedEmail, subject, body);
//            return "Unsubscription is successfully";
//        }
//        throw new ItemNotFoundException("Can't find this subscription");
//    }


}
