package utils;

import org.json.simple.JSONObject;

public class JSONObjects {

    private static String issueJson = createIssueJsonObject();
    private static String commentJson = createCommentJson();

    public static String getIssueJson(){
        return issueJson;
    }

    public static String getCommentJson(){
        return commentJson;
    }
    
    public static String createIssueJsonObject(){
        JSONObject issueJsonObject = new JSONObject();
        
        JSONObject fields = new JSONObject();
        issueJsonObject.put("fields", fields);
        fields.put("summary", "Test summary");

        JSONObject issueType = new JSONObject();
        fields.put("issuetype", issueType);
        issueType.put("id", "10105");
        issueType.put("name", "Test");

        JSONObject project = new JSONObject();
        fields.put("project", project);
        project.put("id", "10508");

        JSONObject reporter = new JSONObject();
        fields.put("reporter", reporter);
        reporter.put("name", "VyacheslavArtyomenko");
        
        return issueJsonObject.toJSONString();
    }

    
    public static String createCommentJson(){
        JSONObject commentJson = new JSONObject();
        
        JSONObject body = new JSONObject();
        commentJson.put("body", "Test comment");

        return commentJson.toJSONString();
    }
}
