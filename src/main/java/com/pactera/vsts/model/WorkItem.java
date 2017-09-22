package com.pactera.vsts.model;

import java.io.File;
import java.util.List;

public class WorkItem {

	/**
	 * _apis/wit/workitems?ids=1113959&api-version=1.0
	 */
	private String id;
	private String rev;
	private String systemAreaPath;
	private String systemTeamProject;
	private String systemIterationPath;
	private String systemWorkItemType;
	private String systemState;
	private String systemReason;
	private String systemCreatedDate;
	private String systemCreatedBy;
	private String systemChangedDate;
	private String systemChangedBy;
	private String systemTitle;
	private String systemDescription;
	private String systemTags;
	private String systemAssignedTo;
	private String url;

	/**
	 * _apis/wit/workitems/1113959?api-version=1.0
	 */
	private String systemHistory;
	private String linksSelf;
	private String linksWorkItemUpdates;
	private String linksWorkItemRevisions;
	private String linksWorkItemHistory;
	private String linksHtml;
	private String linksWorkItemType;
	private String linksFields;

	/**
	 * _apis/wit/workitems/1113959?$expand=all&api-version=1.0
	 */
	private List<File> attachments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public String getSystemAreaPath() {
		return systemAreaPath;
	}

	public void setSystemAreaPath(String systemAreaPath) {
		this.systemAreaPath = systemAreaPath;
	}

	public String getSystemTeamProject() {
		return systemTeamProject;
	}

	public void setSystemTeamProject(String systemTeamProject) {
		this.systemTeamProject = systemTeamProject;
	}

	public String getSystemIterationPath() {
		return systemIterationPath;
	}

	public void setSystemIterationPath(String systemIterationPath) {
		this.systemIterationPath = systemIterationPath;
	}

	public String getSystemWorkItemType() {
		return systemWorkItemType;
	}

	public void setSystemWorkItemType(String systemWorkItemType) {
		this.systemWorkItemType = systemWorkItemType;
	}

	public String getSystemState() {
		return systemState;
	}

	public void setSystemState(String systemState) {
		this.systemState = systemState;
	}

	public String getSystemReason() {
		return systemReason;
	}

	public void setSystemReason(String systemReason) {
		this.systemReason = systemReason;
	}

	public String getSystemCreatedDate() {
		return systemCreatedDate;
	}

	public void setSystemCreatedDate(String systemCreatedDate) {
		this.systemCreatedDate = systemCreatedDate;
	}

	public String getSystemCreatedBy() {
		return systemCreatedBy;
	}

	public void setSystemCreatedBy(String systemCreatedBy) {
		this.systemCreatedBy = systemCreatedBy;
	}

	public String getSystemChangedDate() {
		return systemChangedDate;
	}

	public void setSystemChangedDate(String systemChangedDate) {
		this.systemChangedDate = systemChangedDate;
	}

	public String getSystemChangedBy() {
		return systemChangedBy;
	}

	public void setSystemChangedBy(String systemChangedBy) {
		this.systemChangedBy = systemChangedBy;
	}

	public String getSystemTitle() {
		return systemTitle;
	}

	public void setSystemTitle(String systemTitle) {
		this.systemTitle = systemTitle;
	}

	public String getSystemDescription() {
		return systemDescription;
	}

	public void setSystemDescription(String systemDescription) {
		this.systemDescription = systemDescription;
	}

	public String getSystemTags() {
		return systemTags;
	}

	public void setSystemTags(String systemTags) {
		this.systemTags = systemTags;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSystemAssignedTo() {
		return systemAssignedTo;
	}

	public void setSystemAssignedTo(String systemAssignedTo) {
		this.systemAssignedTo = systemAssignedTo;
	}

	public String getSystemHistory() {
		return systemHistory;
	}

	public void setSystemHistory(String systemHistory) {
		this.systemHistory = systemHistory;
	}

	public String getLinksSelf() {
		return linksSelf;
	}

	public void setLinksSelf(String linksSelf) {
		this.linksSelf = linksSelf;
	}

	public String getLinksWorkItemUpdates() {
		return linksWorkItemUpdates;
	}

	public void setLinksWorkItemUpdates(String linksWorkItemUpdates) {
		this.linksWorkItemUpdates = linksWorkItemUpdates;
	}

	public String getLinksWorkItemRevisions() {
		return linksWorkItemRevisions;
	}

	public void setLinksWorkItemRevisions(String linksWorkItemRevisions) {
		this.linksWorkItemRevisions = linksWorkItemRevisions;
	}

	public String getLinksWorkItemHistory() {
		return linksWorkItemHistory;
	}

	public void setLinksWorkItemHistory(String linksWorkItemHistory) {
		this.linksWorkItemHistory = linksWorkItemHistory;
	}

	public String getLinksHtml() {
		return linksHtml;
	}

	public void setLinksHtml(String linksHtml) {
		this.linksHtml = linksHtml;
	}

	public String getLinksWorkItemType() {
		return linksWorkItemType;
	}

	public void setLinksWorkItemType(String linksWorkItemType) {
		this.linksWorkItemType = linksWorkItemType;
	}

	public String getLinksFields() {
		return linksFields;
	}

	public void setLinksFields(String linksFields) {
		this.linksFields = linksFields;
	}

	public List<File> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<File> attachments) {
		this.attachments = attachments;
	}

}
