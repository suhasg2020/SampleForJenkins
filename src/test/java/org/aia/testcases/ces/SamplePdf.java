package org.aia.testcases.ces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SamplePdf {

	private static final String JENKINS_URL = "https://jenkins-preview.aia.org";
	private static final String USERNAME = "suhasg2020";
	private static final String API_TOKEN = "11533a892c89d3b4dc57df726dbf9034d1";

	public static String getBuildResults(String jobName) throws IOException {
		String apiUrl = JENKINS_URL + "/job/" + jobName + "/lastBuild/api/json";
		URL url = new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		String userCredentials = USERNAME + ":" + API_TOKEN;
		String basicAuth = "Basic " + java.util.Base64.getEncoder().encodeToString(userCredentials.getBytes());
		connection.setRequestProperty("Authorization", basicAuth);

		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} else {
			return null;
		}
	}

	public static void processBuildResult(String jobName, String jsonResult) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResult);
            JSONArray actionsArray = jsonObject.getJSONArray("actions");

            // Loop through each action object to find testngreports
            for (int i = 0; i < actionsArray.length(); i++) {
                JSONObject action = actionsArray.getJSONObject(i);
                System.out.println(action);
                if (action.has("testngreports")) {
                    JSONObject testngReports = action.getJSONObject("testngreports");
                    int passCount = testngReports.getInt("passCount");
                    int failCount = testngReports.getInt("failCount");
                    int skipCount = testngReports.getInt("skipCount");

                    System.out.println("Pass Count: " + passCount);
                    System.out.println("Fail Count: " + failCount);
                    System.out.println("Skip Count: " + skipCount);
                    return; // Found testngreports, so we can exit the loop
                }
            }
            // If testngreports not found
            System.out.println("No testngreports found.");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


	 

	public static void main(String[] args) throws IOException {
		List<String> jobs = new ArrayList<>();
		jobs.add("AIA_memberPortalSuite");
		jobs.add("AIA_chapterPortalSuite");

		for (String job : jobs) {
			String result = getBuildResults(job);
			if (result != null) {
				processBuildResult(job, result);
			} else {
				System.out.println("Failed to fetch build result for job: " + job);
			}
		}
	}
}
