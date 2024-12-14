package state.member.domain.exception.custom;


public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super(MemberErrorCode.DUPLICATE_EMAIL.getDescription());
    }
}
