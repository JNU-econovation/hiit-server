# MEMBER API

## 멤버의 프로필 정보를 조회합니다.

### 기본 정보

| Method | URL                  | 인증방식   |
|--------|----------------------|--------|
| GET    | /api/v1/members/{id} | 액세스 토큰 |

### 요청

#### 헤더

| 이름            | 설명                                                               | 필수 |
|---------------|------------------------------------------------------------------|----|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} 인증 방식, <br> 액세스 토큰으로 인증 요청 | O  |

#### 경로 변수

| 이름 | 타입   | 설명    | 필수 |
|----|------|-------|----|
| id | Long | 멤버 Id | O  |

### 응답

#### 본문

| 이름         | 타입         | 설명      | 필수 |
|------------|------------|---------|----|
| code       | String     | success | O  |
| message    | String     | 성공      | O  |
| memberInfo | memberInfo | 멤버 정보   |    |

#### memberInfo

| 이름          | 타입     | 설명         | 필수 |
|-------------|--------|------------|----|
| id          | Long   | 멤버 Id      | O  |
| name        | String | 멤버 이름      | O  |
| profile     | String | 멤버 프로필 사진  | O  |
| in_it_count | Long   | 참여 중인 잇의 수 | O  |

## 멤버의 잇 참여 통계를 조회합니다.

### 기본 정보

| Method | URL                             | 인증방식   |
|--------|---------------------------------|--------|
| GET    | /api/v1/members/stats/it?id&iid | 액세스 토큰 |

### 요청

#### 헤더

| 이름            | 설명                                                               | 필수 |
|---------------|------------------------------------------------------------------|----|
| Authorization | Authorization: Bearer ${ACCESS_TOKEN} 인증 방식, <br> 액세스 토큰으로 인증 요청 | O  |

#### 쿼리 파라미터

| 이름  | 타입   | 설명         | 필수 |
|-----|------|------------|----|
| id  | Long | 멤버 Id      | O  |
| iid | Long | 참여 중인 잇 Id | O  |

### 예외

| 상황                    | http_code | code               | message                |
|-----------------------|-----------|--------------------|------------------------|
| 잇 Id는 음수일 수 없다.       | 400       | request.id.invalid | 잘못된 요청입니다.             |
| 멤버 Id는 음수일 수 없다.      | 400       | request.id.invalid | 잘못된 요청입니다.             |
| 타인의 잇 접근하는 경우         | 403       | access.denied      | 접근 권한이 없어요.            |
| 잇 Id는 존재하는 Id이어야 한다.  | 404       | resource.notFound  | 요청과 일치하는 결과를 찾을 수 없어요. |
| 멤버 Id는 존재하는 Id이어야 한다. | 404       | resource.notFound  | 요청과 일치하는 결과를 찾을 수 없어요. |

### 응답

#### 본문

| 이름      | 타입     | 설명      | 필수 |
|---------|--------|---------|----|
| code    | String | success | O  |
| message | String | 성공      | O  |
| itInfo  | itInfo | 잇 정보    | O  |

#### itInfo

| 이름         | 타입   | 설명   | 필수 |
|------------|------|------|----|
| id         | Long | 잇 Id | O  |
| with_count | Long | 윗 수  | O  |

## 로그인, 회원가입을 수행합니다.

### 기본 정보

| Method | URL             | 인증방식 |
|--------|-----------------|------|
| POST   | /api/v1/members | X    |

### 요청

#### 본문

| 이름            | 타입     | 설명       | 필수 |
|---------------|--------|----------|----|
| code          | String | 소셜 인증 코드 | O  |
| socialSubject | String | 소셜 종류    | O  |

### 응답

#### 본문

| 이름            | 타입            | 설명      | 필수 |
|---------------|---------------|---------|----|
| code          | String        | success | O  |
| message       | String        | 성공      | O  |
| userAuthToken | userAuthToken | 토큰 정보   | O  |

#### userAuthToken

| 이름           | 타입     | 설명         | 필수 |
|--------------|--------|------------|----|
| accessToken  | String | access 토큰  | O  |
| refreshToken | String | refresh 토큰 | O  |

## 토큰을 갱신합니다.

### 기본 정보

| Method | URL                   | 인증방식 |
|--------|-----------------------|------|
| POST   | /api/v1/members/token | X    |

### 요청

#### 본문

| 이름           | 타입     | 설명         | 필수 |
|--------------|--------|------------|----|
| refreshToken | String | refresh 토큰 | O  |

### 응답

#### 본문

| 이름            | 타입            | 설명      | 필수 |
|---------------|---------------|---------|----|
| code          | String        | success | O  |
| message       | String        | 성공      | O  |
| userAuthToken | userAuthToken | 토큰 정보   | O  |
