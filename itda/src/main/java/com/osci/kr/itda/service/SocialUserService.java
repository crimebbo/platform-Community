package com.osci.kr.itda.service;import com.osci.kr.itda.common.exception.ItDaExceptionHandler;import com.osci.kr.itda.common.model.ItDaResult;import com.osci.kr.itda.entity.sns.FaceBookUser;import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpServletResponse;public interface SocialUserService {    ItDaResult soicalSignIn(Object user, HttpServletRequest req , HttpServletResponse res) throws ItDaExceptionHandler, Exception;}