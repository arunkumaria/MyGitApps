//package com.own;
//
////import org.junit.jupiter.api.Test;
////import org.springframework.boot.test.context.SpringBootTest;
////
////@SpringBootTest
////class MyHuggingFaceSpringAiApplicationTests {
////
////	@Test
////	void contextLoads() {
////	}
////
////}
////
////package com.example.ollama.service;
////
////import org.junit.jupiter.api.Test;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.own.service.ChatService;
//
//@SpringBootTest
//class MyHuggingFaceSpringAiApplicationTests {
//
//    @Autowired
//    private ChatService chatService;
//
//    @Test
//    void testGenerateResponse() {
//        String response = chatService.generateResponse("What is Spring Boot?");
//        
//        assertNotNull(response);
//        assertFalse(response.isEmpty());
//        System.out.println("Response: " + response);
//    }
//
//    @Test
//    void testGenerateRoleBasedResponse() {
//        String response = chatService.generateRoleBasedResponse(
//                "Java expert",
//                "Explain what a Spring Bean is"
//        );
//        
//        assertNotNull(response);
//        assertTrue(response.toLowerCase().contains("spring"));
//        System.out.println("Role-based response: " + response);
//    }
//
//    @Test
//    void testSummarizeText() {
//        String longText = """
//                Spring Framework is an application framework and inversion of control 
//                container for the Java platform. The framework's core features can be 
//                used by any Java application, but there are extensions for building 
//                web applications on top of the Java EE platform.
//                """;
//        
//        String summary = chatService.summarizeText(longText, 20);
//        
//        assertNotNull(summary);
//        System.out.println("Summary: " + summary);
//    }
//
//    @Test
//    void testExplainCode() {
//        String code = """
//                @RestController
//                public class HelloController {
//                    @GetMapping("/hello")
//                    public String hello() {
//                        return "Hello World";
//                    }
//                }
//                """;
//        
//        String explanation = chatService.explainCode(code, "Java");
//        
//        assertNotNull(explanation);
//        System.out.println("Code explanation: " + explanation);
//    }
//}
