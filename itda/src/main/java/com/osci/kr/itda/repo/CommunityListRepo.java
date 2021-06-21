package com.osci.kr.itda.repo;import com.osci.kr.itda.entity.Community;import com.osci.kr.itda.entity.CommunityList;import org.springframework.data.jpa.repository.JpaRepository;import java.util.List;import java.util.Optional;public interface CommunityListRepo extends JpaRepository<CommunityList, Long> {    Optional<CommunityList> findByComNm(String userid);    List<CommunityList> findByComNmLike(String comnm);}