package com.pactera.vsts.service;

import java.util.List;

import com.pactera.vsts.model.WorkItem;

public interface IVisualStudioService {

	/**
	 * @Description WIQL API
	 * @see https://www.visualstudio.com/en-us/docs/integrate/api/wit/wiql
	 * @param area 域
	 * @param project 项目名称
	 * @param version 版本 (Default 1.0)
	 * @return
	 */
	public String getWiqlUrl(String area, String project, String version);

	/**
	 * @Description WorkItem API
	 * @see https://www.visualstudio.com/en-us/docs/integrate/api/wit/work-items
	 * @param area 域
	 * @param id WorkItem ID
	 * @param version 版本 (Default 1.0)
	 * @return
	 */
	public String getWorkItemUrl(String area, String id, String version);

	/**
	 * @Description 根据查询语句查询结果
	 * @param url
	 * @param wiql
	 * @return
	 */
	public List<String> getWorkItemIdsByQuery(String url, String wiql);

	/**
	 * @Description Get 查询 Work item
	 * @param url
	 * @return
	 */
	public List<WorkItem> getWorkItems(String url);
}
