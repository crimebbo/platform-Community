package com.osci.kr.itda.repo;import com.osci.kr.itda.entity.sns.GoogleUser;import org.springframework.data.jpa.repository.JpaRepository;import java.util.Optional;public interface GoogleUserRepo extends JpaRepository<GoogleUser, Long> {    Optional<GoogleUser> findByUserid(String userid);}