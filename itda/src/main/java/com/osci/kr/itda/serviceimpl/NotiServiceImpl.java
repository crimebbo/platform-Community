package com.osci.kr.itda.serviceimpl;import com.osci.kr.itda.common.exception.ItDaExceptionHandler;import com.osci.kr.itda.common.model.ItDaResult;import com.osci.kr.itda.entity.Board;import com.osci.kr.itda.entity.BoardComment;import com.osci.kr.itda.entity.Member;import com.osci.kr.itda.entity.Noti;import com.osci.kr.itda.repo.InterestRopo;import com.osci.kr.itda.repo.NotiRepo;import com.osci.kr.itda.service.CommunityService;import com.osci.kr.itda.service.NotiService;import lombok.AllArgsConstructor;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import java.time.LocalDateTime;import java.util.HashMap;import java.util.List;import java.util.Optional;@Service@Transactional@AllArgsConstructorpublic class NotiServiceImpl implements NotiService {    private final Logger logger = LoggerFactory.getLogger(this.getClass());    @Autowired    private InterestRopo interestRopo;    @Autowired    private NotiRepo notiRepo;    /**     * 알림 조회     * @param userid     * @return     * @throws ItDaExceptionHandler     * @throws Exception     */    public ItDaResult selectAlarm(String userid) throws ItDaExceptionHandler, Exception {        return viewAlarm(userid);    }    /**     * 알람 클릭     * @param userid     * @return     * @throws ItDaExceptionHandler     * @throws Exception     */    public ItDaResult clickAlarm(Long notiid,String userid) throws ItDaExceptionHandler, Exception {        return alarmUpdate(notiid,userid);    }    private ItDaResult alarmUpdate(Long notiid,String userid) throws Exception{        ItDaResult res = new ItDaResult();        Optional<Noti> e = notiRepo.findById(notiid);        Noti entity = e.get();        LocalDateTime currentDateTime = LocalDateTime.now();        if (e.isPresent()) {            e.get().setYnRead("Y");            e.get().setNotirReaddatetime(currentDateTime);            notiRepo.save(entity);        }        List<Noti> noti = notiRepo.findByUseridAndYnReadOrderByNotiDatetimeDesc(userid,"N");        HashMap map = new HashMap();        map.put("CNT_ALARM",noti.size());        res.setResultData(map);        return res;    }    private ItDaResult viewAlarm(String userid) throws Exception{        ItDaResult res = new ItDaResult();        List<Noti> noti = notiRepo.findByUseridAndYnReadOrderByNotiDatetimeDesc(userid,"N");        HashMap map = new HashMap();        map.put("CNT_ALARM",noti.size());        map.put("LIST_ALARM",noti);        res.setResultData(map);        return  res;    }    public void sendNoti(Noti noti) throws Exception{        LocalDateTime currentDateTime = LocalDateTime.now();        Noti nt = Noti.builder()                .userid(noti.getUserid())                .targetUserid(noti.getTargetUserid())                .notiCount(noti.getNotiCount())                .moveNum(noti.getMoveNum())                .notiMessage(noti.getNotiMessage())                .notiType(noti.getNotiType())                .notiUrl(noti.getNotiUrl())                .notiDatetime(currentDateTime)                .build();        notiRepo.save(nt);    }    public void SaveNotiEmail(Member member){        try {            if(notiRepo.findByUseridAndNotiType(member.getUserid(),"email").equals(Optional.empty())){                Noti noti = new Noti();                noti.setUserid(member.getUserid());                noti.setTargetUserid("admin");                noti.setNotiType("email");                sendNoti(noti);            }        }catch (Exception ex){            ex.getMessage();            logger.info(ex.getMessage());        }    }}