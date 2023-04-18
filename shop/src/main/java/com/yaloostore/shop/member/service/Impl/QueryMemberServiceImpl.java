package com.yaloostore.shop.member.service.Impl;

import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginResponse;
import com.yaloostore.shop.member.dto.response.MemberSoftDeleteResponse;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.exception.NotFoundMemberException;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRoleRepository;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryMemberServiceImpl implements QueryMemberService {

    private final QuerydslMemberRepository querydslMemberRepository;
    private final QuerydslMemberRoleRepository querydslMemberRoleRepository;


    /**
     * 회원 삭제(논리적 삭제로 해당 id는 남겨두고(로그인 아이디말고) 나머지는 초기화 진행
     * 해당 객체가 존재하는지 확인, 해당 객체가 삭제되지 않은 상태인지 확인
     * */
    @Transactional
    @Override
    public MemberSoftDeleteResponse softDeleteMember(Long memberId) {

        Optional<Member> optionalMember = querydslMemberRepository.queryFindUndeletedMember(memberId);

        if(optionalMember.isEmpty()){
            throw  new NotFoundMemberException();
        }
        Member member = optionalMember.get();

        member.softDeleteMember();

        MemberSoftDeleteResponse response = MemberSoftDeleteResponse.fromEntity(member);

        return response;
    }

    @Transactional
    public MemberSoftDeleteResponse softDeleteLoginId(String loginId){
        Optional<Member> optionalMember = querydslMemberRepository.queryFindUndeletedMemberLoginId(loginId);

        if(optionalMember.isEmpty()){
            throw  new NotFoundMemberException();
        }
        Member member = optionalMember.get();

        member.softDeleteMember();

        MemberSoftDeleteResponse response = MemberSoftDeleteResponse.fromEntity(member);

        return response;

    }

    @Override
    public Member findByLoginId(String loginId) {
        Member member = querydslMemberRepository.queryFindUndeletedMemberLoginId(loginId).orElseThrow(() ->
        {
            throw new ClientException(ErrorCode.MEMBER_NOT_FOUND, "this member is not found");
        });
        return member;
    }

    @Override
    public MemberLoginResponse findMemberByLoginId(String loginId) {

        Member member = querydslMemberRepository.queryFindUndeletedMemberLoginId(loginId)
                .orElseThrow(() -> new ClientException(ErrorCode.MEMBER_NOT_FOUND, "member not found"));


        List<String> roles = Objects.requireNonNull(querydslMemberRoleRepository.queryFindMemberRoleByLoginId(loginId));

        MemberLoginResponse memberLoginResponse = MemberLoginResponse.fromEntity(member, roles);
        return memberLoginResponse;
    }

    @Override
    public List<MemberIdResponse> findMemberByBirthday(int lateDays) {

        String birthDay = getBirthDay(lateDays);


        List<Member> members = querydslMemberRepository.queryFindBirthdayMember(birthDay);

        return members.stream().map(MemberIdResponse::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<MemberIdResponse> findMemberIdByLateDay(int lateDays) {

        String birthDay = getBirthDay(lateDays);

        List<MemberIdResponse> response = querydslMemberRepository.queryFindMemberByBirthMonthDay(birthDay);

        return response;
    }

    private static String getBirthDay(int lateDays) {
        LocalDate searchBirthday = LocalDate.now().plusDays(lateDays);
        String birthDay;

        int month = searchBirthday.getMonthValue();
        int day = searchBirthday.getDayOfMonth();
        if (month < 10 || (month == 9 && day > 23)){
            birthDay = "0"+String.valueOf(month);
        } else {
            birthDay = String.valueOf(month);
        }

        if (day < 10){
            birthDay = birthDay+String.valueOf(day) +"0";
        } else {
            birthDay = birthDay+String.valueOf(day);
        }
        return birthDay;
    }

}
