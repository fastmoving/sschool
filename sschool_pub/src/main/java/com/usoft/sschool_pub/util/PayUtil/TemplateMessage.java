package com.usoft.sschool_pub.util.PayUtil;

public class TemplateMessage {
    //用户openid
    private String touser;

    //模板消息ID
    private String template_id;

    //详情跳转页面
    private String url;

    //模板数据封装实体
    private Data data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    //消息字段信息
    public class Data {
        private  Content keyword1;
        private  Content keyword2;
        private  Content keyword3;
        private  Content keyword4;
        private  Content keyword5;

        public Content getKeyword5() {
            return keyword5;
        }

        public void setKeyword5(Content keyword5) {
            this.keyword5 = keyword5;
        }
        public Content getKeyword4() {
            return keyword4;
        }

        public void setKeyword4(Content keyword4) {
            this.keyword4 = keyword4;
        }

        public Content getKeyword1() {
            return keyword1;
        }

        public void setKeyword1(Content keyword1) {
            this.keyword1 = keyword1;
        }

        public Content getKeyword2() {
            return keyword2;
        }

        public void setKeyword2(Content keyword2) {
            this.keyword2 = keyword2;
        }

        public Content getKeyword3() {
            return keyword3;
        }

        public void setKeyword3(Content keyword3) {
            this.keyword3 = keyword3;
        }
    }
    //消息内容
    public class Content {

        private String value;

        //消息字体颜色
        private String color;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
