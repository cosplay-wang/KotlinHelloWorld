package com.cosplay.kotlin.hw.mvvm2.entity;

import java.util.List;

/**
 * Author:wangzhiwei on 2019/1/8.
 * Email :wangzhiwei@moyi365.com
 * Description:
 */
public class Book {

    /**
     * total : 6
     * page : 1
     * list : [{"id":"16738","self_id":"465","book_id":"0","teacher_uid":"4724","student_uid":"50425"},{"id":"16738","self_id":"465","book_id":"0","teacher_uid":"4724","student_uid":"50425"},{"id":"16738","self_id":"465","book_id":"0","teacher_uid":"4724","student_uid":"50425"}]
     */

    private String total;
    private String page;
    private List<ListBean> list;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 16738
         * self_id : 465
         * book_id : 0
         * teacher_uid : 4724
         * student_uid : 50425
         */

        private String id;
        private String self_id;
        private String book_id;
        private String teacher_uid;
        private String student_uid;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSelf_id() {
            return self_id;
        }

        public void setSelf_id(String self_id) {
            this.self_id = self_id;
        }

        public String getBook_id() {
            return book_id;
        }

        public void setBook_id(String book_id) {
            this.book_id = book_id;
        }

        public String getTeacher_uid() {
            return teacher_uid;
        }

        public void setTeacher_uid(String teacher_uid) {
            this.teacher_uid = teacher_uid;
        }

        public String getStudent_uid() {
            return student_uid;
        }

        public void setStudent_uid(String student_uid) {
            this.student_uid = student_uid;
        }
    }

}
