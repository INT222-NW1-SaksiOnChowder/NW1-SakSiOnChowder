package sit.int222.nw1apisas.controllers;


import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int222.nw1apisas.config.JwtTokenUtil;
import sit.int222.nw1apisas.dtos.subscriptions.OtpTokenResponse;
import sit.int222.nw1apisas.dtos.subscriptions.SubRequest;
import sit.int222.nw1apisas.dtos.subscriptions.VerifyOtpReq;
import sit.int222.nw1apisas.services.SubscriptionService;

@RestController
@RequestMapping("/api/subscription")
@CrossOrigin(origins = {"http://ip22nw1.sit.kmutt.ac.th", "http://intproj22.sit.kmutt.ac.th", "https://intproj22.sit.kmutt.ac.th", "http://localhost:5173"})
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/subscribe")
    public OtpTokenResponse subscribeCategory(@RequestBody SubRequest subRequest) throws MessagingException{
        String token = subscriptionService.subscribeCategoryAndSendOtp(subRequest);
        OtpTokenResponse otpTokenResponse = new OtpTokenResponse();
        otpTokenResponse.setOtpToken(token);
        return otpTokenResponse;
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestHeader(name = "OtpToken") String otpToken, @RequestBody VerifyOtpReq verifyOtpReq) throws MessagingException {
        if (otpToken == null) {
            return "OTP is missing";
        }
        if (jwtTokenUtil.isTokenExpired(otpToken)) {
            return "OTP is expired";
        }
        return subscriptionService.verifyOTP(otpToken, verifyOtpReq.getOtp());
    }

    @DeleteMapping("/unsubscribe")
    public String unSubscribeCategory(@RequestHeader("UnsubToken") String unsubToken) throws MessagingException {
        return subscriptionService.unsubscribeCategory(unsubToken);
    }

}
