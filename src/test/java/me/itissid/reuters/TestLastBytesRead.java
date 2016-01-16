package me.itissid.reuters;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TestLastBytesRead {

	private final ILastBytesRead underTest = new LastBytesRead();

	private static final String STRING_1100_BYTES = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ultrices ultrices nisl, non convallis orci sollicitudin ut. Sed ullamcorper commodo pharetra. In eros purus, posuere eget quam vitae, rhoncus malesuada massa. Etiam ultricies augue eget justo iaculis faucibus. Donec consequat turpis sed enim dignissim, eget imperdiet nibh posuere. Praesent vitae diam faucibus, ornare odio varius, vehicula purus. Praesent varius augue urna, id malesuada sapien eleifend et. Aliquam sit amet lacus nunc. In et dui non ex rhoncus feugiat et vel sem. Quisque commodo, quam eget dignissim maximus, velit orci vulputate nulla, at molestie justo arcu eget purus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus sodales dapibus facilisis. Curabitur eu dignissim dolor, et aliquam lectus.Mauris egestas sapien eu eleifend ultrices. Sed elementum dapibus odio, id vulputate sem imperdiet eget. Aliquam erat volutpat. Pellentesque interdum eget quam id placerat. Proin vulputate porta facilisis. Phasellus rutrum pretium felis ut luctus. Phasellus placerat blandit sed.'";
	// This is the last 1024 bytes of STRING_1100_BYTES.
	private static final String STRING_1024_BYTES = " nisl, non convallis orci sollicitudin ut. Sed ullamcorper commodo pharetra. In eros purus, posuere eget quam vitae, rhoncus malesuada massa. Etiam ultricies augue eget justo iaculis faucibus. Donec consequat turpis sed enim dignissim, eget imperdiet nibh posuere. Praesent vitae diam faucibus, ornare odio varius, vehicula purus. Praesent varius augue urna, id malesuada sapien eleifend et. Aliquam sit amet lacus nunc. In et dui non ex rhoncus feugiat et vel sem. Quisque commodo, quam eget dignissim maximus, velit orci vulputate nulla, at molestie justo arcu eget purus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus sodales dapibus facilisis. Curabitur eu dignissim dolor, et aliquam lectus.Mauris egestas sapien eu eleifend ultrices. Sed elementum dapibus odio, id vulputate sem imperdiet eget. Aliquam erat volutpat. Pellentesque interdum eget quam id placerat. Proin vulputate porta facilisis. Phasellus rutrum pretium felis ut luctus. Phasellus placerat blandit sed.'";
	
	@Test 
	public void noDataPrintsEmptyString() {
		String actualString = underTest.printLastBytes();
		assertThat(actualString, is(""));
	}

	/**
	 * We expect to read in 1024 bytes exactly if we put them.
	 */
	@Test
	public void readExactly1024Bytes() {
		for(byte b: STRING_1024_BYTES.getBytes())
			underTest.recordByte(b);
		String actualResult = underTest.printLastBytes();
		assertThat(actualResult, is(equalTo(STRING_1024_BYTES)));
	}

	/**
	 * An edge case;
	 */
	@Test
	public void readOneByte() {
		for(byte b: "1".getBytes())
			underTest.recordByte(b);
		String actualResult = underTest.printLastBytes();
		assertThat(actualResult, is(equalTo("1")));
	}

	/**
	 * Reading again should not change the output.
	 */
	@Test
	public void testIdempotentByteRead() {
		for(byte b: STRING_1100_BYTES.getBytes())
			underTest.recordByte(b);
		String actualResult = underTest.printLastBytes();
		String readAgainResult = underTest.printLastBytes();
		assertThat(actualResult, is(equalTo(readAgainResult)));
	}

	/**
	 *  If we add more than {@link LastBytesRead#BYTES_TO_BE_READ}
	 *  then return BYTES_TO_BE_READ;
	 */
	@Test
	public void readLast1024Bytes() {
		for(byte b: STRING_1100_BYTES.getBytes())
			underTest.recordByte(b);
		String actualResult = underTest.printLastBytes();
		assertThat(actualResult, is(STRING_1024_BYTES));
	}
}
