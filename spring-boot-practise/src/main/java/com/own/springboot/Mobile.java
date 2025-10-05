package com.own.springboot;

//java -based config
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class Mobile {
//	public static void main(String[] args) {
//		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//
//		// Retrieve the bean
//		Sim sim = context.getBean("sim", Sim.class);
//
//		// Call methods
//		sim.calling();
//		sim.data();
//	}
//}
//mvn exec:java -Dexec.mainClass="com.own.springboot.Mobile"


//xml based config
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class Mobile {
//    public static void main(String[] args) {
//      
//        // Using ApplicationContext tom implement Spring IoC
//        ApplicationContext applicationContext 
//        = new ClassPathXmlApplicationContext("beans.xml");
//        
//        // Get the bean
//        Sim sim = applicationContext.getBean("sim", Sim.class);
//        
//        // Calling the methods
//        sim.calling();
//        sim.data();
//    }
//}


//annotation based
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Mobile {

    @Autowired
    @Qualifier("airtelBean") // Use the explicit bean name
    private Sim sim;

    public void useSim() {
        sim.calling();
        sim.data();
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Mobile mobile = context.getBean(Mobile.class);
        mobile.useSim();
    }
}