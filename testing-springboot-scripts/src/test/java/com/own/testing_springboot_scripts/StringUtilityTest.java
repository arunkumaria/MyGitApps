package com.own.testing_springboot_scripts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilityTest {

	private final StringUtility stringUtility = new StringUtility();

	@Test
	void testReverse() {
		assertEquals("tac", stringUtility.reverse("cat"));
		assertEquals("", stringUtility.reverse(""));
		assertThrows(IllegalArgumentException.class, () -> stringUtility.reverse(null));
	}

	@Test
	void testToUpperCase() {
		assertEquals("HELLO", stringUtility.toUpperCase("hello"));
		assertEquals("WORLD", stringUtility.toUpperCase("WoRlD"));
		assertThrows(IllegalArgumentException.class, () -> stringUtility.toUpperCase(null));
	}

	@Test
	void testCountVowels() {
		assertEquals(3, stringUtility.countVowels("example"));
		assertEquals(0, stringUtility.countVowels("xyz"));
		assertThrows(IllegalArgumentException.class, () -> stringUtility.countVowels(null));
	}
}
