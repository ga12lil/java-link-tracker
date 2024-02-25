package edu.java.scrapper.httpclient;

public class ResponseBodyJson {
    public static String getGitHubResponse() {
        return """
                {
                    "id": 751890912,
                    "owner": {
                        "login": "ga12lil",
                        "id": 92907053
                    },
                    "created_at": "2024-02-02T14:55:32Z",
                    "updated_at": "2024-02-02T14:57:04Z"
                }
                        """;
    }
    public static String getStackExchangeResponse() {
        return """
            {
                "items": [
                    {
                        "last_activity_date": 1590400952,
                        "creation_date": 1217606932,
                        "last_edit_date": 1445938449,
                        "question_id": 5
                    }
                ]
            }
            """;
    }
}
