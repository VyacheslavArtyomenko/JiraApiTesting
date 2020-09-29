import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.APIPathes;
import utils.Credentials;
import utils.JiraApiSteps;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class JiraApiTests {

    @Test
    public void createAndDeleteIssue() {
        Response createIssueResponse = JiraApiSteps.createIssue();

        String ticketId = createIssueResponse.path("id");

        Response getIssueResponse = JiraApiSteps.getIssue(ticketId);
        assertTrue(getIssueResponse.path("key").toString().contains("WEBINAR-"));
        System.out.println(getIssueResponse.path("key").toString());


        assertEquals(getIssueResponse.path("fields.summary"), "Test summary");
        assertEquals(getIssueResponse.path("fields.creator.name"), Credentials.username);

        Response deleteIssueResponse = JiraApiSteps.deleteIssue(ticketId);
        Response checkDeletedIssueRespones = JiraApiSteps.getDeletedIssue(ticketId);
    }

    @Test
    public void addAndDeleteComment() {
        Response addCommentResponse = JiraApiSteps.addComment();
        Response deleteCommentResponse = JiraApiSteps.deleteComment();
        Response deletedCommentResponse = JiraApiSteps.isCommentDeleted();

        Response getIssueResponse = JiraApiSteps.
                isCommentFromSpecificIssueDeleted(APIPathes.issueId, JiraApiSteps.getLastCreatedCommentId());
    }

}
