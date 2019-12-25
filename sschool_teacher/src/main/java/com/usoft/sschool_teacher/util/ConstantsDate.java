package com.usoft.sschool_teacher.util;

/**
 * @Author: 陈秋
 * @Date: 2019/5/8 13:47
 * @Version 1.0
 * 静态变量工具类
 */
public class ConstantsDate {
    private ConstantsDate(){
    }

    /**
     * 开始位置
     */
    public static final int begin_1 = 0;

    public static final byte ONLINE= 1;//在线答题
    public static final byte UPLOADING= 2;//上传

    public static final byte A = 1;//a选项
    public static final byte B = 2;//b选项
    public static final byte C = 3;//c选项
    public static final byte D = 4;//d选项

    public static final byte HOMEWORKSTATE1 = 1;//作业状态1.未做 2.已做已提交 3.已审批
    public static final byte HOMEWORKSTATE2 = 2;
    public static final byte HOMEWORKSTATE3 = 3;

    /**
     * 各类表
     */
    public static final String HLTEACHER = "hl_teacher";//教师表
    public static final String HLSTUDENTINFO = "hl_studentinfo";//学生表
    public static final String  XNHOMEWORKMANAGE = "xn_homework_manage";//作业表
    public static final String XNSTUHOMEWORK = "xn_stu_homework";//学生作业表
    public static final String HLSCHCLASS = "hl_schclass";//班级表

    /**
     * 是否删除
     */
    public static final byte DELETE_YES = 1;//删除
    public static final byte DELETE_ON = 2;//不删除

    /**
     * 属性
     */
    public static final byte TEACHER = 1;//老师
    public static final byte PARENTS = 2;//家长

    /**
     * 默认地址
     */
    public static final byte ADDRESS_IS = 1;//是
    public static final byte ADDRESS_NOIS = 2;//不是

    /**
     * 升序还是降序
     */
    public static final String ORDERBY_ASC = "ASC";//升
    public static final String ORDERBY_DESC = "DESC";//降

    /**
     * 请假状态
     */
    public static final byte ABSENT_POST = 2;//"审核通过"),
    public static final byte ABSENT_NO = 3;//"审核驳回"),
    public static final byte ABSENT_WAIT = 1;//"待审核"),

    /**
     * 学科
     */
    public static final int SUBJECT_LANGUAGE = 32;//"语文"),
    public static final int SUBJECT_MATH = 33;//"数学"),
    public static final int SUBJECT_ENGLISH = 37;//"英语"),
    public static final int SUBJECT_SPORTS = 34;//"体育"),
    public static final int SUBJECT_MUSIC = 35;//"音乐"),
    public static final int SUBJECT_PHYSICS = 211;//"物理"),
    public static final int SUBJECT_CHEMISTRY = 212;//"化学"),
    public static final int SUBJECT_BIOLOGY = 213;//"生物"),
    public static final int SUBJECT_HISTORY = 215;//"历史"),
    public static final int SUBJECT_POLITICS = 214;//"政治"),
    public static final int SUBJECT_GEOGRAPHY = 216;//"地理"),
    public static final int SUBJECT_FINE_ARTS = 36;//"美术"),
    public static final int SUBJECT_SCIENCE = 209;//"科学"),
    public static final int SUBJECT_IDEOLOGY_AND_MORALITY = 217;//"思想品德"),
    public static final int SUBJECT_GDJY = 225;//"道德与法治"),

    /**
     * 年级
     */
    public static final int CLASSYEAR1 = 20;//"一年级"),
    public static final int CLASSYEAR2 = 21;//"二年级"),
    public static final int CLASSYEAR3 = 22;//"三年级"),
    public static final int CLASSYEAR4 = 23;//"四年级"),
    public static final int CLASSYEAR5 = 24;//"五年级"),
    public static final int CLASSYEAR6 = 25;//"六年级"),
    public static final int CLASSYEAR7 = 26;//"七年级"),
    public static final int CLASSYEAR8 = 27;//"八年级"),
    public static final int CLASSYEAR9 = 28;//"九年级"),

