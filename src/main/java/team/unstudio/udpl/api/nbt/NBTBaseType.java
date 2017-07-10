package team.unstudio.udpl.api.nbt;

public enum NBTBaseType {
	STRING(8, ""),
	BYTE(1, "b"),
	DOUBLE(6, "d"),
	FLOAT(5, "f"),
	INTEGER(3, ""),
	INTARRAY(11, "ints"), // 不确定
	BYTEARRAY(7, "bytes"),
	COMPOUND(10, ""), // 不确定
	LIST(9, ""), // 不确定
	LONG(4, "L"),
	SHORT(2, "s"),
	BOOLEAN(-1, "b"), // 0 is false 1 is true
	END(0, "");

	private int id;
	private String sign;

    private NBTBaseType(int i, String sign) {
        this.id = i;
        this.sign = sign;
    }

    public int getID() {
	    return this.id;
    }

    public String getSign() {
        return this.sign;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
