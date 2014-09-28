package com.eastteam.community.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

/**
 * 分页功能基类
 * Controller只需要调用getCurrentPageContent方法，得到Page对象，就可在页面实现分页
 * @author lichlei
 *
 * @param <T>
 */
public abstract class PageableService<T> {
	
	/**
	 * 查询当前页面结果集
	 * @param parameters
	 * @param pageRequest
	 * @return
	 */
	public abstract List<T> search(Map parameters, Pageable pageRequest);

	/**
	 * 
	 * @param parameters
	 * @param pageNumber 第几页,第一页为1，第二页为2...
	 * @param pageSize 每页显示的记录数
	 * @param sort 排序字段
	 * @return
	 */
	public Page<T> getCurrentPageContent(Map parameters, int pageNumber, int pageSize, String sort) {
		Pageable pageRequest = new PageRequest(pageNumber-1, pageSize, new Sort(sort));
		List<T> list = search(parameters, pageRequest);
		Long count = getCount(parameters);
		Page<T> contents = new PageImpl<T>(list, pageRequest, count);
		
		return contents;
	}

	/**
	 * 结果总数
	 * @param parameters mybatis接收的输入参数
	 * @return 符合查询条件的总数
	 */
	public abstract Long getCount(Map parameters);
	
	/**
	 * 目前在Sort.order里面只保存了一个排序用对象，所以只取第一个的value。如有需要，今后可以扩展。
	 */
	protected String getOrderValue(Sort sort) {
		if (sort == null)
			return null;
		
		Iterator<Order> iterator = sort.iterator();
		String orderBy = null;
		if (iterator.hasNext()) {
			Order order = iterator.next();
			orderBy = order.getProperty();
		}
		
		return orderBy;		
	}

}
