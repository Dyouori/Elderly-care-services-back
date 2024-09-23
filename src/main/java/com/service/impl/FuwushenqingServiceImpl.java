package com.service.impl;

import com.utils.StringUtil;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import com.dao.FuwushenqingDao;
import com.entity.FuwushenqingEntity;
import com.service.FuwushenqingService;
import com.entity.view.FuwushenqingView;

/**
 * 服务申请 服务实现类
 */
@Service("fuwushenqingService")
@Transactional
public class FuwushenqingServiceImpl extends ServiceImpl<FuwushenqingDao, FuwushenqingEntity> implements FuwushenqingService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        // 将Map的entrySet转换成List
        List<Map.Entry<String, Object>> entryList = new ArrayList<>(params.entrySet());

        // 获取第四个数据项
        Map.Entry<String, Object> fourthEntry = null;
        if (entryList.size() > 3) { // 确保列表中至少有四个元素
            fourthEntry = entryList.get(3); // 索引是从0开始的，所以第四个元素的索引是3
        }

        // 输出第四个数据项
        if (fourthEntry != null) {
            System.out.println("------------------在用这个查找筛选：" +fourthEntry.getValue());
        }

        if(params != null && (params.get("limit") == null || params.get("page") == null)){
            params.put("page","1");
            params.put("limit","10");
        }
        Page<FuwushenqingView> page =new Query<FuwushenqingView>(params).getPage();

        page.setRecords(baseMapper.selectListView(page,params));

        return new PageUtils(page);
    }


}
