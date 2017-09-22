package com.pactera.vsts.test;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.pactera.vsts.model.WorkItem;
import com.pactera.vsts.service.IVisualStudioService;
import com.pactera.vsts.service.impl.VisualStudioService;

public class VisualStudioServiceTest {

	@Test
	public void getWorkItemByIdsWithAttachment() {
		IVisualStudioService service = new VisualStudioService();
		String url = service.getWorkItemUrl("skype", "841831", true, "1.0");
		List<WorkItem> list = service.getWorkItems(url);
		System.out.println(list);
	}

	@Test
	public void getWorkItemByIds() {
		IVisualStudioService service = new VisualStudioService();
		String url = service.getWorkItemUrl("skype", "1113959,1146371", false, "1.0");
		List<WorkItem> list = service.getWorkItems(url);
		System.out.println(list);
	}

	@Test
	public void getWorkItemByWiql() {
		IVisualStudioService service = new VisualStudioService();
		String wiqlurl = service.getWiqlUrl("skype", "LOCALIZATION", false, "1.0");
		String wiql = "SELECT [System.Id],[System.WorkItemType],[System.Title],[System.AssignedTo],[System.State],[System.Tags] FROM WorkItems WHERE [System.TeamProject] = @project AND ( [System.AssignedTo] = 'Skype Loc Pactera VSTS <skypactv@microsoft.com>' AND [System.AreaPath] = 'LOCALIZATION\\Skype UA\\skype.com' OR [System.AreaPath] = 'LOCALIZATION\\Skype UA\\Varia' AND [System.Tags] CONTAINS 'icms' AND [System.CreatedDate] = @today)";
		List<String> idlist = service.getWorkItemIdsByQuery(wiqlurl, wiql);
		if (idlist != null && !idlist.isEmpty()) {
			String ids = idlist.stream().map((id) -> id = id + ",")
					.reduce((append, id) -> append + id).get();
			if (StringUtils.isNotEmpty(ids)) {
				String wiurl = service.getWorkItemUrl("skype",
						ids.substring(0, ids.length() - 1), false, "1.0");
				List<WorkItem> list = service.getWorkItems(wiurl);
				System.out.println(list);
			}
		}
	}
}
