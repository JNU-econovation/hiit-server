# EXCEPTIONS

| 상황                      | http_code | code                |
|-------------------------|-----------|---------------------|
| 정의되지 않은 오류인 경우          | 400       | fail                |
| 인증되지 않은 경우              | 401       | fail.authentication |
| 서버 내부에서 처리할 코드가 없는 경우   | 404       | fail.notfound       |
| 유효하지 않은 요청인 경우          | 400       | request.invalid     |
| 유효하지 않은 파라미터 포함한 요청인 경우 | 400       | request.파라미터.invalid |
| 접근이 제한된 경우              | 403       | access.denied       |
| 요청에 대한 자원이 없는 경우        | 404       | resource.notfound   |
| 요청에 대한 자원이 삭제된 경우       | 404       | resource.deleted    |
| 서버 내부 오류인 경우            | 500       | fail                |
