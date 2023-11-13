package sit.int222.nw1apisas.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int222.nw1apisas.dtos.subscriptions.SubAndUnSubReq;
import sit.int222.nw1apisas.dtos.subscriptions.VerifyOtpReq;
import sit.int222.nw1apisas.services.SubscriptionService;

@RestController
@RequestMapping("/api/subscription")
@CrossOrigin(origins = {"http://ip22nw1.sit.kmutt.ac.th", "http://intproj22.sit.kmutt.ac.th", "https://intproj22.sit.kmutt.ac.th", "http://localhost:5173"})
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public String subscribeCategory(@RequestBody SubAndUnSubReq subAndUnSubReq) {
        return subscriptionService.subscribeCategoryAndSendOtp(subAndUnSubReq);
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestBody VerifyOtpReq verifyOtpReq) {
        return subscriptionService.verifyOTP(verifyOtpReq.getEmail(), verifyOtpReq.getOtp());
    }

    @DeleteMapping("/unsubscribe")
    public String unSubscribeCategory(@RequestBody SubAndUnSubReq subAndUnSubReq) {
        return subscriptionService.unsubscribeCategory(subAndUnSubReq);
    }

}
