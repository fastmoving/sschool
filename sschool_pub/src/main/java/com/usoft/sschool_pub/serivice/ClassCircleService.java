package com.usoft.sschool_pub.serivice;

import com.usoft.smartschool.util.MyResult;

import javax.servlet.http.HttpServletRequest;

public interface ClassCircleService {
    //查询教师信息
    MyResult teacherInfo(Integer schoolId,Integer userId);
    //学生端查询班级圈
    MyResult searchClassCircle(Integer schoolId,Integer type,Integer classId,Integer pageNo,Integer pageSize);
    //我发布的班级圈
    MyResult myCricel(Integer pageNo,Integer pageSize);
    //班级圈详情
    MyResult ClassCircleInfo(Integer ccid,Integer schoolId);
    //评论班级圈
    MyResult addcomment(Integer schoolId,Integer ccid, String content, Integer parentId,Byte userType);
    //班级圈点赞
    MyResult addLike(Integer ccid,Integer schoolId,Integer type );
    //取消点赞
    MyResult deleteLike(Integer ccid,Integer schoolId, Integer type);
    //发布班级圈
    MyResult addClassCircle(Integer classId, Integer schoolId, Byte userType, HttpServletRequest request, String description);
    //删除班级圈
    MyResult deleteClassCircle(String ccid,Integer schoolId,Integer type);
    //上传班级圈图片
    MyResult addClassPic(Integer classId,HttpServletRequest request,String albumName,String imgName, Integer schoolId, Integer type);
    //查询班级圈照片
    MyResult searchClassPic(Integer classId,Integer schoolId,Integer type);
    //删除班级圈照片
    MyResult deleteClassPic(String cpid);

}
