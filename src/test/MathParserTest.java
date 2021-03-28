package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import mathParser.MathParser;

class MathParserTest {

	@Test
    public void examplesTest() throws Exception {
		
		
        MathParser solution = new MathParser("17 + 3 - 5");
        assertEquals(15, solution.mainParser());

        solution = new MathParser("2*2");
        assertEquals(4.0d, solution.mainParser());

        solution = new MathParser("sin(37) - cos(37)");
        assertEquals(-1.408952185302343, solution.mainParser());

        solution = new MathParser("tan(37)*ctg(37) ");
        assertEquals(1.0d, solution.mainParser());
        
        solution = new MathParser("123+3+2+//sdasdadsodj12313");
        assertThrows(Exception.class, solution::mainParser);
        solution = new MathParser("2*si2");
        assertThrows(Exception.class, solution::mainParser);
	}
}
