package org.openmore.common.polyv;

public class PolyvResponseData{
        private Integer code;
        private String status;
        private String message;
        private PolyvVideo[] data;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public PolyvVideo[] getData() {
            return data;
        }

        public void setData(PolyvVideo[] data) {
            this.data = data;
        }
    }