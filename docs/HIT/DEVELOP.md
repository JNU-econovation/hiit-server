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

| 관련 번호 | 쿼리                                                               |
|-------|------------------------------------------------------------------|
| 1     | WithRepository#findById                                          |
| 2     | HitRepository#findStatusByWithAndHitterAndStatusAndPeriodExecute |
| 3     | HitRepository#saveAndFlush                                       |
| 4     | HitRepository#countByWithEntityAndStatusAndCreateAtBetween       |

### 특이 사항

- hit 요청이 연속적인 경우 개선 할 수 있는 사항이 있을 것 가타.
- with에 상태가 추가되면 hit 요청 수행 여부를 적은 쿼리로 확인 할 수 있을 것 같다.
