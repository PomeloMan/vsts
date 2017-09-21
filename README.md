# vsts
Java code for Visual Studio Team Services

### WIQL

  `public String getWiqlUrl(String area, String project, String version) {
		Asserts.notNull(area, "Area can't be null.");
		Asserts.notNull(project, "Project can't be null.");
		return String
				.format("https://%s.visualstudio.com/DefaultCollection/%s_apis/wit/wiql?api-version=%s",
						area, project + '/', version != null ? version : "1.0");
	}`
