# HIT DEVELOP

## 특정 윗을 힛하고 취소합니다.

### 설계

1. 힛을 하려는 윗이 존재하는 윗인지 확인합니다.
2. 윗이 속하는 잇의 제한 시간 내에 멤버가 이미 힛한 윗인지 확인합니다.
3. 멤버가 힛한 기록이 없으면 힛을 합니다. || 멤버가 힛한 기록이 있으면 힛을 취소합니다.
4. 해당 윗의 갱신된 힛 수를 반환합니다.

### 예외

| 관련 번호 | 처리                              |
|-------|---------------------------------|
| 1     | 윗이 존재하지 않으면 예외를 발생시킨다.          |
| 2     | 참여 잇 관련 기록이 존재하지 않으면 예외를 발생시킨다. |
| 2     | 잇이 존재하지 않으면 예외를 발생시킨다.          |

### 쿼리

| 관련 번호 | 쿼리                                                                 |
|-------|--------------------------------------------------------------------|
| 1     | WithRepository#findById                                            |
| 2     | ItRelationRepository#findByInItId                                  |
| 2     | RegisteredItRepository#findById                                    |
| 3     | HitRepository#findByWithEntityAndHitterAndStatusAndCreateAtBetween |
| 4     | HitRepository#countByWithEntityAndStatusAndCreateAtBetween         |

### 특이 사항

- 윗의 힛 수를 갱신할 때 동시성 문제 발생 가능성이 있다.
    - 힛 수를 count 쿼리를 통해 가져오기 때문에 갱신할 필요가 없어졌다.
- 힛 수행, 취소에 대한 기록은 힛 테이블에 저장한다.
    - rdms를 통해 기록하기 보다는 다른 방법을 찾아보는 것이 좋을 것 같다.
