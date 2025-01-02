package state.admin.memberManage.application.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import state.common.exception.ErrorCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

   private Integer resultCode;
   private String resultMessage;


   public static Result OK() {
       return Result.builder()
               .resultCode(ErrorCode.OK.getErrorCode())
               .resultMessage(ErrorCode.OK.getDescription())
               .build();
   }

   public static Result ERROR(ErrorCode errorCode) {
      return Result.builder()
              .resultCode(errorCode.getErrorCode())
              .resultMessage(errorCode.getDescription())
              .build();
   }

   public static Result ERROR(ErrorCode errorCode, Throwable throwable) {
      return Result.builder()
              .resultCode(errorCode.getErrorCode())
              .resultMessage(throwable.getMessage())
              .build();
   }

   public static Result ERROR(ErrorCode errorCode, String description) {
      return Result.builder()
              .resultCode(errorCode.getErrorCode())
              .resultMessage(description)
              .build();
   }
}
