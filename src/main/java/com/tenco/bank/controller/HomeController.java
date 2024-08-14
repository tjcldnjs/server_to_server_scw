package com.tenco.bank.controller;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tenco.bank.dto.Todo;

@RestController // @Controller + @ResponseBody IoC 대상
public class HomeController {

	// http://localhost:8080/m-todos/${id}
//	@GetMapping("/m-todos/{id}")
	// 와일드 카드 - 특정 타입에 제한되지 않고, 어떤 타입의 응답이 올 수 있음
	public ResponseEntity<?> restTest1(@PathVariable(name = "id") Integer id) {

		// RestTemplate 사용법
		// 1. URI 객체 설정
		URI uri = UriComponentsBuilder.fromUriString("https://jsonplaceholder.typicode.com/").path("/todos")
				.path("/" + id).build().toUri();
		// 2. 객체 생성
		// RestTemplate - RESTful 웹 서비스와의 통신을 간편하게 도와주는 클라이언트
		RestTemplate restTemplate = new RestTemplate();
		// getForEntity - URI에 GET요청을 보내고 ResponseEntity 객체로 반환
		// String.class - 응답 본문이 String 형식
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getHeaders());
		System.out.println(response.getBody());

		// ResponseEntity.status(HttpStatus.OK) - 상태코드 200을 의미, 요청이 성공적으로 처리되었음
		// body - 응답 본문 설정
		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
	}
	
	@GetMapping("/m-todos/{id}")
	// 와일드 카드 - 특정 타입에 제한되지 않고, 어떤 타입의 응답이 올 수 있음
	public ResponseEntity<?> restTest2(@PathVariable(name = "id") Integer id) {
		
		// RestTemplate 사용법
		// 1. URI 객체 설정
		URI uri = UriComponentsBuilder.fromUriString("https://jsonplaceholder.typicode.com/")
				.path("/todos")
				.path("/" + id)
				.build().toUri();
		
		// 2. 객체 생성
		// RestTemplate - RESTful 웹 서비스와의 통신을 간편하게 도와주는 클라이언트
		RestTemplate restTemplate = new RestTemplate();
		
		// getForEntity - URI에 GET요청을 보내고 ResponseEntity 객체로 반환
		// Todo.class - 응답 본문이 Todo 형식
		ResponseEntity<Todo> response = restTemplate.getForEntity(uri, Todo.class);
		
		System.out.println(response.getStatusCodeValue());
		System.out.println(response.getHeaders());
		System.out.println(response.getBody());
		
		// ResponseEntity.status(HttpStatus.OK) - 상태코드 200을 의미, 요청이 성공적으로 처리되었음
		// body - 응답 본문 설정
		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
	}
	
	@GetMapping("/exchange-test")
	public ResponseEntity<?> restChangeTest() {
		// 여기 주소는 리소스 서버 주소 설정을 해야 한다.
		URI uri = UriComponentsBuilder.fromUriString("https://jsonplaceholder.typicode.com/").path("/posts").build()
				.toUri();

		// 2. 객체 생성
		RestTemplate restTemplate1 = new RestTemplate();

		// 헤더 구성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json; charset=UTF-8");

		// 바디 구성
		// <String, String> - 키와 값의 타입 지정
		// LinkedMultiValueMap는 MutiValueMap 의 구현체중 하나이다, 삽입된 순서대로 값을 가져온다.
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("title", "제목");
		params.add("body", "내용");
		params.add("userId", "6");
		
		// 헤더와 바디 결합 --> HttpEntity Object
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

		// exchange - 지정된 URL로 HTTP 요청 보냄
		// uri - 요청을 보낼 대상 URL을 나타내는 문자열
		// HttpMethod.POST - POST방식을 사용
		// requestEntity - 헤더와 바디를 결합한 정보
		// String.class - 응답 본문을 String 타입으로 지정
		ResponseEntity<String> response = restTemplate1.exchange(uri, HttpMethod.POST, requestEntity, String.class);
		// ResponseEntity.status(HttpStatus.CREATE) - 상태코드 201을 의미, 요청이 성공적으로 처리되었음
		// body - 응답 본문 설정
		return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());

	}
}
