package sit.int222.nw1apisas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sit.int222.nw1apisas.dtos.subscriptions.SubscriptionRequest;
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
        if(user.isPresent()){
            subscription.setUserId(user.get());
        }
        subscription.setEmailSubscription(email);
        subscription.setCategoryId(categoryService.getCategoryById(categoryId));
        subscriptionRepository.saveAndFlush(subscription);
        System.out.println("Save");
    }



    public String subscribeCategory(SubscriptionRequest subscriptionRequest) {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nw1chowder@gmail.com");
        if (subscriptions.stream().anyMatch(subscription -> subscription.getCategoryId().getCategoryId().equals(subscriptionRequest.getCategoryId())
                && subscription.getUserId().getEmail().equals(subscriptionRequest.getEmail()))) {
            message.setTo(subscriptionRequest.getEmail());
            message.setSubject("You have been already subscribed category named: " + categoryService.getCategoryById(subscriptionRequest.getCategoryId()).getCategoryName());
            message.setText("You are subscribing category named: " + categoryService.getCategoryById(subscriptionRequest.getCategoryId()).getCategoryName());
            javaMailSender.send(message);
            System.out.println("Mail successfully sent");
            return "Mail successfully sent";
        }else {
            createSubscription(subscriptionRequest.getEmail(), subscriptionRequest.getCategoryId());
            message.setTo(subscriptionRequest.getEmail());
            message.setSubject("abc " + categoryService.getCategoryById(subscriptionRequest.getCategoryId()).getCategoryName());
            message.setText("abc" + categoryService.getCategoryById(subscriptionRequest.getCategoryId()).getCategoryName());
            javaMailSender.send(message);
            System.out.println("Subscription is successfully");
            return "Subscription is successfully";
        }
    }

}
