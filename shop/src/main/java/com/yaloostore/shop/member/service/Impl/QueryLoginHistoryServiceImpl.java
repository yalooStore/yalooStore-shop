package com.yaloostore.shop.member.service.Impl;


import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberLoginHistory;
import com.yaloostore.shop.member.repository.basic.MemberLoginHistoryRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.member.service.inter.QueryLoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class QueryLoginHistoryServiceImpl implements QueryLoginHistoryService {

    private final MemberLoginHistoryRepository memberLoginHistoryRepository;
    private final QuerydslMemberRepository querydslMemberRepository;

    private  MemberLoginHistory memberLoginHistory;

    @Override
    @Transactional
    public MemberLoginHistory addLoginHistory(String loginId, LocalDate now) {

        //회원 로그인 기록이 없는 경우?
        
        Member member = querydslMemberRepository.queryFindUndeletedMemberLoginId(loginId)
                .orElseThrow(()-> new ClientException(ErrorCode.MEMBER_NOT_FOUND, "not found member"));

        // 로그인 기록이 있는 사람이라면?
        memberLoginHistory = getMemberLoginHistory(member);
        MemberLoginHistory save = memberLoginHistoryRepository.save(memberLoginHistory);
        return save;
    }

    private static MemberLoginHistory getMemberLoginHistory(Member member) {
        return MemberLoginHistory.builder()
                .member(member)
                .loginTime(LocalDate.now())
                .build();
    }
}
