package com.kovalik.rtestapp1.enums;

import android.content.res.Resources;
import com.kovalik.rtestapp1.R;

public enum HTTPCode {

    UNKNOWN(-1),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504);

    private int code;

    // Cache all values to avoid calls to HTTPCode.values() method
    private static HTTPCode[] values = null;

    HTTPCode(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static HTTPCode fromValues(int code) {
        if(HTTPCode.values == null) {
            HTTPCode.values = HTTPCode.values();
        }
        for (HTTPCode httpCode : HTTPCode.values) {
            if (httpCode.getCode() == code) {
                return httpCode;
            }
        }
        return UNKNOWN;
    }

    public static String mapToError(HTTPCode httpCode, Resources resources) {
        String mappedCode;
        switch (httpCode) {
            case BAD_REQUEST:
                mappedCode = resources.getString(R.string.error_400);
                break;
            case UNAUTHORIZED:
                mappedCode = resources.getString(R.string.error_401);
                break;
            case FORBIDDEN:
                mappedCode = resources.getString(R.string.error_403);
                break;
            case NOT_FOUND:
                mappedCode = resources.getString(R.string.error_404);
                break;
            case INTERNAL_SERVER_ERROR:
                mappedCode = resources.getString(R.string.error_500);
                break;
            case BAD_GATEWAY:
                mappedCode = resources.getString(R.string.error_502);
                break;
            case SERVICE_UNAVAILABLE:
                mappedCode = resources.getString(R.string.error_503);
                break;
            case GATEWAY_TIMEOUT:
                mappedCode = resources.getString(R.string.error_504);
                break;
            case UNKNOWN:
            default:
                mappedCode = resources.getString(R.string.error_unknown);
        }
        return mappedCode;
    }
}
