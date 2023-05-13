public class UV {
    private final String name;
    private final String description;
    private final Type type;
    private final int credits;

    public enum Type {
        CS, TM, OM, EC, QC, ST
    }

    public UV(String name, String description, String type, int credits) {
        this.name = name;
        this.description = description;
        this.type = Type.valueOf(type.trim());
        this.credits = credits;
    }
    //toString
    @Override
    public String toString() {
        return "UV{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", credits=" + credits +
                '}';
    }
    //getters
    public String getName() {
        return name;
    }


}
