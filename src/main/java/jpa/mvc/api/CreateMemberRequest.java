package jpa.mvc.api;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMemberRequest {

  @NotNull
  private String name;

}