    /**
     *  订单状态 1.待支付 2.已支付  3.已取消  4.已删除 , 9.已成功
     */
    public static final byte  ORDER_WAIT = 1;
    public static final byte  ORDER_PAID = 2;
    public static final byte  ORDER_CANC = 3;
    public static final byte  ORDER_DELETE = 4;
    public static final byte  ORDER_SUCCESS = 5;

    /**
     * 状态 1.待领 2.已领
     */
    public static final int KINDNESS_WAIT = 1;
    public static final int KINDNESS_POST = 2;

    /**
     * 消息用户属性
     */
    public static final int CENTER_TEACHER = 2;//老师
    public static final int CENTER_PARENTS = 1;//家长or学生

    /**
     * 消息是否已读
     */
    public static final int READ_IS = 1;//是
    public static final int READ_NO = 0;//否

    /**
     * 消息类型,1,一键放学，2，班级通知
     */
    public static final int MESSAGE_CLASS_OVER = 1;
    public static final int MESSAGE_CLASS_MESSAGE = 2;

    /**
     * 操作类型 1.班级圈查看 2.校园爱心
     */
    public static final int CLASS_CIRCLE = 1;
    public static final int SCHOOL_KIN = 2;

    /**
     * 班级圈 用户
     */
    public static final byte CIRCLE_TEACHER = 2;//教师
    public static  final byte CIRCLE_STUDENT = 1;//学生or家长

    /**
     * 积分是否开启 是否开启 1.是 2.否
     */
    public static final byte TOTAL_YES = 1;
    public static final byte TOTAL_NO = 2;

    /**
     * 积分获取类型 1.发布作业 2.班级圈审核 3.上传成绩 4.班级相册 5.请假审批
     */
    public static final int TOTAL_HOMEWORK = 1;
    public static final int TOTAL_CIRCLE = 2;
    public static final int TOTAL_SORCE = 3;
    public static final int TOTAL_PHOTOS = 4;
    public static final int TOTAL_PASS = 5;

    /**
     * 类型1，增加积分，2使用积分
     */
    public static final byte INSERT_INTEGRAL = 1;
    public static final byte USE_INTEGRAL = 2;

    /**
     *  饭点 1.早  2.中  3.晚
     */
    public static final byte MORNING = 1;
    public static final byte AFTERNOON = 2;
    public static final byte EVENING = 3;

    /**
     * 星期(0,1,2,3,4,5,6)
     */
    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    /**
     * 节假日判断 0.工作日，1.法定节假日，2.节假日调休补班，3.周末休息日
     */
    public static final int WORK_DAY = 0;
    public static final int WEEK_DAY_1 = 1;
    public static final int WORK_DAY_1 = 2;
    public static final int WEEK_DAY =3;

    /**
     * 2.修改头像，1.修改风采照,3.APP端
     */
    public static final int HEAR_PHOTO = 1;
    public static final int HEAR_PHOTOS = 2;
    public static final int HEAR_APP =3;

    /**
     * 课程表 第一节课到第十四节课
     */
    public static final int LESSON_1 = 1;
    public static final int LESSON_2 = 2;
    public static final int LESSON_3 = 3;
    public static final int LESSON_4 = 4;
    public static final int LESSON_5 = 5;
    public static final int LESSON_6 = 6;
    public static final int LESSON_7 = 7;
    public static final int LESSON_8 = 8;
    public static final int LESSON_9 = 9;
    public static final int LESSON_10 =10;
    public static final int LESSON_11 = 11;
    public static final int LESSON_12 = 12;
    public static final int LESSON_13 = 13;
    public static final int LESSON_14 = 14;

    /**
     * 请假人物属性 1.学生 2.教师
     */
    public static final byte ABSENT_STUDENT = 1;
    public static final byte ABSENT_TEACHER = 2;
}
