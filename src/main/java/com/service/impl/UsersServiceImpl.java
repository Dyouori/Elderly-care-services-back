
package com.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.service.UsersService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.UsersDao;
import com.entity.UsersEntity;
import com.utils.PageUtils;
import com.utils.Query;


/**
 * 系统用户
 * @author
 */
@Service("userService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
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
		Page<UsersEntity> page = this.selectPage(
                new Query<UsersEntity>(params).getPage(),
                new EntityWrapper<UsersEntity>()
        );
        return new PageUtils(page);
	}

	@Override
	public List<UsersEntity> selectListView(Wrapper<UsersEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params,
			Wrapper<UsersEntity> wrapper) {
		 Page<UsersEntity> page =new Query<UsersEntity>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
	}
}
