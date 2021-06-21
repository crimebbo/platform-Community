package com.osci.kr.itda.entity;import com.fasterxml.jackson.annotation.JsonCreator;import com.fasterxml.jackson.annotation.JsonProperty;import lombok.AccessLevel;import lombok.Builder;import lombok.Data;import lombok.NoArgsConstructor;import org.hibernate.annotations.CreationTimestamp;import org.springframework.hateoas.RepresentationModel;import javax.persistence.*;import java.time.LocalDateTime;@Data@NoArgsConstructor(access = AccessLevel.PUBLIC)@Entity(name="tbl_member_register")@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userid" }),        indexes = {@Index(columnList="userid")})public class MemberJoinLog extends RepresentationModel<MemberJoinLog> {    @Id    @GeneratedValue(strategy= GenerationType.IDENTITY)    @Column(length = 11, nullable = false)    private Long mrgid;    @Column(length = 100, nullable = false)    private String userid;    @Column(length = 50, nullable = false)    private String mrgIp;    @CreationTimestamp    private LocalDateTime mrgDatetime;    @Column(length = 11, nullable = true)    private Long mrgRecommendmemid;    @Column(length = 255, nullable = true)    private String mrguserAgent;    @Column(length = 10, nullable = true)    private int mrgJoinTy;    @Builder    @JsonCreator    private MemberJoinLog(@JsonProperty("mrgid") Long mrgid, @JsonProperty("mrgIp") String mrgIp ,                          @JsonProperty("userid") String userid ,  @JsonProperty("mrgDatetime") LocalDateTime mrgDatetime,                          @JsonProperty("mrgJoinTy") int mrgJoinTy,@JsonProperty("mrguserAgent") String mrguserAgent,@JsonProperty("mrgRecommendmemid") Long mrgRecommendmemid){        this.userid = userid;        this.mrgIp = mrgIp;        this.mrgDatetime = mrgDatetime;        this.mrgJoinTy = mrgJoinTy;        this.mrguserAgent = mrguserAgent;        this.mrgRecommendmemid = mrgRecommendmemid;    }}