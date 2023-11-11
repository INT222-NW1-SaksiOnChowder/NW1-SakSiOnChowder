package sit.int222.nw1apisas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sit.int222.nw1apisas.dtos.subscriptions.SubscriptionRequest;
import sit.int222.nw1apisas.entities.Announcement;
import sit.int222.nw1apisas.entities.Category;
import sit.int222.nw1apisas.entities.Subscription;
import sit.int222.nw1apisas.entities.User;
import sit.int222.nw1apisas.repositories.SubscriptionRepository;
import sit.int222.nw1apisas.repositories.UserRepository;

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

    public void createSubscription(String email, Integer categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Subscription subscription = new Subscription();
        Optional<User> user = userRepository.findUserByUsername(authentication.getName());
//        Check user object is present, then set user in the subscription
        if (user.isPresent()) {
            subscription.setUserId(user.get());
        }
        subscription.setEmailSubscription(email);
        subscription.setCategoryId(categoryService.getCategoryById(categoryId));
        subscriptionRepository.saveAndFlush(subscription);
        System.out.println("Save");
    }


    public String subscribeCategory(SubscriptionRequest subscriptionRequest) {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        if (subscriptions.stream().anyMatch(subscription -> subscription.getCategoryId().getCategoryId().equals(subscriptionRequest.getCategoryId())
                && subscription.getUserId().getEmail().equals(subscriptionRequest.getEmail()))) {
            String subject = "You have been already subscribed category named: " + categoryService.getCategoryById(subscriptionRequest.getCategoryId()).getCategoryName();
            String body = "You are subscribing category named: " + categoryService.getCategoryById(subscriptionRequest.getCategoryId()).getCategoryName();
            mailSender(subscriptionRequest.getEmail(), subject, body);
            System.out.println("Mail successfully sent");
            return "Mail successfully sent";
        }
        createSubscription(subscriptionRequest.getEmail(), subscriptionRequest.getCategoryId());
        String subject = "abc " + categoryService.getCategoryById(subscriptionRequest.getCategoryId()).getCategoryName();
        String body = "abc" + categoryService.getCategoryById(subscriptionRequest.getCategoryId()).getCategoryName();
        mailSender(subscriptionRequest.getEmail(), subject, body);
        System.out.println("Subscription is successfully");
        return "Subscription is successfully";

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

    public String unsubscribeCategory(String email, Integer categoryId) {
        Subscription subscription = subscriptionRepository.findByCategoryIdAndEmailSubscription(email, categoryId);
        System.out.println(subscription);
        return "Unsubscribe successfully";
    }

}
