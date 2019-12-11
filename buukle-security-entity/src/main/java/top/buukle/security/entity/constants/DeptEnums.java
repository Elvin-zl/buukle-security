package top.buukle.security .entity.constants;

/**
 * @Author: elvin
 * @Date: 2019/7/28/028 3:56
 */
public class DeptEnums {

    public enum status {

        DELETED(-1,"已经删除"),
        INIT(0,"初始化"),
        HANDLING(1,"处理中"),
        REJECT(2,"审核不通过"),
        PUBLISED(3,"审核通过"),
        BAN(4,"已被封禁"),
        ;

        private Integer status;
        private String description;

        status(int status, String description) {
            this.description = description;
            this.status = status;
        }
        public String getDescription() {
            return description;
        }
        public Integer value() {
            return status;
        }
    }
    public enum leader {

        SELF_LEADER(0,"自身领导"),
        LOCAL_LEADER(1,"本级领导"),
        PASS_LEADER(2,"越级领导"),
        ;

        private Integer level;
        private String description;

        leader(int level, String description) {
            this.description = description;
            this.level = level;
        }

        public Integer level() {
            return level;
        }

        public String getDescription() {
            return description;
        }
    }
}
