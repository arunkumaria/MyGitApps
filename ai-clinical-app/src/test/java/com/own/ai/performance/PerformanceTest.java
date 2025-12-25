package com.own.ai.performance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.own.ai.service.DocumentService;

@SpringBootTest
class PerformanceTest {

	@Autowired
	private DocumentService documentService;

	@Test
	@Disabled("Enable for performance testing")
	void testConcurrentDocumentAddition() throws InterruptedException {
		// Given
		int numThreads = 10;
		int documentsPerThread = 10;
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);
		CountDownLatch latch = new CountDownLatch(numThreads);

		long startTime = System.currentTimeMillis();

		// When
		for (int i = 0; i < numThreads; i++) {
			int threadId = i;
			executor.submit(() -> {
				try {
					for (int j = 0; j < documentsPerThread; j++) {
						documentService.addDocument("Document from thread " + threadId + ", doc " + j,
								Map.of("thread", threadId));
					}
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await(30, TimeUnit.SECONDS);
		executor.shutdown();

		long duration = System.currentTimeMillis() - startTime;

		// Then
		System.out.println("Added " + (numThreads * documentsPerThread) + " documents in " + duration + "ms");
		assertThat(duration).isLessThan(30000);
	}

	@Test
	@Disabled("Enable for performance testing")
	void testSearchPerformance() {
		// Given - add 100 documents
		for (int i = 0; i < 100; i++) {
			documentService.addDocument("Clinical note " + i, Map.of());
		}

		// When
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 50; i++) {
			documentService.search("clinical");
		}
		long duration = System.currentTimeMillis() - startTime;

		// Then
		System.out.println("50 searches took " + duration + "ms");
		assertThat(duration).isLessThan(10000); // Should complete within 10 seconds
	}
}
