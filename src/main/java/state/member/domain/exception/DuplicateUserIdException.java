package state.member.domain.exception;

public class DuplicateUserIdException extends RuntimeException {
    public DuplicateUserIdException() {
        super(MemberErrorCode.DUPLICATE_USERID.getDescription());
    }
}
