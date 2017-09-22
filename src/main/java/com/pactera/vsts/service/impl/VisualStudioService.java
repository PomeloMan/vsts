package com.pactera.vsts.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.Asserts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.pactera.util.FileUtil;
import com.pactera.vsts.model.WorkItem;
import com.pactera.vsts.service.IVisualStudioService;

public class VisualStudioService implements IVisualStudioService {

	private final static Logger logger = Logger
			.getLogger(VisualStudioService.class);

	private static String accessToken;
	private Gson gson = new GsonBuilder().create();

	static {
		ResourceBundle rb = ResourceBundle.getBundle("application");
		accessToken = rb.getString("vsts.access.token");
	}

	private CloseableHttpClient getHttpClient() {
		return HttpClientBuilder
				.create()
				.setDefaultHeaders(
						Arrays.asList(new BasicHeader("Authorization", "Basic"
								+ getCredentials(accessToken)),
								new BasicHeader("Content-Type",
										"application/json"))).build();
	}

	private String getCredentials(String privateKey) {
		Asserts.notNull(privateKey, "Credentials can't be null.");
		return Base64.getEncoder().encodeToString(
				String.format("%s:%s", "", privateKey).getBytes());
	}

	private List<String> fromJsonToWorkItemIds(String content) {
		List<String> workitems = new ArrayList<String>();
		JsonObject jo = gson.fromJson(content, new TypeToken<JsonObject>() {
		}.getType());
		JsonArray ja = jo.get("workItems") == null ? null : jo.get("workItems")
				.getAsJsonArray();
		if (ja != null) {
			for (JsonElement je : ja) {
				JsonObject wijo = je.getAsJsonObject();
				String id = wijo.get("id") != null ? wijo.get("id")
						.getAsString() : null;
				if (id != null) {
					workitems.add(id);
				}
			}
		}
		return workitems;
	}

	private List<WorkItem> fromJsonToWorkItems(String content) {
		List<WorkItem> workitems = new ArrayList<WorkItem>();
		JsonObject jo = gson.fromJson(content, new TypeToken<JsonObject>() {
		}.getType());
		JsonArray ja = jo.get("value") == null ? null : jo.get("value")
				.getAsJsonArray();
		if (ja != null) {
			// several
			for (JsonElement je : ja) {
				workitems.add(fromJsonToWorkItem(je));
			}
		} else {
			// single
			workitems.add(fromJsonToWorkItem(jo));
		}
		return workitems;
	}

	private WorkItem fromJsonToWorkItem(JsonElement je) {
		WorkItem wi = new WorkItem();

		JsonObject jo = je.getAsJsonObject();
		wi.setId(jo.get("id") != null ? jo.get("id").getAsString() : null);
		wi.setRev(jo.get("rev") != null ? jo.get("rev").getAsString() : null);
		wi.setUrl(jo.get("url") != null ? jo.get("url").getAsString() : null);

		/**
		 * fields
		 */
		JsonObject fieldsJo = jo.get("fields") != null ? jo.get("fields")
				.getAsJsonObject() : null;
		if (fieldsJo != null) {
			wi.setSystemAreaPath(fieldsJo.get("System.AreaPath") != null ? fieldsJo
					.get("System.AreaPath").getAsString() : null);
			wi.setSystemTeamProject(fieldsJo.get("System.TeamProject") != null ? fieldsJo
					.get("System.TeamProject").getAsString() : null);
			wi.setSystemIterationPath(fieldsJo.get("System.IterationPath") != null ? fieldsJo
					.get("System.IterationPath").getAsString() : null);
			wi.setSystemWorkItemType(fieldsJo.get("System.WorkItemType") != null ? fieldsJo
					.get("System.WorkItemType").getAsString() : null);
			wi.setSystemState(fieldsJo.get("System.State") != null ? fieldsJo
					.get("System.State").getAsString() : null);
			wi.setSystemReason(fieldsJo.get("System.Reason") != null ? fieldsJo
					.get("System.Reason").getAsString() : null);
			wi.setSystemCreatedDate(fieldsJo.get("System.CreatedDate") != null ? fieldsJo
					.get("System.CreatedDate").getAsString() : null);
			wi.setSystemCreatedBy(fieldsJo.get("System.CreatedBy") != null ? fieldsJo
					.get("System.CreatedBy").getAsString() : null);
			wi.setSystemChangedDate(fieldsJo.get("System.ChangedDate") != null ? fieldsJo
					.get("System.ChangedDate").getAsString() : null);
			wi.setSystemChangedBy(fieldsJo.get("System.ChangedBy") != null ? fieldsJo
					.get("System.ChangedBy").getAsString() : null);
			wi.setSystemTitle(fieldsJo.get("System.Title") != null ? fieldsJo
					.get("System.Title").getAsString() : null);
			wi.setSystemDescription(fieldsJo.get("System.Description") != null ? fieldsJo
					.get("System.Description").getAsString() : null);
			wi.setSystemTags(fieldsJo.get("System.Tags") != null ? fieldsJo
					.get("System.Tags").getAsString() : null);
			wi.setSystemAssignedTo(fieldsJo.get("System.AssignedTo") != null ? fieldsJo
					.get("System.AssignedTo").getAsString() : null);
		}
		/**
		 * relations
		 */
		JsonArray relationsJa = jo.get("relations") != null ? jo.get(
				"relations").getAsJsonArray() : null;
		if (relationsJa != null) {
			List<File> attachments = new ArrayList<File>();
			for (JsonElement relationsJe : relationsJa) {
				String name = null;
				JsonObject relationsJo = relationsJe.getAsJsonObject();
				JsonObject attributesJo = relationsJo.get("attributes") != null ? relationsJo
						.get("attributes").getAsJsonObject() : null;
				if (attributesJo != null) {
					name = attributesJo.get("name") != null ? attributesJo.get(
							"name").getAsString() : null;
				}
				String url = relationsJo.get("url") != null ? relationsJo.get(
						"url").getAsString() : null;
				File attachment = getFileAttachment(name, url);
				if (attachment != null) {
					attachments.add(attachment);
				}
			}
			wi.setAttachments(attachments);
		}
		/**
		 * _links
		 */
		JsonObject linksJo = jo.get("_links") != null ? jo.get("_links")
				.getAsJsonObject() : null;
		JsonElement hrefJe = null;
		if (linksJo != null) {
			hrefJe = linksJo.get("self") != null ? linksJo.get("self")
					.getAsJsonObject().get("href") : null;
			if (hrefJe != null) {
				wi.setLinksSelf(hrefJe.getAsString());
			}
			hrefJe = linksJo.get("workItemUpdates") != null ? linksJo
					.get("workItemUpdates").getAsJsonObject().get("href")
					: null;
			if (hrefJe != null) {
				wi.setLinksWorkItemUpdates(hrefJe.getAsString());
			}
			hrefJe = linksJo.get("workItemRevisions") != null ? linksJo
					.get("workItemRevisions").getAsJsonObject().get("href")
					: null;
			if (hrefJe != null) {
				wi.setLinksWorkItemRevisions(hrefJe.getAsString());
			}
			hrefJe = linksJo.get("workItemHistory") != null ? linksJo
					.get("workItemHistory").getAsJsonObject().get("href")
					: null;
			if (hrefJe != null) {
				wi.setLinksWorkItemHistory(hrefJe.getAsString());
			}
			hrefJe = linksJo.get("html") != null ? linksJo
					.get("html").getAsJsonObject().get("href")
					: null;
			if (hrefJe != null) {
				wi.setLinksHtml(hrefJe.getAsString());
			}
			hrefJe = linksJo.get("workItemType") != null ? linksJo
					.get("workItemType").getAsJsonObject().get("href")
					: null;
			if (hrefJe != null) {
				wi.setLinksWorkItemType(hrefJe.getAsString());
			}
			hrefJe = linksJo.get("fields") != null ? linksJo
					.get("fields").getAsJsonObject().get("href")
					: null;
			if (hrefJe != null) {
				wi.setLinksFields(hrefJe.getAsString());
			}
		}
		return wi;
	}

