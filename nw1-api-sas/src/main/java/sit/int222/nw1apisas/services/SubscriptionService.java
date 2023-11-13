package sit.int222.nw1apisas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sit.int222.nw1apisas.dtos.subscriptions.SubAndUnSubReq;
import sit.int222.nw1apisas.entities.Announcement;
import sit.int222.nw1apisas.entities.Category;
import sit.int222.nw1apisas.entities.Subscription;
import sit.int222.nw1apisas.entities.User;
import sit.int222.nw1apisas.exceptions.ItemNotFoundException;
import sit.int222.nw1apisas.repositories.SubscriptionRepository;
import sit.int222.nw1apisas.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private JavaMailSender javaMailSender;
    private static final int OTP_LENGTH = 6;

    private String generateOTP() {
        String numbers = "0123456789";
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = (int) (numbers.length() * Math.random());
            otp.append(numbers.charAt(index));
        }
        return otp.toString();
    }


    public String subscribeCategoryAndSendOtp(SubAndUnSubReq subAndUnSubReq) {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        if (subscriptions.stream().anyMatch(subscription -> subscription.getCategoryId().getCategoryId().equals(subAndUnSubReq.getCategoryId())
                && subscription.getEmailSubscription().equals(subAndUnSubReq.getEmail()))) {
            String subject = "You have been already subscribed category name: " + categoryService.getCategoryById(subAndUnSubReq.getCategoryId()).getCategoryName();
            String body = "You are subscribing category name: " + "\n - " + categoryService.getCategoryById(subAndUnSubReq.getCategoryId()).getCategoryName();
            mailSender(subAndUnSubReq.getEmail(), subject, body);
            System.out.println("Mail successfully sent");
            return "You have been already subscribed";
        }
        String otp = generateOTP();
        createSubscription(subAndUnSubReq.getEmail(), subAndUnSubReq.getCategoryId(), otp);
        String subject = "OTP";
        String body = "OTP is: " + otp;
        mailSender(subAndUnSubReq.getEmail(), subject, body);
        System.out.println("OTP successfully sent");

        return "Subscription is successfully";
    }

    public String verifyOTP(String email, String enteredOTP) {
        Subscription subscription = subscriptionRepository.findByEmailSubscriptionAndOtp(email, enteredOTP);
        // เช็ค otp ที่รับเข้ามากับที่มีอยู่
        if (enteredOTP.equals(subscription.getOtp())) {
            List<String> subscribedCategories = getSubscribedCategories(email);
            String subject = "Subscription is successful";
            String body = "You just subscribed to the following categories:\n";
            for (String category : subscribedCategories) {
                body += "- " + category + "\n";
            }
            mailSender(email, subject, body);
            System.out.println("Subscription is successfully");
            return "Subscription is successfully";
        }
        return "OTP is not valid";
    }


    public void createSubscription(String email, Integer categoryId, String otp) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Subscription subscription = new Subscription();
        Optional<User> user = userRepository.findUserByUsername(authentication.getName());
        if (user.isPresent()) {
            subscription.setUserId(user.get());
        }
        subscription.setOtp(otp);
        subscription.setEmailSubscription(email);
        subscription.setCategoryId(categoryService.getCategoryById(categoryId));
        subscriptionRepository.saveAndFlush(subscription);
        System.out.println("Save");
    }

    private List<String> getSubscribedCategories(String email) {
        List<String> subscribedCategories = new ArrayList<>();
        List<Subscription> subscriptions = subscriptionRepository.findAllByEmailSubscription(email);
        for (Subscription subscription : subscriptions) {
            if (subscription.getCategoryId() != null) {
                subscribedCategories.add(subscription.getCategoryId().getCategoryName());
            }
        }
        return subscribedCategories;
    }

    public void mailSender(String email, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nw1chowder@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
        System.out.println("Mail successfully sent");
    }

    public String unsubscribeCategory(SubAndUnSubReq subAndUnSubReq) {
        Category category = categoryService.getCategoryById(subAndUnSubReq.getCategoryId());
        if (category == null) {
            throw new ItemNotFoundException("Can't find this category");
        }
        Subscription subscription = subscriptionRepository.findByCategoryIdAndEmailSubscription(category,
                subAndUnSubReq.getEmail());
        System.out.println("Here");
        if (subscription != null) {
            subscriptionRepository.deleteById(subscription.getId());
            String subject = "Unsubscribe category name: " + category.getCategoryName();
            String body = "You unsubscribe category name: " + category.getCategoryName() + " is successfully";
            mailSender(subAndUnSubReq.getEmail(), subject, body);
            return "Unsubscription is successfully";
        }
        throw new ItemNotFoundException("Can't find this subscription");
    }

    public void sendEmailToSubscribers(Announcement announcement) {
        String announcementLink = "https://intproj22.sit.kmutt.ac.th/nw1/announcement/" + announcement.getId();
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        if (subscriptions.isEmpty()) {
            System.out.println("No subscribers for the category. Announcement not sent.");
            return;
        }
        String subject = announcement.getAnnouncementTitle();
        String body = announcement.getAnnouncementDescription() + "\n\n"
                + "Link to Announcement: " + "\n" + announcementLink;
        for (Subscription subscription : subscriptions) {
            String subscriberEmail = subscription.getEmailSubscription();
            mailSender(subscriberEmail, subject, body);
            System.out.println("Mail successfully sent to " + subscriberEmail);
        }
    }

}
