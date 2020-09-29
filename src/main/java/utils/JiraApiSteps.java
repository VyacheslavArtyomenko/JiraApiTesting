package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;


public class JiraApiSteps {

    private static String commentId;
    private static String issueJson = JSONObjects.getIssueJson();
    private static String commentJson = JSONObjects.getCommentJson();

    public static String getLastCreatedCommentId(){
        return commentId;
    }

    public static Response createIssue() {
        Response response =
                given().
                        auth().preemptive().basic(Credentials.username, Credentials.password).
                        contentType(ContentType.JSON).
                        body(issueJson).
                        when().
                        post(APIPathes.issue).
                        then().
                        statusCode(201).
                        contentType(ContentType.JSON).
                        extract().response();
        return response;
    }

    public static Response getIssue(String ticketId) {
        Response response =
                given().
                        auth().preemptive().basic(Credentials.username, Credentials.password).
                        contentType(ContentType.JSON).
                        when().
                        get(APIPathes.issue + ticketId).
                        then().
                        contentType(ContentType.JSON).
                        statusCode(200).
                        extract().response();
        return response;
    }

    public static Response deleteIssue(String ticketId) {
        Response response =
                given().
                        auth().preemptive().basic(Credentials.username, Credentials.password).
                        contentType(ContentType.JSON).
                        when().
                        delete(APIPathes.issue + ticketId).
                        then().
                        statusCode(204).
                        extract().response();
        return response;
    }

    public static Response getDeletedIssue(String ticketId) {
        Response response =
                given().
                        auth().preemptive().basic(Credentials.username, Credentials.password).
                        contentType(ContentType.JSON).
                        when().
                        get(APIPathes.issue + ticketId).
                        then().
                        statusCode(404).
                        extract().response();
        return response;
    }


    public static Response addComment() {
        Response response =
                given().
                        auth().preemptive().basic(Credentials.username, Credentials.password).
                        contentType(ContentType.JSON).
                        body(commentJson).
                        when().
                        post(String.format(APIPathes.comment, APIPathes.issueId)).
                        then().
                        contentType(ContentType.JSON).
                        time(lessThan(4000L)).
                        statusCode(201).
                        extract().response();

        commentId = response.path("id");

        return response;
    }

    public static Response deleteComment() {
        Response response =
                given().
                        auth().preemptive().basic(Credentials.username, Credentials.password).
                        contentType(ContentType.JSON).
                        when().
                        delete(String.format(APIPathes.comment, APIPathes.issueId).concat(commentId)).
                        then().
                        statusCode(204).
                        extract().response();
        return response;
    }

    public static Response isCommentDeleted() {
        Response response =
                given().
                        auth().preemptive().basic(Credentials.username, Credentials.password).
                        contentType(ContentType.JSON).
                        when().
                        get(String.format(APIPathes.comment, APIPathes.issueId).concat(commentId)).
                        then().
                        statusCode(404).
                        extract().response();
        return response;
    }

    public static Response isCommentFromSpecificIssueDeleted(String issueId, String commentId) {
        Response response =
                given().
                        auth().preemptive().basic(Credentials.username, Credentials.password).
                        contentType(ContentType.JSON).
                        when().
                        get(String.format(APIPathes.comment, issueId).concat(commentId)).
                        then().
                        statusCode(404).
                        extract().response();
        return response;
    }
}
