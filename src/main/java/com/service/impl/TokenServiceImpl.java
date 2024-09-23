
package com.service.impl;


import java.util.*;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.TokenDao;
import com.entity.TokenEntity;
import com.entity.TokenEntity;
import com.service.TokenService;
import com.utils.CommonUtil;
import com.utils.PageUtils;
import com.utils.Query;


/**
 * token
 * @author
 */
@Service("tokenService")
public class TokenServiceImpl extends ServiceImpl<TokenDao, TokenEntity> implements TokenService {

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
		Page<TokenEntity> page = this.selectPage(
                new Query<TokenEntity>(params).getPage(),
                new EntityWrapper<TokenEntity>()
        );
        return new PageUtils(page);
	}

	@Override
	public List<TokenEntity> selectListView(Wrapper<TokenEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public PageUtils queryPage(Map<String, Object> params,
			Wrapper<TokenEntity> wrapper) {
		 Page<TokenEntity> page =new Query<TokenEntity>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	    	PageUtils pageUtil = new PageUtils(page);
	    	return pageUtil;
	}

	@Override
	public String generateToken(Integer userid,String username, String tableName, String role) {
		TokenEntity tokenEntity = this.selectOne(new EntityWrapper<TokenEntity>().eq("userid", userid).eq("role", role));
		String token = CommonUtil.getRandomString(32);
		Calendar cal = Calendar.getInstance();   
    	cal.setTime(new Date());   
    	cal.add(Calendar.HOUR_OF_DAY, 1);
		if(tokenEntity!=null) {
			tokenEntity.setToken(token);
			tokenEntity.setExpiratedtime(cal.getTime());
			this.updateById(tokenEntity);
		} else {
			this.insert(new TokenEntity(userid,username, tableName, role, token, cal.getTime()));
		}
		return token;
	}

	@Override
	public TokenEntity getTokenEntity(String token) {
		TokenEntity tokenEntity = this.selectOne(new EntityWrapper<TokenEntity>().eq("token", token));
		if(tokenEntity == null || tokenEntity.getExpiratedtime().getTime()<new Date().getTime()) {
			return null;
		}
		return tokenEntity;
	}
}
