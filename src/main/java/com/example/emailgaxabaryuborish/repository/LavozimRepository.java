package com.example.emailgaxabaryuborish.repository;

import com.example.emailgaxabaryuborish.entity.Enum.Lavozimlar;
import com.example.emailgaxabaryuborish.entity.Lavozim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LavozimRepository extends JpaRepository<Lavozim,Integer> {
    Lavozim findByLavozimlar(Lavozimlar lavozimlar);
}
