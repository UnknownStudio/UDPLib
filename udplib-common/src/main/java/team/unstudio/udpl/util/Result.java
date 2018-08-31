package team.unstudio.udpl.util;

import com.google.common.base.Strings;

import javax.annotation.Nullable;

public final class Result {

    private static final Result SUCCESS_RESULT = new Result(true);

    public static Result success() {
        return SUCCESS_RESULT;
    }

    public static Result failure() {
        return new Result(false);
    }

    public static Result failure(String message) {
        return new Result(false, message);
    }

    public static Result failure(Throwable throwable) {
        return new Result(false, throwable);
    }

    public static Result failure(String message, Throwable throwable) {
        return new Result(false, message, throwable);
    }

    private final boolean success;
    private final String message;
    private final Throwable throwable;

    private Result(boolean success) {
        this(success, null, null);
    }

    private Result(boolean success, String message) {
        this(success, message, null);
    }

    private Result(boolean success, Throwable throwable) {
        this(success, null, throwable);
    }

    private Result(boolean success, String message, Throwable throwable) {
        this.success = success;
        this.message = Strings.nullToEmpty(message);
        this.throwable = throwable;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return !success;
    }

    public String getMessage() {
        return message;
    }

    @Nullable
    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public String toString() {
        return String.format("team.unstudio.udpl.util.Result[success: %b, message: %s, throwable: %s]", success, message == null ? "null" : message, throwable == null ? "null" : throwable.toString());
    }
}
