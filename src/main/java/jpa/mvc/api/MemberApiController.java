package jpa.mvc.api;

import jpa.mvc.domain.Member;
import jpa.mvc.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

  private final MemberService memberService;

  @PostMapping("/api/v1/members")
  public CreateMemberResponse saveMemberV1(@RequestBody @Validated Member member){
    Long joinId = memberService.join(member);
    return new CreateMemberResponse(joinId);
  }

  @PostMapping("/api/v2/members")
  public CreateMemberResponse saveMemberV2(@RequestBody @Validated CreateMemberRequest createMemberRequest){
    String name = createMemberRequest.getName();
    Member member = new Member();
    member.setName(name);
    Long joinId = memberService.join(member);
    return new CreateMemberResponse(joinId);
  }


  @Data
  static class CreateMemberResponse{
    private Long id;

    public CreateMemberResponse(Long id) {
      this.id = id;
    }
  }

}
