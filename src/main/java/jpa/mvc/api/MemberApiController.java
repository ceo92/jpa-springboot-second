package jpa.mvc.api;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import jpa.mvc.domain.Member;
import jpa.mvc.service.MemberService;
import jpa.mvc.web.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

  private final MemberService memberService;
  @PostMapping("/api/v2/members")
  public CreateMemberResponse saveMemberV2(@RequestBody @Validated CreateMemberRequest createMemberRequest){
    String name = createMemberRequest.getName();
    Member member = new Member();
    member.setName(name);
    Long joinId = memberService.join(member);
    return new CreateMemberResponse(joinId);
  }

  @PutMapping("/api/v2/members/{id}")
  public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
      @RequestBody @Validated UpdateMemberRequest updateMemberRequest){
    String name = updateMemberRequest.getName();
    memberService.update(id , name);
    Member findMember = memberService.findOne(id);
    return new UpdateMemberResponse(findMember.getId() , name ); //해당 id 값이 잘 update 됨을 알리기 위해 API 스펙적으로 단일 id 값 하나를 넘김
  }

  @GetMapping("api/v1/members")
  public List<Member> findMembersV1(){
    return memberService.findMembers();
  }

  @GetMapping("api/v2/members")
  public Result findMembersV2(){
    List<Member> members = memberService.findMembers();
    List<ReadMemberDto> list = members.stream().map(member -> new ReadMemberDto(member.getName()))
        .toList();
    return new Result<>(list); //리스트가 아닌 필드로 반환
  }


  @Data
  @AllArgsConstructor
  static class Result<T>{
    //private Integer count;
    private T data;
  }


  @Data
  @AllArgsConstructor
  static class ReadMemberDto{
    private String name;
  }


  @Data
  static class UpdateMemberRequest{
    private String name;
  }


  @Data
  @AllArgsConstructor
  static class UpdateMemberResponse{
    private Long id;
    private String name;

  }





  @Data
  static class CreateMemberRequest{
    @NotNull
    private String name; //요청 파라메터 스펙 , 이름을 던지는 것임 , 요청할 땐 폼 처리에서도 주로 이름을 나이 학교 같은 데이터들을 입력하므로
  }

  @Data
  static class CreateMemberResponse{
    private Long id;

    public CreateMemberResponse(Long id) {
      this.id = id;
    }
  }


}
