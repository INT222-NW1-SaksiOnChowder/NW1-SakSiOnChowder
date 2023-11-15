package sit.int222.nw1apisas.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sit.int222.nw1apisas.config.JwtTokenUtil;
import sit.int222.nw1apisas.dtos.subscriptions.SubAndUnSubReq;
import sit.int222.nw1apisas.entities.Announcement;
import sit.int222.nw1apisas.entities.Category;
import sit.int222.nw1apisas.entities.Subscription;
import sit.int222.nw1apisas.exceptions.ItemNotFoundException;
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


    public String subscribeCategoryAndSendOtp(SubAndUnSubReq subAndUnSubReq) {
        String trimmedEmail = subAndUnSubReq.getEmail().trim();
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        if (subscriptions.stream().anyMatch(subscription -> subscription.getCategoryId().getCategoryId().equals(subAndUnSubReq.getCategoryId())
                && subscription.getSubscriberEmail().equals(trimmedEmail))) {
            String subject = "You have been already subscribed category name: " + categoryService.getCategoryById(subAndUnSubReq.getCategoryId()).getCategoryName();
            String body = "You are subscribing category name: " + "\n - " + categoryService.getCategoryById(subAndUnSubReq.getCategoryId()).getCategoryName();
            mailSender(trimmedEmail, subject, body);
            System.out.println("Mail successfully sent");
            return "You have been already subscribed";
        }

        String otp = generateOTP();
        String subject = "OTP";
        String body = "OTP is: " + otp;
        mailSender(trimmedEmail, subject, body);
        System.out.println("Sent OTP successfully");

        return jwtTokenUtil.generateOtpToken(trimmedEmail, otp, subAndUnSubReq.getCategoryId());
    }

    public String verifyOTP(String otpToken, String enteredOTP) {
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
        System.out.println("Save");
    }

    private List<String> getSubscribedCategories(String trimmedEmail) {
        List<String> subscribedCategories = new ArrayList<>();
        List<Subscription> subscriptions = subscriptionRepository.findAllBySubscriberEmail(trimmedEmail);
        for (Subscription subscription : subscriptions) {
            if (subscription.getCategoryId() != null) {
                subscribedCategories.add(subscription.getCategoryId().getCategoryName());
            }
        }
        return subscribedCategories;
    }

    public void mailSender(String trimmedEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nw1chowder@gmail.com");
        message.setTo(trimmedEmail);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
        System.out.println("Mail successfully sent");
    }


    public void sendNewAnnouncementToSubscribers(Announcement announcement) {
        String announcementLink = "https://intproj22.sit.kmutt.ac.th/nw1/announcement/" + announcement.getId();
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        if (subscriptions.isEmpty()) {
            System.out.println("No subscribers for the category. Announcement not sent.");
            return;
        }
        String subject = announcement.getAnnouncementTitle();
        String body = announcement.getAnnouncementDescription() + "\n\n"
                + "Link to Announcement: " + "\n" + announcementLink + "\n\n";

        for (Subscription subscription : subscriptions) {
            String subscriberEmail = subscription.getSubscriberEmail();
            mailSender(subscriberEmail, subject, body);
            System.out.println("Mail successfully sent to " + subscriberEmail);
        }
    }


    public String unsubscribeCategory(SubAndUnSubReq subAndUnSubReq) {
        String trimmedEmail = subAndUnSubReq.getEmail().trim();
        Category category = categoryService.getCategoryById(subAndUnSubReq.getCategoryId());
        if (category == null) {
            throw new ItemNotFoundException("Can't find this category");
        }
        Subscription subscription = subscriptionRepository.findByCategoryIdAndSubscriberEmail(category,
                trimmedEmail);
        System.out.println("Here");
        if (subscription != null) {
            subscriptionRepository.deleteById(subscription.getId());
            String subject = "Unsubscribe category name: " + category.getCategoryName();
            String body = "You unsubscribe category name: " + category.getCategoryName() + " is successfully";
            mailSender(trimmedEmail, subject, body);
            return "Unsubscription is successfully";
        }
        throw new ItemNotFoundException("Can't find this subscription");
    }


}
