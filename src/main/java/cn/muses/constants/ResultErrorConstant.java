package cn.muses.constants;

/**
 * 应用内部自定义异常错误信息
 *
 * @author jervis
 * @date 2020/8/6.
 */
public interface ResultErrorConstant {

    /**
     * 前端undefined
     */
    String UNDEFINED = "undefined";

    enum Error {
        /** 通用错误 */
        GENERAL(1, "系统异常"),
        /** 自定义错误 */
        CUSTOM(2, "");

        private final Integer code;

        private final String desc;

        Error(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int code() {
            return code;
        }

        public String desc() {
            return desc;
        }
    }
}
