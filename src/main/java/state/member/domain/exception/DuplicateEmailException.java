package state.member.domain.exception;


public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super(MemberErrorCode.DUPLICATE_USER.getDescription());
    }
}
