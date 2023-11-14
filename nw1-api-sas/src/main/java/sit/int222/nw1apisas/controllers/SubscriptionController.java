package sit.int222.nw1apisas.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int222.nw1apisas.config.JwtTokenUtil;
import sit.int222.nw1apisas.dtos.subscriptions.OtpTokenResponse;
import sit.int222.nw1apisas.dtos.subscriptions.SubAndUnSubReq;
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
    public OtpTokenResponse subscribeCategory(@RequestBody SubAndUnSubReq subAndUnSubReq) {
        String token = subscriptionService.subscribeCategoryAndSendOtp(subAndUnSubReq);
        OtpTokenResponse otpTokenResponse = new OtpTokenResponse();
        otpTokenResponse.setOtpToken(token);
        return otpTokenResponse;
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestHeader(name = "OtpToken") String otpToken, @RequestBody VerifyOtpReq verifyOtpReq) {
        if (otpToken == null) {
            return "OTP is missing";
        }
        if (jwtTokenUtil.isTokenExpired(otpToken)) {
            return "OTP is expired";
        }
        return subscriptionService.verifyOTP(otpToken, verifyOtpReq.getOtp());
    }

    @DeleteMapping("/unsubscribe")
    public String unSubscribeCategory(@RequestBody SubAndUnSubReq subAndUnSubReq) {
        return subscriptionService.unsubscribeCategory(subAndUnSubReq);
    }

}
