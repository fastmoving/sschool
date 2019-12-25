package com.usoft.sschool_zuul.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class HlTeacherExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HlTeacherExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSchoolidIsNull() {
            addCriterion("SchoolID is null");
            return (Criteria) this;
        }

        public Criteria andSchoolidIsNotNull() {
            addCriterion("SchoolID is not null");
            return (Criteria) this;
        }

        public Criteria andSchoolidEqualTo(Integer value) {
            addCriterion("SchoolID =", value, "schoolid");
            return (Criteria) this;
        }

        public Criteria andSchoolidNotEqualTo(Integer value) {
            addCriterion("SchoolID <>", value, "schoolid");
            return (Criteria) this;
        }

        public Criteria andSchoolidGreaterThan(Integer value) {
            addCriterion("SchoolID >", value, "schoolid");
            return (Criteria) this;
        }

        public Criteria andSchoolidGreaterThanOrEqualTo(Integer value) {
            addCriterion("SchoolID >=", value, "schoolid");
            return (Criteria) this;
        }

        public Criteria andSchoolidLessThan(Integer value) {
            addCriterion("SchoolID <", value, "schoolid");
            return (Criteria) this;
        }

        public Criteria andSchoolidLessThanOrEqualTo(Integer value) {
            addCriterion("SchoolID <=", value, "schoolid");
            return (Criteria) this;
        }

        public Criteria andSchoolidIn(List<Integer> values) {
            addCriterion("SchoolID in", values, "schoolid");
            return (Criteria) this;
        }

        public Criteria andSchoolidNotIn(List<Integer> values) {
            addCriterion("SchoolID not in", values, "schoolid");
            return (Criteria) this;
        }

        public Criteria andSchoolidBetween(Integer value1, Integer value2) {
            addCriterion("SchoolID between", value1, value2, "schoolid");
            return (Criteria) this;
        }

        public Criteria andSchoolidNotBetween(Integer value1, Integer value2) {
            addCriterion("SchoolID not between", value1, value2, "schoolid");
            return (Criteria) this;
        }

        public Criteria andTnameIsNull() {
            addCriterion("Tname is null");
            return (Criteria) this;
        }

        public Criteria andTnameIsNotNull() {
            addCriterion("Tname is not null");
            return (Criteria) this;
        }

        public Criteria andTnameEqualTo(String value) {
            addCriterion("Tname =", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameNotEqualTo(String value) {
            addCriterion("Tname <>", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameGreaterThan(String value) {
            addCriterion("Tname >", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameGreaterThanOrEqualTo(String value) {
            addCriterion("Tname >=", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameLessThan(String value) {
            addCriterion("Tname <", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameLessThanOrEqualTo(String value) {
            addCriterion("Tname <=", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameLike(String value) {
            addCriterion("Tname like", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameNotLike(String value) {
            addCriterion("Tname not like", value, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameIn(List<String> values) {
            addCriterion("Tname in", values, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameNotIn(List<String> values) {
            addCriterion("Tname not in", values, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameBetween(String value1, String value2) {
            addCriterion("Tname between", value1, value2, "tname");
            return (Criteria) this;
        }

        public Criteria andTnameNotBetween(String value1, String value2) {
            addCriterion("Tname not between", value1, value2, "tname");
            return (Criteria) this;
        }

        public Criteria andTsnameIsNull() {
            addCriterion("TsName is null");
            return (Criteria) this;
        }

        public Criteria andTsnameIsNotNull() {
            addCriterion("TsName is not null");
            return (Criteria) this;
        }

        public Criteria andTsnameEqualTo(String value) {
            addCriterion("TsName =", value, "tsname");
            return (Criteria) this;
        }

        public Criteria andTsnameNotEqualTo(String value) {
            addCriterion("TsName <>", value, "tsname");
            return (Criteria) this;
        }

        public Criteria andTsnameGreaterThan(String value) {
            addCriterion("TsName >", value, "tsname");
            return (Criteria) this;
        }

        public Criteria andTsnameGreaterThanOrEqualTo(String value) {
            addCriterion("TsName >=", value, "tsname");
            return (Criteria) this;
        }

        public Criteria andTsnameLessThan(String value) {
            addCriterion("TsName <", value, "tsname");
            return (Criteria) this;
        }

        public Criteria andTsnameLessThanOrEqualTo(String value) {
            addCriterion("TsName <=", value, "tsname");
            return (Criteria) this;
        }

        public Criteria andTsnameLike(String value) {
            addCriterion("TsName like", value, "tsname");
            return (Criteria) this;
        }

        public Criteria andTsnameNotLike(String value) {
            addCriterion("TsName not like", value, "tsname");
            return (Criteria) this;
        }

        public Criteria andTsnameIn(List<String> values) {
            addCriterion("TsName in", values, "tsname");
            return (Criteria) this;
        }

        public Criteria andTsnameNotIn(List<String> values) {
            addCriterion("TsName not in", values, "tsname");
            return (Criteria) this;
        }

        public Criteria andTsnameBetween(String value1, String value2) {
            addCriterion("TsName between", value1, value2, "tsname");
            return (Criteria) this;
        }

        public Criteria andTsnameNotBetween(String value1, String value2) {
            addCriterion("TsName not between", value1, value2, "tsname");
            return (Criteria) this;
        }

        public Criteria andTcodeIsNull() {
            addCriterion("TCode is null");
            return (Criteria) this;
        }

        public Criteria andTcodeIsNotNull() {
            addCriterion("TCode is not null");
            return (Criteria) this;
        }

        public Criteria andTcodeEqualTo(String value) {
            addCriterion("TCode =", value, "tcode");
            return (Criteria) this;
        }

        public Criteria andTcodeNotEqualTo(String value) {
            addCriterion("TCode <>", value, "tcode");
            return (Criteria) this;
        }

        public Criteria andTcodeGreaterThan(String value) {
            addCriterion("TCode >", value, "tcode");
            return (Criteria) this;
        }

        public Criteria andTcodeGreaterThanOrEqualTo(String value) {
            addCriterion("TCode >=", value, "tcode");
            return (Criteria) this;
        }

        public Criteria andTcodeLessThan(String value) {
            addCriterion("TCode <", value, "tcode");
            return (Criteria) this;
        }

        public Criteria andTcodeLessThanOrEqualTo(String value) {
            addCriterion("TCode <=", value, "tcode");
            return (Criteria) this;
        }

        public Criteria andTcodeLike(String value) {
            addCriterion("TCode like", value, "tcode");
            return (Criteria) this;
        }

        public Criteria andTcodeNotLike(String value) {
            addCriterion("TCode not like", value, "tcode");
            return (Criteria) this;
        }

        public Criteria andTcodeIn(List<String> values) {
            addCriterion("TCode in", values, "tcode");
            return (Criteria) this;
        }

        public Criteria andTcodeNotIn(List<String> values) {
            addCriterion("TCode not in", values, "tcode");
            return (Criteria) this;
        }

        public Criteria andTcodeBetween(String value1, String value2) {
            addCriterion("TCode between", value1, value2, "tcode");
            return (Criteria) this;
        }

        public Criteria andTcodeNotBetween(String value1, String value2) {
            addCriterion("TCode not between", value1, value2, "tcode");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("Sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("Sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(Integer value) {
            addCriterion("Sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(Integer value) {
            addCriterion("Sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(Integer value) {
            addCriterion("Sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(Integer value) {
            addCriterion("Sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(Integer value) {
            addCriterion("Sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(Integer value) {
            addCriterion("Sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<Integer> values) {
            addCriterion("Sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<Integer> values) {
            addCriterion("Sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(Integer value1, Integer value2) {
            addCriterion("Sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(Integer value1, Integer value2) {
            addCriterion("Sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNull() {
            addCriterion("IdCard is null");
            return (Criteria) this;
        }

        public Criteria andIdcardIsNotNull() {
            addCriterion("IdCard is not null");
            return (Criteria) this;
        }

        public Criteria andIdcardEqualTo(String value) {
            addCriterion("IdCard =", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotEqualTo(String value) {
            addCriterion("IdCard <>", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThan(String value) {
            addCriterion("IdCard >", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("IdCard >=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThan(String value) {
            addCriterion("IdCard <", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLessThanOrEqualTo(String value) {
            addCriterion("IdCard <=", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardLike(String value) {
            addCriterion("IdCard like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotLike(String value) {
            addCriterion("IdCard not like", value, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardIn(List<String> values) {
            addCriterion("IdCard in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotIn(List<String> values) {
            addCriterion("IdCard not in", values, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardBetween(String value1, String value2) {
            addCriterion("IdCard between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andIdcardNotBetween(String value1, String value2) {
            addCriterion("IdCard not between", value1, value2, "idcard");
            return (Criteria) this;
        }

        public Criteria andNationIsNull() {
            addCriterion("Nation is null");
            return (Criteria) this;
        }

        public Criteria andNationIsNotNull() {
            addCriterion("Nation is not null");
            return (Criteria) this;
        }

        public Criteria andNationEqualTo(Integer value) {
            addCriterion("Nation =", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotEqualTo(Integer value) {
            addCriterion("Nation <>", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThan(Integer value) {
            addCriterion("Nation >", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationGreaterThanOrEqualTo(Integer value) {
            addCriterion("Nation >=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThan(Integer value) {
            addCriterion("Nation <", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationLessThanOrEqualTo(Integer value) {
            addCriterion("Nation <=", value, "nation");
            return (Criteria) this;
        }

        public Criteria andNationIn(List<Integer> values) {
            addCriterion("Nation in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotIn(List<Integer> values) {
            addCriterion("Nation not in", values, "nation");
            return (Criteria) this;
        }

        public Criteria andNationBetween(Integer value1, Integer value2) {
            addCriterion("Nation between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andNationNotBetween(Integer value1, Integer value2) {
            addCriterion("Nation not between", value1, value2, "nation");
            return (Criteria) this;
        }

        public Criteria andHometownIsNull() {
            addCriterion("Hometown is null");
            return (Criteria) this;
        }

        public Criteria andHometownIsNotNull() {
            addCriterion("Hometown is not null");
            return (Criteria) this;
        }

        public Criteria andHometownEqualTo(String value) {
            addCriterion("Hometown =", value, "hometown");
            return (Criteria) this;
        }

        public Criteria andHometownNotEqualTo(String value) {
            addCriterion("Hometown <>", value, "hometown");
            return (Criteria) this;
        }

        public Criteria andHometownGreaterThan(String value) {
            addCriterion("Hometown >", value, "hometown");
            return (Criteria) this;
        }

        public Criteria andHometownGreaterThanOrEqualTo(String value) {
            addCriterion("Hometown >=", value, "hometown");
            return (Criteria) this;
        }

        public Criteria andHometownLessThan(String value) {
            addCriterion("Hometown <", value, "hometown");
            return (Criteria) this;
        }

        public Criteria andHometownLessThanOrEqualTo(String value) {
            addCriterion("Hometown <=", value, "hometown");
            return (Criteria) this;
        }

        public Criteria andHometownLike(String value) {
            addCriterion("Hometown like", value, "hometown");
            return (Criteria) this;
        }

        public Criteria andHometownNotLike(String value) {
            addCriterion("Hometown not like", value, "hometown");
            return (Criteria) this;
        }

        public Criteria andHometownIn(List<String> values) {
            addCriterion("Hometown in", values, "hometown");
            return (Criteria) this;
        }

        public Criteria andHometownNotIn(List<String> values) {
            addCriterion("Hometown not in", values, "hometown");
            return (Criteria) this;
        }

        public Criteria andHometownBetween(String value1, String value2) {
            addCriterion("Hometown between", value1, value2, "hometown");
            return (Criteria) this;
        }

        public Criteria andHometownNotBetween(String value1, String value2) {
            addCriterion("Hometown not between", value1, value2, "hometown");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNull() {
            addCriterion("Birthday is null");
            return (Criteria) this;
        }

        public Criteria andBirthdayIsNotNull() {
            addCriterion("Birthday is not null");
            return (Criteria) this;
        }

        public Criteria andBirthdayEqualTo(String value) {
            addCriterion("Birthday =", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotEqualTo(String value) {
            addCriterion("Birthday <>", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThan(String value) {
            addCriterion("Birthday >", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayGreaterThanOrEqualTo(String value) {
            addCriterion("Birthday >=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThan(String value) {
            addCriterion("Birthday <", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLessThanOrEqualTo(String value) {
            addCriterion("Birthday <=", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayLike(String value) {
            addCriterion("Birthday like", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotLike(String value) {
            addCriterion("Birthday not like", value, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayIn(List<String> values) {
            addCriterion("Birthday in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotIn(List<String> values) {
            addCriterion("Birthday not in", values, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayBetween(String value1, String value2) {
            addCriterion("Birthday between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andBirthdayNotBetween(String value1, String value2) {
            addCriterion("Birthday not between", value1, value2, "birthday");
            return (Criteria) this;
        }

        public Criteria andHealthstatusintIsNull() {
            addCriterion("HealthStatusint is null");
            return (Criteria) this;
        }

        public Criteria andHealthstatusintIsNotNull() {
            addCriterion("HealthStatusint is not null");
            return (Criteria) this;
        }

        public Criteria andHealthstatusintEqualTo(Integer value) {
            addCriterion("HealthStatusint =", value, "healthstatusint");
            return (Criteria) this;
        }

        public Criteria andHealthstatusintNotEqualTo(Integer value) {
            addCriterion("HealthStatusint <>", value, "healthstatusint");
            return (Criteria) this;
        }

        public Criteria andHealthstatusintGreaterThan(Integer value) {
            addCriterion("HealthStatusint >", value, "healthstatusint");
            return (Criteria) this;
        }

        public Criteria andHealthstatusintGreaterThanOrEqualTo(Integer value) {
            addCriterion("HealthStatusint >=", value, "healthstatusint");
            return (Criteria) this;
        }

        public Criteria andHealthstatusintLessThan(Integer value) {
            addCriterion("HealthStatusint <", value, "healthstatusint");
            return (Criteria) this;
        }

        public Criteria andHealthstatusintLessThanOrEqualTo(Integer value) {
            addCriterion("HealthStatusint <=", value, "healthstatusint");
            return (Criteria) this;
        }

        public Criteria andHealthstatusintIn(List<Integer> values) {
            addCriterion("HealthStatusint in", values, "healthstatusint");
            return (Criteria) this;
        }

        public Criteria andHealthstatusintNotIn(List<Integer> values) {
            addCriterion("HealthStatusint not in", values, "healthstatusint");
            return (Criteria) this;
        }

        public Criteria andHealthstatusintBetween(Integer value1, Integer value2) {
            addCriterion("HealthStatusint between", value1, value2, "healthstatusint");
            return (Criteria) this;
        }

        public Criteria andHealthstatusintNotBetween(Integer value1, Integer value2) {
            addCriterion("HealthStatusint not between", value1, value2, "healthstatusint");
            return (Criteria) this;
        }

        public Criteria andTtypeIsNull() {
            addCriterion("Ttype is null");
            return (Criteria) this;
        }

        public Criteria andTtypeIsNotNull() {
            addCriterion("Ttype is not null");
            return (Criteria) this;
        }

        public Criteria andTtypeEqualTo(Integer value) {
            addCriterion("Ttype =", value, "ttype");
            return (Criteria) this;
        }

        public Criteria andTtypeNotEqualTo(Integer value) {
            addCriterion("Ttype <>", value, "ttype");
            return (Criteria) this;
        }

        public Criteria andTtypeGreaterThan(Integer value) {
            addCriterion("Ttype >", value, "ttype");
            return (Criteria) this;
        }

        public Criteria andTtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("Ttype >=", value, "ttype");
            return (Criteria) this;
        }

        public Criteria andTtypeLessThan(Integer value) {
            addCriterion("Ttype <", value, "ttype");
            return (Criteria) this;
        }

        public Criteria andTtypeLessThanOrEqualTo(Integer value) {
            addCriterion("Ttype <=", value, "ttype");
            return (Criteria) this;
        }

        public Criteria andTtypeIn(List<Integer> values) {
            addCriterion("Ttype in", values, "ttype");
            return (Criteria) this;
        }

        public Criteria andTtypeNotIn(List<Integer> values) {
            addCriterion("Ttype not in", values, "ttype");
            return (Criteria) this;
        }

        public Criteria andTtypeBetween(Integer value1, Integer value2) {
            addCriterion("Ttype between", value1, value2, "ttype");
            return (Criteria) this;
        }

        public Criteria andTtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("Ttype not between", value1, value2, "ttype");
            return (Criteria) this;
        }

        public Criteria andEmpformsIsNull() {
            addCriterion("EmpForms is null");
            return (Criteria) this;
        }

        public Criteria andEmpformsIsNotNull() {
            addCriterion("EmpForms is not null");
            return (Criteria) this;
        }

        public Criteria andEmpformsEqualTo(Integer value) {
            addCriterion("EmpForms =", value, "empforms");
            return (Criteria) this;
        }

        public Criteria andEmpformsNotEqualTo(Integer value) {
            addCriterion("EmpForms <>", value, "empforms");
            return (Criteria) this;
        }

        public Criteria andEmpformsGreaterThan(Integer value) {
            addCriterion("EmpForms >", value, "empforms");
            return (Criteria) this;
        }

        public Criteria andEmpformsGreaterThanOrEqualTo(Integer value) {
            addCriterion("EmpForms >=", value, "empforms");
            return (Criteria) this;
        }

        public Criteria andEmpformsLessThan(Integer value) {
            addCriterion("EmpForms <", value, "empforms");
            return (Criteria) this;
        }

        public Criteria andEmpformsLessThanOrEqualTo(Integer value) {
            addCriterion("EmpForms <=", value, "empforms");
            return (Criteria) this;
        }

        public Criteria andEmpformsIn(List<Integer> values) {
            addCriterion("EmpForms in", values, "empforms");
            return (Criteria) this;
        }

        public Criteria andEmpformsNotIn(List<Integer> values) {
            addCriterion("EmpForms not in", values, "empforms");
            return (Criteria) this;
        }

        public Criteria andEmpformsBetween(Integer value1, Integer value2) {
            addCriterion("EmpForms between", value1, value2, "empforms");
            return (Criteria) this;
        }

        public Criteria andEmpformsNotBetween(Integer value1, Integer value2) {
            addCriterion("EmpForms not between", value1, value2, "empforms");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffIsNull() {
            addCriterion("PoliticalAff is null");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffIsNotNull() {
            addCriterion("PoliticalAff is not null");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffEqualTo(Integer value) {
            addCriterion("PoliticalAff =", value, "politicalaff");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffNotEqualTo(Integer value) {
            addCriterion("PoliticalAff <>", value, "politicalaff");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffGreaterThan(Integer value) {
            addCriterion("PoliticalAff >", value, "politicalaff");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffGreaterThanOrEqualTo(Integer value) {
            addCriterion("PoliticalAff >=", value, "politicalaff");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffLessThan(Integer value) {
            addCriterion("PoliticalAff <", value, "politicalaff");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffLessThanOrEqualTo(Integer value) {
            addCriterion("PoliticalAff <=", value, "politicalaff");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffIn(List<Integer> values) {
            addCriterion("PoliticalAff in", values, "politicalaff");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffNotIn(List<Integer> values) {
            addCriterion("PoliticalAff not in", values, "politicalaff");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffBetween(Integer value1, Integer value2) {
            addCriterion("PoliticalAff between", value1, value2, "politicalaff");
            return (Criteria) this;
        }

        public Criteria andPoliticalaffNotBetween(Integer value1, Integer value2) {
            addCriterion("PoliticalAff not between", value1, value2, "politicalaff");
            return (Criteria) this;
        }

        public Criteria andPartytimeIsNull() {
            addCriterion("PartyTime is null");
            return (Criteria) this;
        }

        public Criteria andPartytimeIsNotNull() {
            addCriterion("PartyTime is not null");
            return (Criteria) this;
        }

        public Criteria andPartytimeEqualTo(String value) {
            addCriterion("PartyTime =", value, "partytime");
            return (Criteria) this;
        }

        public Criteria andPartytimeNotEqualTo(String value) {
            addCriterion("PartyTime <>", value, "partytime");
            return (Criteria) this;
        }

        public Criteria andPartytimeGreaterThan(String value) {
            addCriterion("PartyTime >", value, "partytime");
            return (Criteria) this;
        }

        public Criteria andPartytimeGreaterThanOrEqualTo(String value) {
            addCriterion("PartyTime >=", value, "partytime");
            return (Criteria) this;
        }

        public Criteria andPartytimeLessThan(String value) {
            addCriterion("PartyTime <", value, "partytime");
            return (Criteria) this;
        }

        public Criteria andPartytimeLessThanOrEqualTo(String value) {
            addCriterion("PartyTime <=", value, "partytime");
            return (Criteria) this;
        }

        public Criteria andPartytimeLike(String value) {
            addCriterion("PartyTime like", value, "partytime");
            return (Criteria) this;
        }

        public Criteria andPartytimeNotLike(String value) {
            addCriterion("PartyTime not like", value, "partytime");
            return (Criteria) this;
        }

        public Criteria andPartytimeIn(List<String> values) {
            addCriterion("PartyTime in", values, "partytime");
            return (Criteria) this;
        }

        public Criteria andPartytimeNotIn(List<String> values) {
            addCriterion("PartyTime not in", values, "partytime");
            return (Criteria) this;
        }

        public Criteria andPartytimeBetween(String value1, String value2) {
            addCriterion("PartyTime between", value1, value2, "partytime");
            return (Criteria) this;
        }

        public Criteria andPartytimeNotBetween(String value1, String value2) {
            addCriterion("PartyTime not between", value1, value2, "partytime");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("Address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("Address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("Address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("Address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("Address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("Address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("Address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("Address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("Address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("Address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("Address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("Address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("Address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("Address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andHomephoneIsNull() {
            addCriterion("HomePhone is null");
            return (Criteria) this;
        }

        public Criteria andHomephoneIsNotNull() {
            addCriterion("HomePhone is not null");
            return (Criteria) this;
        }

        public Criteria andHomephoneEqualTo(String value) {
            addCriterion("HomePhone =", value, "homephone");
            return (Criteria) this;
        }

        public Criteria andHomephoneNotEqualTo(String value) {
            addCriterion("HomePhone <>", value, "homephone");
            return (Criteria) this;
        }

        public Criteria andHomephoneGreaterThan(String value) {
            addCriterion("HomePhone >", value, "homephone");
            return (Criteria) this;
        }

        public Criteria andHomephoneGreaterThanOrEqualTo(String value) {
            addCriterion("HomePhone >=", value, "homephone");
            return (Criteria) this;
        }

        public Criteria andHomephoneLessThan(String value) {
            addCriterion("HomePhone <", value, "homephone");
            return (Criteria) this;
        }

        public Criteria andHomephoneLessThanOrEqualTo(String value) {
            addCriterion("HomePhone <=", value, "homephone");
            return (Criteria) this;
        }

        public Criteria andHomephoneLike(String value) {
            addCriterion("HomePhone like", value, "homephone");
            return (Criteria) this;
        }

        public Criteria andHomephoneNotLike(String value) {
            addCriterion("HomePhone not like", value, "homephone");
            return (Criteria) this;
        }

        public Criteria andHomephoneIn(List<String> values) {
            addCriterion("HomePhone in", values, "homephone");
            return (Criteria) this;
        }

        public Criteria andHomephoneNotIn(List<String> values) {
            addCriterion("HomePhone not in", values, "homephone");
            return (Criteria) this;
        }

        public Criteria andHomephoneBetween(String value1, String value2) {
            addCriterion("HomePhone between", value1, value2, "homephone");
            return (Criteria) this;
        }

        public Criteria andHomephoneNotBetween(String value1, String value2) {
            addCriterion("HomePhone not between", value1, value2, "homephone");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("Mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("Mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("Mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("Mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("Mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("Mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("Mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("Mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("Mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("Mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("Mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("Mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("Mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("Mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("Email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("Email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("Email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("Email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("Email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("Email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("Email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("Email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("Email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("Email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("Email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("Email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("Email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("Email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andMaxeducationIsNull() {
            addCriterion("MaxEducation is null");
            return (Criteria) this;
        }

        public Criteria andMaxeducationIsNotNull() {
            addCriterion("MaxEducation is not null");
            return (Criteria) this;
        }

        public Criteria andMaxeducationEqualTo(String value) {
            addCriterion("MaxEducation =", value, "maxeducation");
            return (Criteria) this;
        }

        public Criteria andMaxeducationNotEqualTo(String value) {
            addCriterion("MaxEducation <>", value, "maxeducation");
            return (Criteria) this;
        }

        public Criteria andMaxeducationGreaterThan(String value) {
            addCriterion("MaxEducation >", value, "maxeducation");
            return (Criteria) this;
        }

        public Criteria andMaxeducationGreaterThanOrEqualTo(String value) {
            addCriterion("MaxEducation >=", value, "maxeducation");
            return (Criteria) this;
        }

        public Criteria andMaxeducationLessThan(String value) {
            addCriterion("MaxEducation <", value, "maxeducation");
            return (Criteria) this;
        }

        public Criteria andMaxeducationLessThanOrEqualTo(String value) {
            addCriterion("MaxEducation <=", value, "maxeducation");
            return (Criteria) this;
        }

        public Criteria andMaxeducationLike(String value) {
            addCriterion("MaxEducation like", value, "maxeducation");
            return (Criteria) this;
        }

        public Criteria andMaxeducationNotLike(String value) {
            addCriterion("MaxEducation not like", value, "maxeducation");
            return (Criteria) this;
        }

        public Criteria andMaxeducationIn(List<String> values) {
            addCriterion("MaxEducation in", values, "maxeducation");
            return (Criteria) this;
        }

        public Criteria andMaxeducationNotIn(List<String> values) {
            addCriterion("MaxEducation not in", values, "maxeducation");
            return (Criteria) this;
        }

        public Criteria andMaxeducationBetween(String value1, String value2) {
            addCriterion("MaxEducation between", value1, value2, "maxeducation");
            return (Criteria) this;
        }

        public Criteria andMaxeducationNotBetween(String value1, String value2) {
            addCriterion("MaxEducation not between", value1, value2, "maxeducation");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyIsNull() {
            addCriterion("MaxSpecialty is null");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyIsNotNull() {
            addCriterion("MaxSpecialty is not null");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyEqualTo(String value) {
            addCriterion("MaxSpecialty =", value, "maxspecialty");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyNotEqualTo(String value) {
            addCriterion("MaxSpecialty <>", value, "maxspecialty");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyGreaterThan(String value) {
            addCriterion("MaxSpecialty >", value, "maxspecialty");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyGreaterThanOrEqualTo(String value) {
            addCriterion("MaxSpecialty >=", value, "maxspecialty");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyLessThan(String value) {
            addCriterion("MaxSpecialty <", value, "maxspecialty");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyLessThanOrEqualTo(String value) {
            addCriterion("MaxSpecialty <=", value, "maxspecialty");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyLike(String value) {
            addCriterion("MaxSpecialty like", value, "maxspecialty");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyNotLike(String value) {
            addCriterion("MaxSpecialty not like", value, "maxspecialty");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyIn(List<String> values) {
            addCriterion("MaxSpecialty in", values, "maxspecialty");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyNotIn(List<String> values) {
            addCriterion("MaxSpecialty not in", values, "maxspecialty");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyBetween(String value1, String value2) {
            addCriterion("MaxSpecialty between", value1, value2, "maxspecialty");
            return (Criteria) this;
        }

        public Criteria andMaxspecialtyNotBetween(String value1, String value2) {
            addCriterion("MaxSpecialty not between", value1, value2, "maxspecialty");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeIsNull() {
            addCriterion("MaxDegree is null");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeIsNotNull() {
            addCriterion("MaxDegree is not null");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeEqualTo(String value) {
            addCriterion("MaxDegree =", value, "maxdegree");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeNotEqualTo(String value) {
            addCriterion("MaxDegree <>", value, "maxdegree");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeGreaterThan(String value) {
            addCriterion("MaxDegree >", value, "maxdegree");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeGreaterThanOrEqualTo(String value) {
            addCriterion("MaxDegree >=", value, "maxdegree");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeLessThan(String value) {
            addCriterion("MaxDegree <", value, "maxdegree");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeLessThanOrEqualTo(String value) {
            addCriterion("MaxDegree <=", value, "maxdegree");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeLike(String value) {
            addCriterion("MaxDegree like", value, "maxdegree");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeNotLike(String value) {
            addCriterion("MaxDegree not like", value, "maxdegree");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeIn(List<String> values) {
            addCriterion("MaxDegree in", values, "maxdegree");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeNotIn(List<String> values) {
            addCriterion("MaxDegree not in", values, "maxdegree");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeBetween(String value1, String value2) {
            addCriterion("MaxDegree between", value1, value2, "maxdegree");
            return (Criteria) this;
        }

        public Criteria andMaxdegreeNotBetween(String value1, String value2) {
            addCriterion("MaxDegree not between", value1, value2, "maxdegree");
            return (Criteria) this;
        }

        public Criteria andMandarinleIsNull() {
            addCriterion("MandarinLe is null");
            return (Criteria) this;
        }

        public Criteria andMandarinleIsNotNull() {
            addCriterion("MandarinLe is not null");
            return (Criteria) this;
        }

        public Criteria andMandarinleEqualTo(Integer value) {
            addCriterion("MandarinLe =", value, "mandarinle");
            return (Criteria) this;
        }

        public Criteria andMandarinleNotEqualTo(Integer value) {
            addCriterion("MandarinLe <>", value, "mandarinle");
            return (Criteria) this;
        }

        public Criteria andMandarinleGreaterThan(Integer value) {
            addCriterion("MandarinLe >", value, "mandarinle");
            return (Criteria) this;
        }

        public Criteria andMandarinleGreaterThanOrEqualTo(Integer value) {
            addCriterion("MandarinLe >=", value, "mandarinle");
            return (Criteria) this;
        }

        public Criteria andMandarinleLessThan(Integer value) {
            addCriterion("MandarinLe <", value, "mandarinle");
            return (Criteria) this;
        }

        public Criteria andMandarinleLessThanOrEqualTo(Integer value) {
            addCriterion("MandarinLe <=", value, "mandarinle");
            return (Criteria) this;
        }

        public Criteria andMandarinleIn(List<Integer> values) {
            addCriterion("MandarinLe in", values, "mandarinle");
            return (Criteria) this;
        }

        public Criteria andMandarinleNotIn(List<Integer> values) {
            addCriterion("MandarinLe not in", values, "mandarinle");
            return (Criteria) this;
        }

        public Criteria andMandarinleBetween(Integer value1, Integer value2) {
            addCriterion("MandarinLe between", value1, value2, "mandarinle");
            return (Criteria) this;
        }

        public Criteria andMandarinleNotBetween(Integer value1, Integer value2) {
            addCriterion("MandarinLe not between", value1, value2, "mandarinle");
            return (Criteria) this;
        }

        public Criteria andEnglishproIsNull() {
            addCriterion("EnglishPro is null");
            return (Criteria) this;
        }

        public Criteria andEnglishproIsNotNull() {
            addCriterion("EnglishPro is not null");
            return (Criteria) this;
        }

        public Criteria andEnglishproEqualTo(Integer value) {
            addCriterion("EnglishPro =", value, "englishpro");
            return (Criteria) this;
        }

        public Criteria andEnglishproNotEqualTo(Integer value) {
            addCriterion("EnglishPro <>", value, "englishpro");
            return (Criteria) this;
        }

        public Criteria andEnglishproGreaterThan(Integer value) {
            addCriterion("EnglishPro >", value, "englishpro");
            return (Criteria) this;
        }

        public Criteria andEnglishproGreaterThanOrEqualTo(Integer value) {
            addCriterion("EnglishPro >=", value, "englishpro");
            return (Criteria) this;
        }

        public Criteria andEnglishproLessThan(Integer value) {
            addCriterion("EnglishPro <", value, "englishpro");
            return (Criteria) this;
        }

        public Criteria andEnglishproLessThanOrEqualTo(Integer value) {
            addCriterion("EnglishPro <=", value, "englishpro");
            return (Criteria) this;
        }

        public Criteria andEnglishproIn(List<Integer> values) {
            addCriterion("EnglishPro in", values, "englishpro");
            return (Criteria) this;
        }

        public Criteria andEnglishproNotIn(List<Integer> values) {
            addCriterion("EnglishPro not in", values, "englishpro");
            return (Criteria) this;
        }

        public Criteria andEnglishproBetween(Integer value1, Integer value2) {
            addCriterion("EnglishPro between", value1, value2, "englishpro");
            return (Criteria) this;
        }

        public Criteria andEnglishproNotBetween(Integer value1, Integer value2) {
            addCriterion("EnglishPro not between", value1, value2, "englishpro");
            return (Criteria) this;
        }

        public Criteria andWorktimeIsNull() {
            addCriterion("WorkTime is null");
            return (Criteria) this;
        }

        public Criteria andWorktimeIsNotNull() {
            addCriterion("WorkTime is not null");
            return (Criteria) this;
        }

        public Criteria andWorktimeEqualTo(Date value) {
            addCriterionForJDBCDate("WorkTime =", value, "worktime");
            return (Criteria) this;
        }

        public Criteria andWorktimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("WorkTime <>", value, "worktime");
            return (Criteria) this;
        }

        public Criteria andWorktimeGreaterThan(Date value) {
            addCriterionForJDBCDate("WorkTime >", value, "worktime");
            return (Criteria) this;
        }

        public Criteria andWorktimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("WorkTime >=", value, "worktime");
            return (Criteria) this;
        }

        public Criteria andWorktimeLessThan(Date value) {
            addCriterionForJDBCDate("WorkTime <", value, "worktime");
            return (Criteria) this;
        }

        public Criteria andWorktimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("WorkTime <=", value, "worktime");
            return (Criteria) this;
        }

        public Criteria andWorktimeIn(List<Date> values) {
            addCriterionForJDBCDate("WorkTime in", values, "worktime");
            return (Criteria) this;
        }

        public Criteria andWorktimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("WorkTime not in", values, "worktime");
            return (Criteria) this;
        }

        public Criteria andWorktimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("WorkTime between", value1, value2, "worktime");
            return (Criteria) this;
        }

        public Criteria andWorktimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("WorkTime not between", value1, value2, "worktime");
            return (Criteria) this;
        }

        public Criteria andTsubjectIsNull() {
            addCriterion("TSubject is null");
            return (Criteria) this;
        }

        public Criteria andTsubjectIsNotNull() {
            addCriterion("TSubject is not null");
            return (Criteria) this;
        }

        public Criteria andTsubjectEqualTo(String value) {
            addCriterion("TSubject =", value, "tsubject");
            return (Criteria) this;
        }

        public Criteria andTsubjectNotEqualTo(String value) {
            addCriterion("TSubject <>", value, "tsubject");
            return (Criteria) this;
        }

        public Criteria andTsubjectGreaterThan(String value) {
            addCriterion("TSubject >", value, "tsubject");
            return (Criteria) this;
        }

        public Criteria andTsubjectGreaterThanOrEqualTo(String value) {
            addCriterion("TSubject >=", value, "tsubject");
            return (Criteria) this;
        }

        public Criteria andTsubjectLessThan(String value) {
            addCriterion("TSubject <", value, "tsubject");
            return (Criteria) this;
        }

        public Criteria andTsubjectLessThanOrEqualTo(String value) {
            addCriterion("TSubject <=", value, "tsubject");
            return (Criteria) this;
        }

        public Criteria andTsubjectLike(String value) {
            addCriterion("TSubject like", value, "tsubject");
            return (Criteria) this;
        }

        public Criteria andTsubjectNotLike(String value) {
            addCriterion("TSubject not like", value, "tsubject");
            return (Criteria) this;
        }

        public Criteria andTsubjectIn(List<String> values) {
            addCriterion("TSubject in", values, "tsubject");
            return (Criteria) this;
        }

        public Criteria andTsubjectNotIn(List<String> values) {
            addCriterion("TSubject not in", values, "tsubject");
            return (Criteria) this;
        }

        public Criteria andTsubjectBetween(String value1, String value2) {
            addCriterion("TSubject between", value1, value2, "tsubject");
            return (Criteria) this;
        }

        public Criteria andTsubjectNotBetween(String value1, String value2) {
            addCriterion("TSubject not between", value1, value2, "tsubject");
            return (Criteria) this;
        }

        public Criteria andTpoststatueIsNull() {
            addCriterion("TPostStatue is null");
            return (Criteria) this;
        }

        public Criteria andTpoststatueIsNotNull() {
            addCriterion("TPostStatue is not null");
            return (Criteria) this;
        }

        public Criteria andTpoststatueEqualTo(String value) {
            addCriterion("TPostStatue =", value, "tpoststatue");
            return (Criteria) this;
        }

        public Criteria andTpoststatueNotEqualTo(String value) {
            addCriterion("TPostStatue <>", value, "tpoststatue");
            return (Criteria) this;
        }

        public Criteria andTpoststatueGreaterThan(String value) {
            addCriterion("TPostStatue >", value, "tpoststatue");
            return (Criteria) this;
        }

        public Criteria andTpoststatueGreaterThanOrEqualTo(String value) {
            addCriterion("TPostStatue >=", value, "tpoststatue");
            return (Criteria) this;
        }

        public Criteria andTpoststatueLessThan(String value) {
            addCriterion("TPostStatue <", value, "tpoststatue");
            return (Criteria) this;
        }

        public Criteria andTpoststatueLessThanOrEqualTo(String value) {
            addCriterion("TPostStatue <=", value, "tpoststatue");
            return (Criteria) this;
        }

        public Criteria andTpoststatueLike(String value) {
            addCriterion("TPostStatue like", value, "tpoststatue");
            return (Criteria) this;
        }

        public Criteria andTpoststatueNotLike(String value) {
            addCriterion("TPostStatue not like", value, "tpoststatue");
            return (Criteria) this;
        }

        public Criteria andTpoststatueIn(List<String> values) {
            addCriterion("TPostStatue in", values, "tpoststatue");
            return (Criteria) this;
        }

        public Criteria andTpoststatueNotIn(List<String> values) {
            addCriterion("TPostStatue not in", values, "tpoststatue");
            return (Criteria) this;
        }

        public Criteria andTpoststatueBetween(String value1, String value2) {
            addCriterion("TPostStatue between", value1, value2, "tpoststatue");
            return (Criteria) this;
        }

        public Criteria andTpoststatueNotBetween(String value1, String value2) {
            addCriterion("TPostStatue not between", value1, value2, "tpoststatue");
            return (Criteria) this;
        }

        public Criteria andTlevelIsNull() {
            addCriterion("TLevel is null");
            return (Criteria) this;
        }

        public Criteria andTlevelIsNotNull() {
            addCriterion("TLevel is not null");
            return (Criteria) this;
        }

        public Criteria andTlevelEqualTo(Integer value) {
            addCriterion("TLevel =", value, "tlevel");
            return (Criteria) this;
        }

        public Criteria andTlevelNotEqualTo(Integer value) {
            addCriterion("TLevel <>", value, "tlevel");
            return (Criteria) this;
        }

        public Criteria andTlevelGreaterThan(Integer value) {
            addCriterion("TLevel >", value, "tlevel");
            return (Criteria) this;
        }

        public Criteria andTlevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("TLevel >=", value, "tlevel");
            return (Criteria) this;
        }

        public Criteria andTlevelLessThan(Integer value) {
            addCriterion("TLevel <", value, "tlevel");
            return (Criteria) this;
        }

        public Criteria andTlevelLessThanOrEqualTo(Integer value) {
            addCriterion("TLevel <=", value, "tlevel");
            return (Criteria) this;
        }

        public Criteria andTlevelIn(List<Integer> values) {
            addCriterion("TLevel in", values, "tlevel");
            return (Criteria) this;
        }

        public Criteria andTlevelNotIn(List<Integer> values) {
            addCriterion("TLevel not in", values, "tlevel");
            return (Criteria) this;
        }

        public Criteria andTlevelBetween(Integer value1, Integer value2) {
            addCriterion("TLevel between", value1, value2, "tlevel");
            return (Criteria) this;
        }

        public Criteria andTlevelNotBetween(Integer value1, Integer value2) {
            addCriterion("TLevel not between", value1, value2, "tlevel");
            return (Criteria) this;
        }

        public Criteria andImagesrcIsNull() {
            addCriterion("ImageSrc is null");
            return (Criteria) this;
        }

        public Criteria andImagesrcIsNotNull() {
            addCriterion("ImageSrc is not null");
            return (Criteria) this;
        }

        public Criteria andImagesrcEqualTo(String value) {
            addCriterion("ImageSrc =", value, "imagesrc");
            return (Criteria) this;
        }

        public Criteria andImagesrcNotEqualTo(String value) {
            addCriterion("ImageSrc <>", value, "imagesrc");
            return (Criteria) this;
        }

        public Criteria andImagesrcGreaterThan(String value) {
            addCriterion("ImageSrc >", value, "imagesrc");
            return (Criteria) this;
        }

        public Criteria andImagesrcGreaterThanOrEqualTo(String value) {
            addCriterion("ImageSrc >=", value, "imagesrc");
            return (Criteria) this;
        }

        public Criteria andImagesrcLessThan(String value) {
            addCriterion("ImageSrc <", value, "imagesrc");
            return (Criteria) this;
        }

        public Criteria andImagesrcLessThanOrEqualTo(String value) {
            addCriterion("ImageSrc <=", value, "imagesrc");
            return (Criteria) this;
        }

        public Criteria andImagesrcLike(String value) {
            addCriterion("ImageSrc like", value, "imagesrc");
            return (Criteria) this;
        }

        public Criteria andImagesrcNotLike(String value) {
            addCriterion("ImageSrc not like", value, "imagesrc");
            return (Criteria) this;
        }

        public Criteria andImagesrcIn(List<String> values) {
            addCriterion("ImageSrc in", values, "imagesrc");
            return (Criteria) this;
        }

        public Criteria andImagesrcNotIn(List<String> values) {
            addCriterion("ImageSrc not in", values, "imagesrc");
            return (Criteria) this;
        }

        public Criteria andImagesrcBetween(String value1, String value2) {
            addCriterion("ImageSrc between", value1, value2, "imagesrc");
            return (Criteria) this;
        }

        public Criteria andImagesrcNotBetween(String value1, String value2) {
            addCriterion("ImageSrc not between", value1, value2, "imagesrc");
            return (Criteria) this;
        }

        public Criteria andCreateuserIsNull() {
            addCriterion("CreateUser is null");
            return (Criteria) this;
        }

        public Criteria andCreateuserIsNotNull() {
            addCriterion("CreateUser is not null");
            return (Criteria) this;
        }

        public Criteria andCreateuserEqualTo(Integer value) {
            addCriterion("CreateUser =", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotEqualTo(Integer value) {
            addCriterion("CreateUser <>", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserGreaterThan(Integer value) {
            addCriterion("CreateUser >", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserGreaterThanOrEqualTo(Integer value) {
            addCriterion("CreateUser >=", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLessThan(Integer value) {
            addCriterion("CreateUser <", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserLessThanOrEqualTo(Integer value) {
            addCriterion("CreateUser <=", value, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserIn(List<Integer> values) {
            addCriterion("CreateUser in", values, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotIn(List<Integer> values) {
            addCriterion("CreateUser not in", values, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserBetween(Integer value1, Integer value2) {
            addCriterion("CreateUser between", value1, value2, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreateuserNotBetween(Integer value1, Integer value2) {
            addCriterion("CreateUser not between", value1, value2, "createuser");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("CreateTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("CreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterionForJDBCDate("CreateTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("CreateTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterionForJDBCDate("CreateTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CreateTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterionForJDBCDate("CreateTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("CreateTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterionForJDBCDate("CreateTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("CreateTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CreateTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("CreateTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andIsauditingIsNull() {
            addCriterion("IsAuditing is null");
            return (Criteria) this;
        }

        public Criteria andIsauditingIsNotNull() {
            addCriterion("IsAuditing is not null");
            return (Criteria) this;
        }

        public Criteria andIsauditingEqualTo(Integer value) {
            addCriterion("IsAuditing =", value, "isauditing");
            return (Criteria) this;
        }

        public Criteria andIsauditingNotEqualTo(Integer value) {
            addCriterion("IsAuditing <>", value, "isauditing");
            return (Criteria) this;
        }

        public Criteria andIsauditingGreaterThan(Integer value) {
            addCriterion("IsAuditing >", value, "isauditing");
            return (Criteria) this;
        }

        public Criteria andIsauditingGreaterThanOrEqualTo(Integer value) {
            addCriterion("IsAuditing >=", value, "isauditing");
            return (Criteria) this;
        }

        public Criteria andIsauditingLessThan(Integer value) {
            addCriterion("IsAuditing <", value, "isauditing");
            return (Criteria) this;
        }

        public Criteria andIsauditingLessThanOrEqualTo(Integer value) {
            addCriterion("IsAuditing <=", value, "isauditing");
            return (Criteria) this;
        }

        public Criteria andIsauditingIn(List<Integer> values) {
            addCriterion("IsAuditing in", values, "isauditing");
            return (Criteria) this;
        }

        public Criteria andIsauditingNotIn(List<Integer> values) {
            addCriterion("IsAuditing not in", values, "isauditing");
            return (Criteria) this;
        }

        public Criteria andIsauditingBetween(Integer value1, Integer value2) {
            addCriterion("IsAuditing between", value1, value2, "isauditing");
            return (Criteria) this;
        }

        public Criteria andIsauditingNotBetween(Integer value1, Integer value2) {
            addCriterion("IsAuditing not between", value1, value2, "isauditing");
            return (Criteria) this;
        }

        public Criteria andIsnormalIsNull() {
            addCriterion("IsNormal is null");
            return (Criteria) this;
        }

        public Criteria andIsnormalIsNotNull() {
            addCriterion("IsNormal is not null");
            return (Criteria) this;
        }

        public Criteria andIsnormalEqualTo(Integer value) {
            addCriterion("IsNormal =", value, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalNotEqualTo(Integer value) {
            addCriterion("IsNormal <>", value, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalGreaterThan(Integer value) {
            addCriterion("IsNormal >", value, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalGreaterThanOrEqualTo(Integer value) {
            addCriterion("IsNormal >=", value, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalLessThan(Integer value) {
            addCriterion("IsNormal <", value, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalLessThanOrEqualTo(Integer value) {
            addCriterion("IsNormal <=", value, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalIn(List<Integer> values) {
            addCriterion("IsNormal in", values, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalNotIn(List<Integer> values) {
            addCriterion("IsNormal not in", values, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalBetween(Integer value1, Integer value2) {
            addCriterion("IsNormal between", value1, value2, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalNotBetween(Integer value1, Integer value2) {
            addCriterion("IsNormal not between", value1, value2, "isnormal");
            return (Criteria) this;
        }

        public Criteria andGxtimeIsNull() {
            addCriterion("GXtime is null");
            return (Criteria) this;
        }

        public Criteria andGxtimeIsNotNull() {
            addCriterion("GXtime is not null");
            return (Criteria) this;
        }

        public Criteria andGxtimeEqualTo(Date value) {
            addCriterionForJDBCDate("GXtime =", value, "gxtime");
            return (Criteria) this;
        }

        public Criteria andGxtimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("GXtime <>", value, "gxtime");
            return (Criteria) this;
        }

        public Criteria andGxtimeGreaterThan(Date value) {
            addCriterionForJDBCDate("GXtime >", value, "gxtime");
            return (Criteria) this;
        }

        public Criteria andGxtimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("GXtime >=", value, "gxtime");
            return (Criteria) this;
        }

        public Criteria andGxtimeLessThan(Date value) {
            addCriterionForJDBCDate("GXtime <", value, "gxtime");
            return (Criteria) this;
        }

        public Criteria andGxtimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("GXtime <=", value, "gxtime");
            return (Criteria) this;
        }

        public Criteria andGxtimeIn(List<Date> values) {
            addCriterionForJDBCDate("GXtime in", values, "gxtime");
            return (Criteria) this;
        }

        public Criteria andGxtimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("GXtime not in", values, "gxtime");
            return (Criteria) this;
        }

        public Criteria andGxtimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("GXtime between", value1, value2, "gxtime");
            return (Criteria) this;
        }

        public Criteria andGxtimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("GXtime not between", value1, value2, "gxtime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}