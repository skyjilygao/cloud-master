package cn.skyjilygao.cloud.utils;

import cn.skyjilygao.cloud.enums.ErrorCode;
import cn.skyjilygao.cloud.exception.PwException;
import org.springframework.lang.Nullable;

/**
 * @author skyjilygao
 * @date 2022-01-14 17:19
 **/
public class AssertUtil {

    public static void notNull(@Nullable Object object, ErrorCode code) {
        if (object == null) {
            throw new PwException(code);
        }
    }
    public static void isTrue(boolean b, ErrorCode code) {
        if (!b) {
            throw new PwException(code);
        }
    }

}
