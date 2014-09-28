package com.eastteam.community.entity;


/**
 * 用于数据库里面有自增字段的entity， 在配置Mybatis的mapping文件时， 
 * 设置：useGeneratedKeys="true" keyProperty="id"，可以在insert的时候
 * 得到该对象的ID；
 * 
 * @author lichlei
 *
 */
public abstract class IdEntity extends BaseEntity{

	protected Long id;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
