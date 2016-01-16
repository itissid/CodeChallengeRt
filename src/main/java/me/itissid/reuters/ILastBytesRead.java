package me.itissid.reuters;

public interface ILastBytesRead {
	/**
	 * Record the byte for later viewing.
	 * @param b
	 */
	public abstract void recordByte(byte b);

	/**
	 * If the data was sent in bytes: "f" "o", "o", "b", "a", "r", where the latest byte sent is "r"
	 * we return a String we return is "foobar". The order of the bytes in the string are oldest to latest byte.
	 * This does not change the underlying data.
	 * @return
	 */
	public abstract String printLastBytes();
}