package com.example.emailgaxabaryuborish.service;

import com.example.emailgaxabaryuborish.entity.Enum.Lavozimlar;
import com.example.emailgaxabaryuborish.entity.Users;
import com.example.emailgaxabaryuborish.payload.ApiResponse;
import com.example.emailgaxabaryuborish.payload.LoginDto;
import com.example.emailgaxabaryuborish.payload.UserDto;
import com.example.emailgaxabaryuborish.repository.LavozimRepository;
import com.example.emailgaxabaryuborish.repository.UserRepository;
import com.example.emailgaxabaryuborish.token.TokenGenerator;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static org.apache.coyote.http11.Constants.a;
import static org.hibernate.cfg.AvailableSettings.USER;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LavozimRepository lavozimRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenGenerator tokenGenerator;
    public ApiResponse UserAdd(UserDto userDto) {
        boolean b = userRepository.existsByUsername(userDto.getUsername());
        if(b){
            return new ApiResponse("Bunday foydalanuvchi mavjud",false);
        }
        Users users=new Users();
        users.setIsm(userDto.getIsm());
        users.setFamiliya(userDto.getFamiliya());
        users.setTelNomer(userDto.getTelnomer());
        users.setUsername(userDto.getUsername());
        users.setPassword(passwordEncoder.encode(userDto.getPassword()));
        users.setLavozim(lavozimRepository.findByLavozimlar(Lavozimlar.USER));
        String emailcode=UUID.randomUUID().toString().substring(0,6);
        users.setEmailCode(emailcode);
        if(xabarYuborish(userDto.getUsername(),emailcode)){
            userRepository.save(users);
            return new ApiResponse("Ro'yhatdan muvaffaqiyatli o'tdingiz! Eletkron pochtangizni faollashtirish kodi yuborildi",true);
        }
       return new ApiResponse("Elektron pochtangizda xatolik mavjud",false);

    }
    public boolean xabarYuborish(String email,String emailcode){
        try{
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setFrom("axrorovakmal4@gmail.com");
            simpleMailMessage.setSubject("Tasdiqlash kodi");
            simpleMailMessage.setText("<a href='http://localhost:8080/users/tasdiqlash?email="+email+"&emailcode="+emailcode+"'>Email tasdiqlash</a>");
            mailSender.send(simpleMailMessage);
            return true;
        }
        catch (Exception ex){
            ex.getStackTrace();
            return false;
        }
    }

    public ApiResponse userConfirm(String email, String emailcode) {
       // System.out.println(email+"  "+emailcode);
        Optional<Users> byUsernameAndEmailCode = userRepository.findByUsernameAndEmailCode(email, emailcode);
        if(byUsernameAndEmailCode.isPresent()){
            Users users = byUsernameAndEmailCode.get();
            users.setEnabled(true);
            users.setEmailCode(null);
            userRepository.save(users);
            return new ApiResponse("Profilingiz faollashtirildi",true);
        }
        return new ApiResponse("Profillingiz allaqachon faollashtirilgan", false);
    }

    public ApiResponse login(LoginDto loginDto) {
    //    boolean b = userRepository.existsByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        if(authenticate.isAuthenticated()){
            Optional<Users> byUsernameAndEmailCode = userRepository.findByUsernameAndEmailCode(loginDto.getUsername(), null);
            if(byUsernameAndEmailCode.isPresent()){
                Users principal = (Users) authenticate.getPrincipal();

                return new ApiResponse("Profilingizga xush kelibsiz"+tokenGenerator.getToken(principal.getUsername()),true);

            }
            return new ApiResponse("Profillingiz faollashtirilmagan",false);
        }
        return new ApiResponse("Login yoki parol xato",false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> byUsername = userRepository.findByUsername(username);
        if(byUsername.isPresent()){
            return byUsername.get();
        }
        throw new UsernameNotFoundException("Bunday foydalanuvchi mavjud emas");

    }
}
