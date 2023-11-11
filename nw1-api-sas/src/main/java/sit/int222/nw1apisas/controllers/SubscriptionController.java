package sit.int222.nw1apisas.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int222.nw1apisas.dtos.subscriptions.SubscriptionRequest;
import sit.int222.nw1apisas.services.SubscriptionService;

@RestController
@RequestMapping("/api/subscription")
@CrossOrigin(origins = {"http://ip22nw1.sit.kmutt.ac.th", "http://intproj22.sit.kmutt.ac.th", "https://intproj22.sit.kmutt.ac.th", "http://localhost:5173"})
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public String subscribeCategory(@RequestBody SubscriptionRequest subscriptionRequest) {
        return subscriptionService.subscribeCategory(subscriptionRequest);
    }

    @DeleteMapping("/unsubscribe")
    public String unSubscribeCategory(@RequestParam("email") String email,
                                      @RequestParam("categoryId") Integer categoryId) {
        return subscriptionService.unsubscribeCategory(email, categoryId);
    }





//    @GetMapping("")
//    public String helloTest() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("nw1chowder@gmail.com");
//        message.setTo("31970@stw.ac.th");
//        message.setSubject("Subject: Test Email Subject");
//        message.setText("Body: This is body for sample mail");
//
//        javaMailSender.send(message);
//        System.out.println("Mail successfully sent");
//        return "Mail successfully sent";
//    }

}
