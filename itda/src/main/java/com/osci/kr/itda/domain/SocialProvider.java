package com.osci.kr.itda.domain;import lombok.AllArgsConstructor;import lombok.Getter;@AllArgsConstructor@Getterpublic enum SocialProvider {    PLATFORM(0),    FACEBOOK(1),    KAKAO(2),    NAVER(3),    GOOGLE(4);    private int value;}