	private File getFileAttachment(String name, String url) {
		File temp = null;
		HttpGet httpRequest = new HttpGet(url);
		CloseableHttpClient httpclient = getHttpClient();
		try {
			temp = FileUtil.getRelativeTempFile(name);
			HttpResponse response = httpclient.execute(httpRequest);
			IOUtils.copy(response.getEntity().getContent(), new FileOutputStream(temp));
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(httpclient);
		}
		return temp;
	}

	public String getWiqlUrl(String area, String project, boolean expand,
			String version) {
		Asserts.notNull(area, "Area can't be null.");
		Asserts.notNull(project, "Project can't be null.");
		return String
				.format("https://%s.visualstudio.com/DefaultCollection/%s_apis/wit/wiql?$expand=%s&api-version=%s",
						area, project + '/', expand ? "all" : "none",
						version != null ? version : "1.0");
	}

	public String getWorkItemUrl(String area, String ids, boolean expand,
			String version) {
		Asserts.notNull(area, "Area can't be null.");
		Asserts.notNull(ids, "IDs can't be null.");
		if (ids.contains(",")) {
			// several
			return String
					.format("https://%s.visualstudio.com/DefaultCollection/_apis/wit/workitems?ids=%s&$expand=%s&api-version=%s",
							area, ids, expand ? "all" : "none",
							version != null ? version : "1.0");
		} else {
			// single
			return String
					.format("https://%s.visualstudio.com/DefaultCollection/_apis/wit/workitems/%s?$expand=%s&api-version=%s",
							area, ids, expand ? "all" : "none",
							version != null ? version : "1.0");
		}
	}

	public List<String> getWorkItemIdsByQuery(String url, String wiql) {
		List<String> ids = new ArrayList<String>();
		HttpPost httpRequest = new HttpPost(url);

		JsonObject jo = new JsonObject();
		jo.addProperty("query", wiql);
		httpRequest.setEntity(new StringEntity(gson.toJson(jo),
				ContentType.APPLICATION_JSON));

		CloseableHttpClient httpclient = getHttpClient();
		try {
			HttpResponse response = httpclient.execute(httpRequest);
			String content = EntityUtils.toString(response.getEntity());
			ids = fromJsonToWorkItemIds(content);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(httpclient);
		}
		return ids;
	}

	public List<WorkItem> getWorkItems(String url) {
		HttpGet httpRequest = new HttpGet(url);
		CloseableHttpClient httpclient = getHttpClient();
		try {
			HttpResponse response = httpclient.execute(httpRequest);
			String content = EntityUtils.toString(response.getEntity());
			return fromJsonToWorkItems(content);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(httpclient);
		}
		return null;
	}

}
