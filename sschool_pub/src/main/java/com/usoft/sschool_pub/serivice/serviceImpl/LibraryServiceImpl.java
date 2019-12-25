package com.usoft.sschool_pub.serivice.serviceImpl;

import com.usoft.smartschool.pojo.BAttachfile;
import com.usoft.smartschool.pojo.BAttachfileExample;
import com.usoft.smartschool.pojo.BDictionary;
import com.usoft.smartschool.pojo.BDictionaryExample;
import com.usoft.smartschool.util.MyResult;
import com.usoft.smartschool.util.ObjectUtil;
import com.usoft.sschool_pub.mapper.BAttachfileMapper;
import com.usoft.sschool_pub.mapper.BDictionaryMapper;
import com.usoft.sschool_pub.serivice.LibraryService;
import com.usoft.sschool_pub.util.ResultPage;
import com.usoft.sschool_pub.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("LibraryService")
public class LibraryServiceImpl implements LibraryService {
    @Resource
    private BAttachfileMapper bAttachfileMapper;
    @Resource
    private BDictionaryMapper bDictionaryMapper;
    /**
     * 查询所有图书
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public MyResult allBooks(Integer pageNo, Integer pageSize,String bookName,Integer conditionId) {
        if ( pageSize==null){
            pageSize=10;
        }
        if(pageNo==null){
            pageNo=1;
        }
        List<BAttachfile> bAttachfiles=null;
        int totalNum=0;
        if (conditionId==null || "".equals(conditionId) || "null".equals(conditionId)){
            if (bookName==null || "".equals(bookName) || "null".equals(bookName)){
                bAttachfiles = bAttachfileMapper.allBooks((pageNo-1)*pageSize,pageSize);
                BAttachfileExample example=new BAttachfileExample();
                example.createCriteria();
                totalNum = bAttachfileMapper.countByExample(example);
            }else {
                bAttachfiles=bAttachfileMapper.searchByName("%"+bookName+"%");
                totalNum=bAttachfiles.size();
            }

        }else {
            if (bookName==null || "".equals(bookName) || "null".equals(bookName)){
                BAttachfileExample example=new BAttachfileExample();
                example.createCriteria().andDataIdEqualTo(conditionId);
                bAttachfiles=bAttachfileMapper.selectByExample(example);
                totalNum=bAttachfileMapper.countByExample(example);
            }else {
                bAttachfiles=bAttachfileMapper.searchByCondition("%"+bookName+"%",conditionId);
                totalNum=bAttachfiles.size();
            }
        }
        int Pages=totalNum/pageSize;
        int totalPage=totalNum%pageSize==0 ? Pages : Pages+1;
        if (ObjectUtil.isEmpty(bAttachfiles))return MyResult.failure("没找到图书信息");
        List<Map> list=new ArrayList<>();
        for (BAttachfile baf:bAttachfiles){
            Map map=new HashMap();
            map.put("FileCategory",baf.getFilecategory());
            map.put("FilePath",baf.getFilepath());
            map.put("FileVType",baf.getFilevtype());
            map.put("Attach_Name",baf.getAttachName());
            map.put("Attach_Path",baf.getAttachPath());
            BDictionary bDictionary = bDictionaryMapper.selectByPrimaryKey(baf.getDataId());
            map.put("Data_ID",bDictionary.getDictname());
            map.put("conditionId",baf.getDataId());
            map.put("UploadTime", TimeUtil.TimeToYYYYMMDDHHMMSS(baf.getUploadtime()));
            Integer IsPreview=0;
            if (baf.getIspreview()!=null){
                IsPreview=baf.getIspreview();
            }
            map.put("IsPreview",IsPreview);
            list.add(map);
        }
        return MyResult.success("查询成功",list,pageNo,totalPage,pageSize,totalNum);
    }

    /**
     * 查询分类条件
     * @param parentId
     * @return
     */
    @Override
    public MyResult conditionClass(Integer parentId) {
        if (parentId==null){
            parentId=37;
        }
        BDictionaryExample example=new BDictionaryExample();
        example.createCriteria().andParentidEqualTo(parentId);
        List<BDictionary> bDictionaries = bDictionaryMapper.selectByExample(example);
        List<Map> list=new ArrayList<>();
        for (BDictionary bd:bDictionaries){
            Map map=new HashMap();
            map.put("conditionClass",bd.getDictname());
            map.put("id",bd.getDictid());
            list.add(map);
        }
        return MyResult.success(list);
    }
}
