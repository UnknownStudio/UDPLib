package team.unstudio.udpl.api.nbt;

public enum NBTBaseType {
	STRING(8),
	BYTE(1),
	DOUBLE(6),
	FLOAT(5),
	INTEGER(3),
	INTARRAY(11),
	BYTEARRAY(7),
	COMPOUND(10),
	LIST(9),
	LONG(4),
	SHORT(2),
	BOOLEAN(-1), // do you sure it has a NBT Tag named BOOLEAN?  see http://minecraft-zh.gamepedia.com/NBT%E6%A0%BC%E5%BC%8F
	END(0);

	private int id;

    private NBTBaseType(int i) {
        this.id = i;
    }

    public int getID() {
	    return id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
