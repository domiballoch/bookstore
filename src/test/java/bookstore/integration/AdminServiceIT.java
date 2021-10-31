package bookstore.integration;

import bookstore.dao.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class AdminServiceIT {

	private static final String ENDPOINT = "/rest";

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private BookRepository bookRepository;

	@Test
	public void shouldReturnAllBooks() {
		webTestClient.get()
				.uri(ENDPOINT + "/findAllBooks")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.length()").isNotEmpty();
	}

	@Test
	public void shouldReturnBookByIsbn() {
		webTestClient.get()
				.uri(ENDPOINT + "/findBookByIsbn/{1}", 1)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
//				.expectBody()
//				.jsonPath("$.isbn").isEqualTo(1)
//				.jsonPath("$.category").isEqualTo(1)
//				.jsonPath("$.title").isEqualTo(1)
//				.jsonPath("$.author").isEqualTo(1)
		;
	}

	//examples

	//for post -
	// .contentType(MediaType.APPLICATION_JSON
	// .body(Mono.just(json), String.class)

	//from web

//	  this.webTestClient
//			  .get()
//			  .uri("/api/users")
//  .header(ACCEPT,APPLICATION_JSON_VALUE)
//  .exchange()
//  .expectStatus()
//  .is2xxSuccessful()
//  .expectHeader()
//  .contentType(APPLICATION_JSON)
//  .expectBody()
//  .jsonPath("$.length()").isEqualTo(3)
//  .jsonPath("$[0].id").isEqualTo(1)
//  .jsonPath("$[0].name").isEqualTo("duke")
//  .jsonPath("$[0].tags").isNotEmpty();

}
