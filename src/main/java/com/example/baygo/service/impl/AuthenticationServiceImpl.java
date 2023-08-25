package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.AuthenticateRequest;
import com.example.baygo.db.dto.request.BuyerRegisterRequest;
import com.example.baygo.db.dto.request.SellerRegisterRequest;
import com.example.baygo.db.dto.response.AuthenticationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.AlreadyExistException;
import com.example.baygo.db.exceptions.BadCredentialException;
import com.example.baygo.db.exceptions.BadRequestException;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.db.model.Seller;
import com.example.baygo.db.model.User;
import com.example.baygo.db.model.enums.Role;
import com.example.baygo.repository.BuyerRepository;
import com.example.baygo.repository.SellerRepository;
import com.example.baygo.repository.UserRepository;
import com.example.baygo.service.AuthenticationService;
import com.example.baygo.service.EmailService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final BuyerRepository buyerRepository;

    private final UserRepository userInfoRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TemplateEngine templateEngine;
    private final EmailService emailService;
    private final SellerRepository sellerRepository;


    @Override
    public AuthenticationResponse buyerRegister(BuyerRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            log.error(String.format("Пользователь с адресом электронной почты %s уже существует", request.email()));
            throw new AlreadyExistException(String.format("Пользователь с адресом электронной почты %s уже существует", request.email()));
        }
        String split = request.email().split("@")[0];
        if (split.equals(request.password())) {
            throw new BadRequestException("Создайте более надежный пароль");
        }

        Buyer buyer = new Buyer();
        buyer.setFullName(request.fullName());

        int confirmationCode = generateConfirmationCode();

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.BUYER)
                .confirmCode(confirmationCode)
                .isVerify(false)
                .build();
        buyer.setUser(user);

        confirmCodeSending(confirmationCode, user.getEmail());

        buyerRepository.save(buyer);
        log.info(String.format("Пользователь %s успешно сохранен!", user.getEmail()));

        return AuthenticationResponse.builder()
                .message("теперь вы должны пройти проверку")
                .build();
    }

    @Override
    public AuthenticationResponse sellerRegister(SellerRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            log.error(String.format("Продавец с адресом электронной почты %s уже существует", request.email()));
            throw new AlreadyExistException(String.format("Продавец с адресом электронной почты %s уже существует", request.email()));
        }
        String split = request.email().split("@")[0];
        if (split.equals(request.password())) {
            throw new BadRequestException("Создайте более надежный пароль");
        }

        int confirmationCode = generateConfirmationCode();

        User user = User.builder()
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .password(passwordEncoder.encode(request.password()))
                .isVerify(false)
                .confirmCode(confirmationCode)
                .role(Role.SELLER)
                .build();

        Seller seller = Seller.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .ITN(request.ITN())
                .address(request.address())
                .BIC(request.BIC())
                .nameOfStore(request.nameOfStore())
                .vendorNumber(UUID.randomUUID().toString().substring(0, 6).toUpperCase())
                .user(user)
                .build();

        sellerRepository.save(seller);
        log.info(String.format("Пользователь %s успешно сохранен!", user.getEmail()));

        confirmCodeSending(confirmationCode, user.getEmail());

        return AuthenticationResponse.builder()
                .message("теперь вы должны пройти проверку")
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticateRequest request) {

        User userInfo = userInfoRepository.findByEmail(request.email())
                .orElseThrow(() -> {
                    log.error(String.format("Пользователь с адресом электронной почты %s не существует", request.email()));
                    throw new BadCredentialException(String.format("Пользователь с адресом электронной почты %s не существует", request.email()));
                });
        if (!passwordEncoder.matches(request.password(), userInfo.getPassword())) {
            log.error("Пароль не подходит");
            throw new BadRequestException("Пароль не подходит");
        }

        if (userInfo.isVerify()){
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
            log.info(String.format("Пользователь %s успешно аутентифицирован", userInfo.getEmail()));
            String token = jwtService.generateToken(userInfo);

            return AuthenticationResponse.builder()
                    .email(userInfo.getEmail())
                    .role(userInfo.getRole())
                    .token(token)
                    .build();
        }
        return AuthenticationResponse.builder()
                .message(String.format("Пользователь с адресом электронной почты %s следует подтвердить учетную запись", request.email()))
                .build();
    }

    @Override
    public SimpleResponse forgotPassword(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error(String.format("Пользователь с адресом электронной почты %s не существует", email));
                    throw new NotFoundException(String.format("Пользователь с адресом электронной почты %s не существует", email));
                });
        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);

        String subject = "Запрос на сброс пароля";
        String resetPasswordLink = "http://localhost:3000/reset-password?token=" + token;

        Context context = new Context();
        context.setVariable("baygo", "https://baygo.s3.us-east-2.amazonaws.com/1692961926031Снимок экрана 2023-08-25 в 16.53.24.png");
        context.setVariable("title", "Восстановление пароля");
        context.setVariable("message", "Пожалуйста, нажмите на ссылку ниже для сброса пароля!");
        context.setVariable("token", resetPasswordLink);
        context.setVariable("tokenTitle", "Восстановление пароля");

        String htmlContent = templateEngine.process("reset-password.html", context);

        emailService.sendEmail(email, subject, htmlContent);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Сброс пароля был отправлен на вашу электронную почту. Пожалуйста, проверьте свою электронную почту.")
                .build();
    }

    @Override
    public SimpleResponse resetPassword(String token, String newPassword) {
        User user = userInfoRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> {
                    log.error("Пользователь не существует");
                    throw new NotFoundException("Пользователь не существует");
                });

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        log.info("Пароль пользователя успешно изменен!");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Пароль пользователя успешно изменен!")
                .build();
    }

    @Override
    public AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);
        if (!userRepository.existsByEmail(firebaseToken.getEmail())) {
            User user = User.builder()
                    .email(firebaseToken.getEmail())
                    .password(passwordEncoder.encode(UUID.randomUUID()
                            .toString().substring(0, 6).toUpperCase()))
                    .role(Role.BUYER)
                    .build();
            Buyer buyer = Buyer.builder()
                    .fullName(firebaseToken.getName())
                    .user(user)
                    .build();

            buyerRepository.save(buyer);
            log.info(String.format("Пользователь %s успешно сохранен!", user.getEmail()));
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .email(user.getEmail())
                    .role(user.getRole())
                    .token(jwtToken)
                    .build();
        } else {
            User user = userRepository.findByEmail(firebaseToken.getEmail())
                    .orElseThrow(() -> {
                        log.error(
                                String.format("Пользователь с адресом электронной почты %s не существует",
                                        firebaseToken.getEmail()));
                        throw new NotFoundException(
                                String.format("Пользователь с адресом электронной почты %s не существует",
                                        firebaseToken.getEmail()));
                    });
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .email(user.getEmail())
                    .role(user.getRole())
                    .token(jwtToken)
                    .build();
        }
    }

    @Override
    public AuthenticationResponse confirmRegistration(String email, int code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException(String.format("Пользователь с адресом электронной почты %s не существует", email)));

        if (user.getConfirmCode() == code){

            user.setVerify(true);
            userRepository.save(user);

            String jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse.builder()
                    .email(user.getEmail())
                    .role(user.getRole())
                    .token(jwtToken)
                    .build();
        }

        return AuthenticationResponse.builder().message("неверный проверочный код").build();

    }

    @PostConstruct
    void init() {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource("firebase/baygo-392813-firebase-adminsdk-9isz0-bc25bd675f.json")
                            .getInputStream());
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(googleCredentials)
                    .build();
            log.info("successfully works the init method");
            FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
        } catch (IOException e) {
            log.error("IOException from firebase: " + e.getMessage());
        }
    }

    private int generateConfirmationCode() {
        Random random = new Random();

        return random.nextInt(1000, 9999);
    }

    private void confirmCodeSending(int code, String email){
        String subject = "Письмо для подтверждения";

        Context context = new Context();
        context.setVariable("logo", "https://baygo.s3.us-east-2.amazonaws.com/1692961926031Снимок экрана 2023-08-25 в 16.53.24.png");
        context.setVariable("text", "Это ваш проверочный код");
        context.setVariable("password", code);

        String htmlContent = templateEngine.process("message.html", context);

        emailService.sendEmail(email, subject, htmlContent);
    }
}
