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

import java.time.LocalDate;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class QueryLoginHistoryServiceImpl implements QueryLoginHistoryService {

    private final MemberLoginHistoryRepository memberLoginHistoryRepository;
    private final QuerydslMemberRepository querydslMemberRepository;

    private  MemberLoginHistory memberLoginHistory;

    @Override
    public MemberLoginHistory addLoginHistory(String loginId, LocalDate now) {
        Optional<Member> optionalMember = querydslMemberRepository.queryFindUndeletedMemberLoginId(loginId);

        if(optionalMember.isEmpty()){
            throw new ClientException(ErrorCode.MEMBER_NOT_FOUND, "member not found");
        }

        Member member = optionalMember.get();
        memberLoginHistory = MemberLoginHistory.builder()
                .member(member)
                .loginTime(now)
                .build();

        MemberLoginHistory save = memberLoginHistoryRepository.save(memberLoginHistory);
        return save;
    }
}
