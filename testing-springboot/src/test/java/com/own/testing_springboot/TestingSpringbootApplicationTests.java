package com.own.testing_springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

// Extend test with Mockito support
@ExtendWith(MockitoExtension.class)
public class TestingSpringbootApplicationTests {
	// Dependency mock (fake implementation)
	@Mock
	private PaymentService paymentService;
	// Real object spy (keeps real behavior but can be stubbed)
	@Spy
	private List<String> items = new ArrayList<>();
	// Class under test, dependencies auto-injected
	@InjectMocks
	private ShoppingCart cart;

	@Test
	void testCheckoutSuccessful() {
		// Arrange - stub payment approval
		when(paymentService.processPayment(100)).thenReturn(true);

		// Spy allows us to use real add() but we can still verify calls
		items.add("Apple");
		items.add("Banana");
		// Act
		boolean result = cart.checkout(100);
		// Assert
		assertTrue(result);
		verify(paymentService).processPayment(100);
		verify(items, times(2)).add(anyString());
	}

	@Test
	void testCheckoutFailsDueToPayment() {
		// Arrange
		when(paymentService.processPayment(200)).thenReturn(false);
		items.add("Laptop");
		// Act
		boolean result = cart.checkout(200);
		// Assert
		assertFalse(result);
		verify(paymentService).processPayment(200);
	}
}

// ==== Production Classes ====
class ShoppingCart {
	private PaymentService paymentService;
	private List<String> items;

	public ShoppingCart(PaymentService paymentService, List<String> items) {
		this.paymentService = paymentService;
		this.items = items;
	}

	public boolean checkout(int amount) {
		if (items.isEmpty()) {
			return false;
		}
		return paymentService.processPayment(amount);
	}
}

interface PaymentService {
	boolean processPayment(int amount);
}


//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//// Runs tests with Mockito support
//@RunWith(MockitoJUnitRunner.class)
//public class TodoServiceImplMockTest {
//
//    private TodoServiceImpl todoBusiness;
//
//    // Mock object for TodoService
//    @Mock
//    private TodoService todoServiceMock;
//
//    // Runs before each test
//    @Before
//    public void setUp() {
//        todoBusiness = new TodoServiceImpl(todoServiceMock);
//    }
//
//    // Test case: when todos contain "Java"
//    @Test
//    public void testRetrieveTodosRelatedToJava_usingMock() {
//        List<String> todos = Arrays.asList("Learn Spring", "Learn Java", "Learn Spring Boot");
//        when(todoServiceMock.retrieveTodos("User")).thenReturn(todos);
//
//        List<String> filteredTodos = todoBusiness.retrieveTodosRelatedToJava("User");
//        assertEquals(1, filteredTodos.size()); // Only "Learn Java" matches
//    }
//
//    // Test case: when todos list is empty
//    @Test
//    public void testRetrieveTodosRelatedToJava_withEmptyList_usingMock() {
//        List<String> todos = Arrays.asList();
//        when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);
//
//        List<String> filteredTodos = todoBusiness.retrieveTodosRelatedToJava("Dummy");
//        assertEquals(0, filteredTodos.size());
//    }
//}


//import java.util.ArrayList;
//import java.util.List;
//
//public class TodoServiceImpl {
//
//    private TodoService todoService;
//
//    // Constructor
//    public TodoServiceImpl(TodoService todoService) {
//        this.todoService = todoService;
//    }
//
//    // Method to filter todos that contain the word "Java"
//    public List<String> retrieveTodosRelatedToJava(String user) {
//        List<String> filteredTodos = new ArrayList<>();
//        List<String> todos = todoService.retrieveTodos(user);
//
//        for (String todo : todos) {
//            if (todo.contains("Java")) {
//                filteredTodos.add(todo);
//            }
//        }
//        return filteredTodos;
//    }
//}
//
//
//import java.util.List;
//
//public interface TodoService {
//    // Method to retrieve todos for a given user
//    List<String> retrieveTodos(String user);
//}
