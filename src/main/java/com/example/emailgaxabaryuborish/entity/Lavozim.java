package com.example.emailgaxabaryuborish.entity;

import com.example.emailgaxabaryuborish.entity.Enum.Lavozimlar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Lavozim implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Lavozimlar lavozimlar;
    @Override
    public String getAuthority() {
        return lavozimlar.toString();
    }
}
