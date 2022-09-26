<div align=center><h1>확장성을 가진 채팅 서버 구축</h1></div>

<br>

## 🛠Server Architecture
<img width="600" alt="Server Architecture" src="https://user-images.githubusercontent.com/87802191/191989034-6573f7d5-a9dd-433d-94f7-0e2774ad5a77.png">

<br>

## 🚗Tech Stacks
<table>
	<tr><th rowspan="7">🗄️Back-end</th><td>Language</td><td>Java 11</td></tr>
	<tr><td>Framework</td><td>Spring Boot 2.7.3</td></tr>
	<tr><td>ORM</td><td>Spring Data JPA</td></tr>
	<tr><td>Messaging</td><td>WebSocket, Kafka</td></tr>
	<tr><td>API Documentation</td><td>Swagger</td></tr>
	<tr><td>Database</td><td>MySQL</td></tr>
	<tr><td>Test</td><td>JUnit5, Mockito</td></tr>
	<tr><th rowspan="3">🤝Collaboration</th><td>API Test</td><td>Postman</td></tr>
	<tr><td>Communication</td><td>Notion, Gather</td></tr>
	<tr><td>Version Control</td><td>Git, Github</td></tr>
	<tr><th>☁️AWS Cloud Service</th><td colspan="2">EC2, S3, CodeDeploy, RDS, ELB</td></tr>
</table>

<br>

## 🌱ERD
<img width="700" alt="ERD" src="https://user-images.githubusercontent.com/87802191/191988157-3d0ece0e-fa96-4aa3-9598-d0056b1c1a96.png">

<br>

## 💻Server Deployment Environment
<img width="600" alt="서버 배포" src="https://user-images.githubusercontent.com/87802191/191987945-ee649cb4-27e4-4699-9470-315a92cce65a.png">

<br>

## 📨Messaging Process
- 예) 사용자 A가 1번 방에 입장하여 메시지를 전송하는 경우
<img width="800" alt="메시지 전달 과정" src="https://user-images.githubusercontent.com/87802191/191988542-15a0ac50-b0d3-4dae-9eb2-83dd609f0794.png">
