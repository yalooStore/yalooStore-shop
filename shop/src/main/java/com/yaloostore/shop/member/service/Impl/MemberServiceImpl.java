package com.yaloostore.shop.member.service.Impl;

import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.member.dto.request.MemberCreateRequest;
import com.yaloostore.shop.member.dto.request.MemberUpdateRequest;
import com.yaloostore.shop.member.dto.response.InactiveMemberResponse;
import com.yaloostore.shop.member.dto.response.MemberCreateResponse;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberUpdateResponse;
import com.yaloostore.shop.member.entity.*;
import com.yaloostore.shop.member.entity.MemberRole.MemberRolePk;
import com.yaloostore.shop.member.exception.AlreadyExistsMember;
import com.yaloostore.shop.member.exception.AlreadyExistsMemberProfile;
import com.yaloostore.shop.member.exception.NotFoundMemberException;
import com.yaloostore.shop.member.exception.NotFoundMemberRoleException;
import com.yaloostore.shop.member.repository.basic.MemberRepository;
import com.yaloostore.shop.member.repository.basic.MemberRoleRepository;
import com.yaloostore.shop.member.repository.basic.MembershipHistoryRepository;
import com.yaloostore.shop.member.repository.basic.MembershipRepository;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.member.service.inter.MemberService;
import com.yaloostore.shop.role.entity.Role;
import com.yaloostore.shop.role.exception.AlreadyDeletedAddressException;
import com.yaloostore.shop.role.repository.basic.RoleCommonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImpl implements MemberService {
    private final MemberRoleRepository memberRoleRepository;
    private final RoleCommonRepository roleRepository;
    private final MembershipRepository membershipRepository;
    private final MembershipHistoryRepository membershipHistoryRepository;

    private final QuerydslMemberRepository querydslMemberRepository;
    private final MemberRepository memberRepository;


    /**
     * 회원등록 기능
     * 회원 가입 시 회원 등급(Membership)과 회원 권한(MemberRole)을 부여해줍니다.
     * @param createRequest DTO로 넘겨받은 정보를 가지고 연관된 테이블까지 전부 정보를 저장할 수 있도록 합니다.
     * @return 해당 entity를 넘겨주는 형식이 아닌 response DTO를 두어 반환할 수 있게 합니다.
     * */
    @Transactional
    @Override
    public MemberCreateResponse createMember(MemberCreateRequest createRequest) {

        //1. 입력받은 request dto에서 해당 중복 사항이 없다면 회원 생성, 저장(연관관계에 있는 데이터들을 모두 넣어주기) 작업 계속 진행
        checkExistMember(createRequest);

        //2. member 생성, 이때 OneToOne 관계로 둘을 같이 save 해준다.
        Membership membership = Membership.createMembership();
        Member member = createRequest.toEntity(membership);
        Member savedMember = memberRepository.save(member);

        membershipRepository.save(membership);

        MembershipHistory membershipHistory = createMembershipHistory(membership,savedMember);
        membershipHistoryRepository.save(membershipHistory);

        Long roleId = 2L;
        Role role = roleRepository.findByRoleId(roleId).orElseThrow(NotFoundMemberRoleException::new);
        MemberRole memberRole = createMemberRole(savedMember, roleId, role);

        memberRoleRepository.save(memberRole);

        MemberCreateResponse response = MemberCreateResponse.fromEntity(savedMember, role);

        return response;
    }


    private MembershipHistory createMembershipHistory(Membership membership, Member member) {
        //회원가입시에만 사용하는 멤버쉽 히스토리
        return MembershipHistory.builder()
                .updateTime(LocalDateTime.now())
                .previousPaidAmount(0L)
                .membership(membership)
                .member(member)
                .build();
    }

    private MemberRole createMemberRole(Member savedMember, Long roleId, Role roleMember) {
        //회원가입시에만 사용하는 회원권한
        return MemberRole.builder()
                .memberRolePk(new MemberRolePk(savedMember.getMemberId(), roleId))
                .member(savedMember)
                .role(roleMember)
                .build();
    }

    //회원 가입시 중복되는 id, nickname, emailAddress, phoneNumber 은 예외를 던져 처리하고 회원 가입을 하지 못하게 한다.
    private void checkExistMember(MemberCreateRequest createRequest){
        if(querydslMemberRepository.existMemberByNickname(createRequest.getNickname())){
            throw new AlreadyExistsMember();
        }
        if(querydslMemberRepository.existMemberByEmail(createRequest.getEmailAddress())){
            throw new AlreadyExistsMember();
        }
        if (querydslMemberRepository.existMemberByPhoneNumber(createRequest.getPhoneNumber())){
            throw new AlreadyExistsMember();
        }
        if (querydslMemberRepository.existMemberByLoginId(createRequest.getId())) {
            throw new AlreadyDeletedAddressException();
        }
    }

    /**
     * 회원 수정(우선은 닉네임만 수정 가능)
     * 회원 수정 시 해당 memberId에 해당하는 member 객체를 가져온 뒤, 변경하고자하는 정보에서 중복이 되면 안 되는 부분을 확인해준다.
     * 중복이 없이 조건에 부합하는 정보를 받았다면 해당 회원 객체의 정보를 수정할 수 있도록 한다.
     * @param memberId - 회원 객체를 가져오기 위한 입력값,
     * @param updateRequest - 수정하고자하는 회원의 정보를 받을 DTO 객체
     * @return 회원 엔티티가 아닌 response dto를 넘겨준다.
     * */
    @Transactional
    @Override
    public MemberUpdateResponse updateMember(Long memberId, MemberUpdateRequest updateRequest) {
        Member member = getMemberByMemberId(memberId);
        //checkUniqueProfileMember(updateRequest);
        member.changeMemberNickname(updateRequest.getNickname());
        return MemberUpdateResponse.fromEntity(member);
    }

    @Transactional
    @Override
    public List<InactiveMemberResponse> changeInactiveMembers(List<MemberIdResponse> inactiveMembers) {
//        List<Member> list = inactiveMembers.stream().map(memberId -> getMemberByMemberId(memberId.getMemberId())).collect(Collectors.toList());
//        List<InactiveMemberResponse> result = new ArrayList<>();
//
//        for (Member member : list) {
//            member.makeSleepAccount();
//            result.add(InactiveMemberResponse.fromEntity(member));
//        }
//        return result;

        return inactiveMembers.stream()
                .map(memberId -> getMemberByMemberId(memberId.getMemberId()))
                .peek(Member::makeSleepAccount)
                .map(InactiveMemberResponse::fromEntity)
                .collect(Collectors.toList());
    }


    @Transactional
    public MemberUpdateResponse updateMemberNickname(String loginId, MemberUpdateRequest updateRequest){
        Member member = getMember(loginId);
        member.changeMemberNickname(updateRequest.getNickname());
        return MemberUpdateResponse.fromEntity(member);
    }


    private Member getMember(String loginId) {
        Member member = querydslMemberRepository.queryFindUndeletedMemberLoginId(loginId)
                .orElseThrow(() -> new ClientException(ErrorCode.MEMBER_NOT_FOUND, "member is not found!"));
        return member;
    }


    private Member getMemberByMemberId(Long memberId) {
        return memberRepository.findMemberByMemberId(memberId)
                .orElseThrow(NotFoundMemberException::new);
    }




}
