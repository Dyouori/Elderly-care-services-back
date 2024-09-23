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
import com.dao.YonghushenheDao;
import com.entity.YonghushenheEntity;
import com.service.YonghushenheService;
import com.entity.view.YonghushenheView;

/**
 * 用户信息修改审核 服务实现类
 */
@Service("yonghushenheService")
@Transactional
public class YonghushenheServiceImpl extends ServiceImpl<YonghushenheDao, YonghushenheEntity> implements YonghushenheService {

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
            System.out.println("-----------------在用这个查找筛选：" +fourthEntry.getValue());
        }
        if(params != null && (params.get("limit") == null || params.get("page") == null)){
            params.put("page","1");
            params.put("limit","10");
        }
        Page<YonghushenheView> page =new Query<YonghushenheView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}
