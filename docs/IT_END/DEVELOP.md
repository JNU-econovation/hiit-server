# END IT DEVELOP

## 종료된 잇의 정보를 수정합니다.

### 설계

1. 수정하려는 잇이 종료된 잇인지 확인합니다.
2. 수정하려는 잇이 멤버가 작성한 잇인지 확인합니다.
3. 잇을 수정합니다.

### 예외

| 관련 번호 | 처리                        |
|-------|---------------------------|
| 1     | 잇이 존재하지 않으면 예외를 발생시킨다.    |
| 2     | 멤버가 작성한 잇이 아니면 예외를 발생시킨다. |

### 특이사항

- 종료된 잇의 경우 수정할 수 있는 정보에 제한이 있습니다.
    - 현재(v1)에서는 제목만 수정할 수 있습니다.

## 종료된 잇을 숨깁니다.

### 설계

1. 숨기려는 잇이 종료된 숨겨지지 않은 잇인지 확인합니다.
2. 숨기려는 잇이 멤버가 작성한 잇인지 확인합니다.
3. 잇을 숨깁니다.

### 예외

| 관련 번호 | 처리                        |
|-------|---------------------------|
| 1     | 잇이 존재하지 않으면 예외를 발생시킨다.    |
| 2     | 멤버가 작성한 잇이 아니면 예외를 발생시킨다. |

## 종료된 잇 목록을 조회합니다.

### 설계

1. 멤버의 종료된 잇 목록을 조회합니다.
2. 조회된 종료된 잇 목록을 반환합니다.

## 종료된 잇을 조회합니다.

### 설계

1. 조회하려는 잇이 종료된 잇인지 확인합니다.
2. 종료된 잇이 멤버가 작성한 잇인지 확인합니다.
3. 조회된 종료된 잇을 반환합니다.

### 예외

| 관련 번호 | 처리                        |
|-------|---------------------------|
| 1     | 잇이 존재하지 않으면 예외를 발생시킨다.    |
| 2     | 멤버가 작성한 잇이 아니면 예외를 발생시킨다. |

## 종료된 잇의 윗을 조회합니다.

### 설계

1. 조회하려는 잇이 종료된 잇인지 확인합니다.
2. 종료된 잇이 멤버가 작성한 잇인지 확인합니다.
3. 조회된 종료된 잇의 윗 목록을 조회합니다.
4. 조회된 윗 목록을 반환합니다.

### 예외

| 관련 번호 | 처리                        |
|-------|---------------------------|
| 1     | 잇이 존재하지 않으면 예외를 발생시킨다.    |
| 2     | 멤버가 작성한 잇이 아니면 예외를 발생시킨다. |