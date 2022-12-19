package com.example.emailgaxabaryuborish.service;

import com.example.emailgaxabaryuborish.entity.Enum.Lavozimlar;
import com.example.emailgaxabaryuborish.entity.Users;
import com.example.emailgaxabaryuborish.payload.ApiResponse;
import com.example.emailgaxabaryuborish.payload.UserDto;
import com.example.emailgaxabaryuborish.repository.LavozimRepository;
import com.example.emailgaxabaryuborish.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static org.apache.coyote.http11.Constants.a;
import static org.hibernate.cfg.AvailableSettings.USER;

@Service
public class UserService {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LavozimRepository lavozimRepository;
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
}
