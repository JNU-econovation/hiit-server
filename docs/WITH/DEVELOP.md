# WITH DEVELOP

## 윗을 작성합니다.

### 설계

1. 윗을 작성하려는 잇이 멤버가 참여 중인 잇인지 확인합니다.
2. 멤버가 이미 잇을 작성하였는지 확인합니다.
3. 잇의 제한 사항에 따라 윗을 작성할 수 있는지 확인합니다.
4. 윗을 작성합니다.

### 예외

| 관련 번호 | 처리                                    |
|-------|---------------------------------------|
| 1     | 잇이 존재하지 않으면 예외를 발생시킨다.                |
| 1     | 멤버가 참여중인 잇이 아니면 예외를 발생시킨다.            |
| 2     | 잇의 시작 시간 이전과 종료 시간 이후의 요청은 예외를 발생시킨다. |
| 3     | 멤버가 이미 잇을 작성하였으면 예외를 발생시킨다.           |

### 특이사항

- 윗을 작성하기 위한 제한 사항이 존재한다.
    - 윗을 작성하기 위한 제한 사항은 잇의 시작 시간 이전과 종료 시간 이후이다.
    - 잇에 해당하는 윗은 하루에 하나만 작성할 수 있다.

### 쿼리

| 관련 번호 | 쿼리                                                             |
|-------|----------------------------------------------------------------|
| 1     | InitRepository#findByIdAndHiitMemberAndStatus                  |
| 2     | ItRelationRepository#findByInItId                              |
| 2     | RegisteredItRepository#findById                                |
| 3     | WithRepository#findByInItEntityAndHiitMemberAndCreateAtBetween |

## 윗을 삭제합니다.

### 설계

1. 삭제하려는 윗이 멤버가 작성한 윗인지 확인합니다.
2. 잇의 제한 사항에 따라 윗을 삭제할 수 있는지 확인합니다.
3. 윗을 삭제합니다.

### 예외

| 관련 번호 | 처리                                    |
|-------|---------------------------------------|
| 1     | 윗이 존재하지 않으면 예외를 발생시킨다.                |
| 1     | 멤버가 작성한 윗이 아니면 예외를 발생시킨다.             |
| 2     | 잇의 시작 시간 이전과 종료 시간 이후의 요청은 예외를 발생시킨다. |

### 쿼리

| 관련 번호 | 쿼리                                           |
|-------|----------------------------------------------|
| 1     | WithRepository#findById                      |
| 2     | InItRepository#findActiveStatusByIdAndMember |
| 2     | ItRelationRepository#findByInItId            |
| 2     | RegisteredItRepository#findById              |
| 3     | WithRepository#delete                        |

## 윗 목록을 조회합니다.

### 설계

1. 잇 목록을 조회하려는 잇이 존재하는 참여하는 잇인지 확인합니다.
2. my가 true이면 조회하려는 잇이 멤버가 참여 중인 잇인지 확인합니다.
3. 윗 목록을 조회합니다.

### 예외

| 관련 번호 | 처리                                     |
|-------|----------------------------------------|
| 1     | 잇이 존재하지 않으면 예외를 발생시킨다.                 |
| 2     | my가 true이고 멤버가 참여 중인 잇이 아니면 예외를 발생시킨다. |

### 쿼리

| 관련 번호 | 쿼리                                                                    |
|-------|-----------------------------------------------------------------------|
| 1     | MemberRepository#findById                                             |
| 1     | InItRepository#findActiveStatusByIdAndMember                          |
| 3     | WithRepository#findAllByInIt or WithRepository#findAllByInItAndMember |
