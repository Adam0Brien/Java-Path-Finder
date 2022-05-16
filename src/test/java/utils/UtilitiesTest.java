package utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UtilitiesTest {


    @Test
    public void max10Chars() {
        assertTrue(Utilities.max10Chars("1234567890"));  //normal
        assertFalse(Utilities.max10Chars("1234567890-123"));  //abnormal
        assertTrue(Utilities.max10Chars(""));  //normal but unusual
    }
    @Test
    public void max30Chars() {
        assertTrue( Utilities.max30Chars("123456789012345678901234567890"));  //normal
        assertFalse(Utilities.max30Chars("123456789012345678901234567890-123"));  //abnormal
        assertTrue(Utilities.max30Chars(""));  //normal but unusual
    }


    @Test
    public void validIntRange() {
        assertTrue(Utilities.validIntRange(1, 10, 5));
        assertTrue(Utilities.validIntRange(1, 10, 1));
        assertTrue(Utilities.validIntRange(1, 10, 10));
        assertFalse(Utilities.validIntRange(1, 10, 0));
        assertFalse(Utilities.validIntRange(1, 10, 11));

    }

    @Test
    public void validIntNonNegative() {
        assertTrue(Utilities.validIntNonNegative(0));
        assertTrue(Utilities.validIntNonNegative(1));
        assertTrue(Utilities.validIntNonNegative(1000));
        assertFalse(Utilities.validIntNonNegative(-1));


    }
    @Test
    public void validPPS() {
        assertTrue(Utilities.validPPS(("1234567ab")));    // normal
        assertTrue(Utilities.validPPS(("1234567AB")));    // checking different incorrect positions
        assertFalse(Utilities.validPPS(("1234567ab1")));
        assertFalse(Utilities.validPPS(("a234567ab")));
        assertFalse(Utilities.validPPS(("1a34567ab")));
        assertFalse(Utilities.validPPS(("12a4567ab")));
        assertFalse(Utilities.validPPS(("123a567ab")));
        assertFalse(Utilities.validPPS(("1234a67ab")));
        assertFalse(Utilities.validPPS(("12345a7ab")));
        assertFalse(Utilities.validPPS(("123456aab")));
        assertFalse(Utilities.validPPS(("12345671b")));
        assertFalse(Utilities.validPPS(("1234567a1")));
        assertTrue(Utilities.validPPS("0000000XX"));
    }


    @Test
    public void validEmail(){
        assertTrue(Utilities.validEmail("Cshanahan@gmail.com"));
        assertFalse(Utilities.validEmail("Cshan@gmail"));
        assertFalse(Utilities.validEmail("Cshangmail.com"));
        assertFalse(Utilities.validEmail(""));
    }

    @Test
    public void validDoubleNonNegative(){
        assertTrue(Utilities.validDoubleNonNegative(10));
        assertFalse(Utilities.validDoubleNonNegative(-10));
        assertTrue(Utilities.validDoubleNonNegative(0));
        assertFalse(Utilities.validDoubleNonNegative(-0.01));
    }


    @Test
    public void validIndex(){
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("One");
        strings.add("Two");
        strings.add("Three");
        strings.add("Four");
        assertTrue(Utilities.validIndex(2, strings));
        assertTrue(Utilities.validIndex(0, strings));
        assertFalse(Utilities.validIndex(-1, strings));
        assertTrue(Utilities.validIndex(3, strings));
        assertFalse(Utilities.validIndex(4, strings));
    }

    @Test
    public void validBoothIdentifier(){
        String validIdentifier = "1A";
        String validIdentifier1 = "5N";
        String invalidIdentifier = "23";
        String invalidIdentifier1 = "1a23";
        assertTrue(Utilities.validBoothIdentifier(validIdentifier));
        assertTrue(Utilities.validBoothIdentifier(validIdentifier1));
        assertFalse(Utilities.validBoothIdentifier(invalidIdentifier));
        assertFalse(Utilities.validBoothIdentifier(invalidIdentifier1));
    }

    @Test
    public void onlyContainsNumbers(){
        assertTrue(Utilities.onlyContainsNumbers("0256845"));
        assertFalse(Utilities.onlyContainsNumbers("hdksbfl"));
        assertFalse(Utilities.onlyContainsNumbers("as6744asd"));
    }


}