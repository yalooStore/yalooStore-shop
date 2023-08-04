package com.yaloostore.shop.member.service.Impl;

import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginHistoryResponse;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberLoginHistory;
import com.yaloostore.shop.member.repository.basic.MemberLoginHistoryRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QueryLoginHistoryRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QueryMemberLoginHistoryRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.member.service.inter.MemberLoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberLoginHistoryServiceImpl implements MemberLoginHistoryService {



    private final QuerydslMemberRepository querydslMemberRepository;
    private final MemberLoginHistoryRepository memberLoginHistoryRepository;
    private final QueryMemberLoginHistoryRepository queryMemberLoginHistoryRepository;


    /**
     * {@inheritDoc}
     * */
    @Transactional
    @Override
    public MemberLoginHistoryResponse addLoginHistory(String loginId) {
        Member member = querydslMemberRepository.queryFindUndeletedMemberLoginId(loginId)
                .orElseThrow(() -> new ClientException(ErrorCode.MEMBER_NOT_FOUND, "해당 로그인 아이디에 해당하는 회원이 없습니다 아이디를 다시 확인해주세요: " + loginId));

        MemberLoginHistory memberLoginHistory = MemberLoginHistory.toEntity(member);

        memberLoginHistoryRepository.save(memberLoginHistory);

        return MemberLoginHistoryResponse.fromEntity(memberLoginHistory);
    }

    @Override
    public List<MemberIdResponse> findMemberByLoginHistory(LocalDate today) {

        List<MemberIdResponse> inactiveMemberList = queryMemberLoginHistoryRepository.queryFindMemberBySleeper(today);

        return inactiveMemberList;
    }


}
