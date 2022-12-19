package com.example.emailgaxabaryuborish.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(min = 3,max = 15)
    @Column(nullable = false,length = 15)
    private String ism;
    @Length(min = 6,max = 20)
    @Column(nullable = false)
    private String familiya;
    @Column(nullable = false,unique = true)
    private String telNomer;
    @Email
    @Column(nullable = false,unique = true)
    private String username;
    @Length(min = 8,max = 72)
    @Column(nullable = false,length = 72)
    private String password;
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp yaratilganVaqt;
    @UpdateTimestamp
    private Timestamp tahrirlanganVaqt;
    @ManyToOne
    private Lavozim lavozim;
    @Column(nullable = false)
    private String emailCode;
    private boolean accountNonExpired=true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;
    private boolean enabled=false;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(lavozim);
    }

}
