package jpa.mvc.web;

import jpa.mvc.Address;
import jpa.mvc.repository.MemberRepository;
import jpa.mvc.domain.Member;
import jpa.mvc.service.MemberService;
import jpa.mvc.web.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    @GetMapping("member-form")
    public String getMemberForm(Model model){
        model.addAttribute("member", new MemberDto()); // 빈 Member 모델로 던짐으로써 유연하게 타임리프 뷰 템플릿에서 form에서 쓸 수 있게끔
        return "member/memberForm";
    }

    @PostMapping("member-form")
    public String postMemberForm(@Validated @ModelAttribute("member") MemberDto memberDto , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "member/memberForm"; //필드에러만 검증 ㅇㅇ 전역 에러는 따로 if문으로 검증해야됨 ㅋㅋ
        }


        //VO는 여기서 발동되고 뷰에는 dto만 던지는 것!
        Member member = new Member(memberDto.getUsername() , new Address(memberDto.getCity() , memberDto.getDistance() , memberDto.getZipcode()));
        memberService.join(member);
        return "redirect:/";

    }

    @GetMapping("member-list")
    public String getMemberList(Model model){
        List<Member> members = memberService.findMembers();
        List<MemberDto> memberDtos = new ArrayList<>();

        //실제론 이런 식으로 원하는 필드만 담아서 dto에 넣어서 뷰에 던져야됨 , vo 어차피 뷰에서 못꺼냄??
        for (Member member : members) {
            String username = member.getName();
            String city = member.getAddress().getCity();
            String distance = member.getAddress().getDistance();
            String zipcode = member.getAddress().getZipcode();
            memberDtos.add(new MemberDto(username, city, distance, zipcode));
        }
        model.addAttribute("members", memberDtos);
        return "member/memberList";
    }



}
